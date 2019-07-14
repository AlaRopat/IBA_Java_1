import by.iba.web.dao.DaoFactory;
import by.iba.web.dao.DaoFactory_Impl;
import by.iba.web.dao.GenericDao;
import by.iba.web.dao.Identified;
import by.iba.web.database.pool.JDBCConnectionPoolManager;
import by.iba.web.entity.Person;
import by.iba.web.entity.Role;
import by.iba.web.entity.User;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runners.Parameterized;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class MySqlDaoTest extends GenericDaoTest<Connection> {

  private Connection connection;

  private GenericDao genericDao;

  private static final DaoFactory<Connection> factory = new DaoFactory_Impl();

  public MySqlDaoTest(Class daoClass, Identified<Integer> notPersistedDto) {
    super(daoClass, notPersistedDto);
  }

  @Parameterized.Parameters
  public static Collection getParameters() {
    return Arrays.asList(
        new Object[][] {
          {
            User.class,
            new User(
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5),
                Role.USER)
          },
          {
            Person.class,
            new Person(
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5) + "@gmail.com",
                RandomStringUtils.randomAlphanumeric(7))
          },
        });
  }

  @Before
  public void setUp() throws PersistException, ConnectionPoolException, SQLException {
    connection = factory.getContext();
    connection.setAutoCommit(false);
    genericDao = factory.getDao(connection, daoClass);
  }

  @After
  public void tearDown() throws SQLException, ConnectionPoolException {
    context().rollback();
    JDBCConnectionPoolManager.getInstance().getConnectionPool().release(connection);
  }

  @Override
  public GenericDao dao() {
    return genericDao;
  }

  @Override
  public Connection context() {
    return connection;
  }

  @AfterClass
  public static void close() throws ConnectionPoolException {
    JDBCConnectionPoolManager.getInstance().getConnectionPool().shutdown();
  }
}
