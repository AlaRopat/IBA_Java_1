import by.iba.web.database.pool.ConnectionPool;
import by.iba.web.database.pool.JDBCConnectionPoolManager;
import by.iba.web.entity.Role;
import by.iba.web.entity.User;
import by.iba.web.exception.ConnectionPoolException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;

public class ConnectionPoolTest {

  private static final Logger LOGGER = Logger.getLogger(ConnectionPoolTest.class);
  public String errorMessage = "";
  private ConnectionPool connectionPool = null;

  @Before
  public void initConnectionPool() {
    connectionPool = JDBCConnectionPoolManager.getInstance().getConnectionPool();
  }

  @Test
  public void normalWork() {
    final CountDownLatch allExecutorThreadsReady = new CountDownLatch(10);
    for (int i = 0; i < 10; i++) {
      final int finalI = i;
      new Thread(
              () -> {
                Connection connection = null;
                try {
                  connection = connectionPool.getConnection();
                  LOGGER.info("Client # " + finalI + " took connection ");
                  DataBaseHelper dataBaseHelper = new DataBaseHelper(connection);
                  PreparedStatement ps = dataBaseHelper.getPreparedStatement();
                  User user =
                      new User(
                          RandomStringUtils.randomAlphabetic(5),
                          RandomStringUtils.randomAlphabetic(5),
                          Role.USER);
                  dataBaseHelper.insertUser(ps, user);
                } catch (ConnectionPoolException e) {
                  LOGGER.error(e.getMessage());
                  errorMessage = e.getMessage();
                } finally {
                  connectionPool.release(connection);
                  allExecutorThreadsReady.countDown();
                  LOGGER.info("all Executor Threads Ready: " + allExecutorThreadsReady.getCount());
                }
              })
          .start();
      LOGGER.info("Client # " + i + " was created");
    }
    try {
      allExecutorThreadsReady.await();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
    }

    Assert.assertTrue("Error: " + errorMessage, errorMessage.isEmpty());
  }

  @After
  public void shutdown() {
    try {
      connectionPool.shutdown();
    } catch (ConnectionPoolException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
