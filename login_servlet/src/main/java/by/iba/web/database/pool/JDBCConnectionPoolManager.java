package by.iba.web.database.pool;

import by.iba.web.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

public class JDBCConnectionPoolManager {

  private static final Logger LOGGER = Logger.getLogger(JDBCConnectionPoolManager.class);
  private ConnectionPool connectionPool;
  private static JDBCConnectionPoolManager instance = null;

  private JDBCConnectionPoolManager() throws ConnectionPoolException {
    connectionPool = JDBCConnectionPool.initConnectionPool();
  }

  public static JDBCConnectionPoolManager getInstance() {
    if (instance == null) {
      instance =
          ThreadLocal.withInitial(() -> {
            JDBCConnectionPoolManager connectionPoolManager = null;
            try {
              connectionPoolManager = new JDBCConnectionPoolManager();
            } catch (ConnectionPoolException e) {
              LOGGER.error(e.getMessage());
            }
            return connectionPoolManager;
          }).get();
    }
    return instance;
  }

  public ConnectionPool getConnectionPool() {
    return this.connectionPool;
  }
}
