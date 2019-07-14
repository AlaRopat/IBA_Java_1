package by.iba.web.dao;

import by.iba.web.database.pool.JDBCConnectionPoolManager;
import by.iba.web.entity.Person;
import by.iba.web.entity.User;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DaoFactory_Impl implements DaoFactory<Connection> {

  private Map<Class, DaoCreator> creators;

  @Override
  public Connection getContext() throws ConnectionPoolException {
    return JDBCConnectionPoolManager.getInstance().getConnectionPool().getConnection();
  }

  @Override
  public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
    DaoCreator daoCreator = creators.get(dtoClass);
    if (daoCreator == null) {
      throw new PersistException("Dao object for " + dtoClass + " not found.");
    }
    return daoCreator.create(connection);
  }

  public DaoFactory_Impl() {
    creators = new HashMap<>();
    creators.put(User.class, (connection) -> new UserDao((Connection) connection));
    creators.put(Person.class, (connection) -> new PersonDao((Connection) connection));
  }
}
