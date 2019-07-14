package by.iba.web.database.pool;

import by.iba.web.exception.ConnectionPoolException;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;

public interface ConnectionPool {

  Connection getConnection() throws ConnectionPoolException;

  void release(Connection connection);

  void shutdown() throws ConnectionPoolException;

  int getOpenedConnectionsSize();

  BlockingQueue<Connection> getBusyConnections();
}
