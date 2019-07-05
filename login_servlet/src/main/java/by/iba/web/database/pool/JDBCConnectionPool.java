package by.iba.web.database.pool;

import by.iba.web.database.ConnectorDB;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.property.DatabaseProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JDBCConnectionPool implements ConnectionPool {

  private static final Logger LOGGER = Logger.getLogger(JDBCConnectionPool.class);
  private int MAX_SIZE = Integer.valueOf(DatabaseProperties.getProperty("max.size"));
  private static int INIT_SIZE = Integer.valueOf(DatabaseProperties.getProperty("init.size"));
  private static Lock lock = new ReentrantLock();
  private BlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(MAX_SIZE);
  private BlockingQueue<Connection> busyConnections = new ArrayBlockingQueue<>(MAX_SIZE);
  private static JDBCConnectionPool jdbcConnectionPool;
  private static AtomicInteger openedConnections;
  private AtomicBoolean isShutDown = new AtomicBoolean(false);
  private static final int MAX_TIMEOUT = 10000;

  private JDBCConnectionPool(List<Connection> connections) {
    freeConnections.addAll(connections);
  }

  public static JDBCConnectionPool initConnectionPool() throws ConnectionPoolException {

    lock.lock();

    if (jdbcConnectionPool == null) {

      List<Connection> connections = new ArrayList<>();
      try {
        for (int i = 0; INIT_SIZE > i; i++) {
          connections.add(ConnectorDB.getConnection());
        }
        jdbcConnectionPool = new JDBCConnectionPool(connections);
        openedConnections = new AtomicInteger(INIT_SIZE);
        LOGGER.info("Init connection pool. Connection pool size: " + openedConnections.get());
      } finally {
        lock.unlock();
      }
    }
    return jdbcConnectionPool;
  }

  public Connection getConnection() throws ConnectionPoolException {
    Connection connection;
    try {
      connection = freeConnections.poll(1000, TimeUnit.MILLISECONDS);
      if (connection == null) {
        if (openedConnections.get() < MAX_SIZE) {
          connection = ConnectorDB.getConnection();
          LOGGER.info(
              "Create new connection. Connection pool size: "
                  + openedConnections.incrementAndGet());
          busyConnections.put(connection);
        } else {
          connection = freeConnections.poll(MAX_TIMEOUT, TimeUnit.MILLISECONDS);
          if (connection == null) {

            throw new ConnectionPoolException("Exceeded the connection timeout");
          } else {
            busyConnections.put(connection);
          }
        }
      } else {
        busyConnections.put(connection);
      }

    } catch (InterruptedException e) {
      throw new ConnectionPoolException(e.getMessage());
    }
    return connection;
  }

  public void release(Connection connection) {

    if (!busyConnections.contains(connection)) {
      LOGGER.error("Not Allowed operation");
      return;
    }

    busyConnections.removeIf(iteratorConnection -> iteratorConnection.equals(connection));

    try {

      if (connection != null && !connection.isClosed()) {
        freeConnections.put(connection);
        LOGGER.info("Released connection");

      } else {

        LOGGER.info(
            "Connection was closed. Connection pool size: " + openedConnections.decrementAndGet());
      }

    } catch (SQLException | InterruptedException e) {
      LOGGER.error("Database access error occurs" + e.getMessage());
    }
  }

  public void shutdown() throws ConnectionPoolException {

    isShutDown.set(true);
    Connection connection;
    lock.lock();

    try {
      while (!busyConnections.isEmpty()) {
        connection = busyConnections.poll();
        if (connection != null || !connection.isClosed()) {
          connection.close();
        }
      }
      while (!freeConnections.isEmpty()) {
        connection = freeConnections.poll();
        if (connection != null || !connection.isClosed()) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new ConnectionPoolException(
          "SQLException, shut down the connection pool: " + e.getMessage());
    } finally {
      lock.unlock();
    }
  }

  public int getOpenedConnectionsSize() {
    return openedConnections.get();
  }

  public BlockingQueue<Connection> getBusyConnections() {
    return busyConnections;
  }
}
