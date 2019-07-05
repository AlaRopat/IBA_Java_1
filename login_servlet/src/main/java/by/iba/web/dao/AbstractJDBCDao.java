package by.iba.web.dao;

import by.iba.web.database.pool.JDBCConnectionPoolManager;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class provides a base base implementation of CRUD operations with JDBC
 *
 * @param <T> type of object
 * @param <PK> primary key
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Integer>
    implements GenericDao<T, PK> {

  protected Connection connection;

  public AbstractJDBCDao() {
    try {
      this.connection = JDBCConnectionPoolManager.getInstance().getConnectionPool().getConnection();
    } catch (ConnectionPoolException e) {
      e.printStackTrace();
    }
  }

  /** @return sql request for all records SELECT * FROM [Table] */
  public abstract String getSelectQuery();

  /**
   * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
   *
   * @return sql request to add a new record to data base
   */
  public abstract String getCreateQuery();

  /**
   * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
   *
   * @return sql request to update an exist record
   */
  public abstract String getUpdateQuery();

  /**
   * DELETE FROM [Table] WHERE id= ?;
   *
   * @return sql request to delete a record from data base
   */
  public abstract String getDeleteQuery();

  /** @return List of object corresponding to ResultSet */
  public abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;

  protected abstract void prepareStatementForInsert(PreparedStatement preparedStatement, T object)
      throws PersistException;

  protected abstract void prepareStatementForUpdate(PreparedStatement preparedStatement, T object)
      throws PersistException;

  @Override
  public T get(Integer key) throws PersistException {
    List<T> list;
    String sql = getSelectQuery();
    sql += " WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, key);
      ResultSet rs = statement.executeQuery();
      list = parseResultSet(rs);
    } catch (Exception e) {
      throw new PersistException(e);
    }
    if (list == null || list.size() == 0) {
      throw new PersistException("Record with PK = " + key + " not found.");
    }
    if (list.size() > 1) {
      throw new PersistException("Received more than one record.");
    }
    return list.iterator().next();
  }

  @Override
  public List<T> getAll() throws PersistException {
    List<T> list;
    String sql = getSelectQuery();
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      list = parseResultSet(resultSet);
    } catch (SQLException e) {
      throw new PersistException(e);
    }
    return list;
  }

  @Override
  public T save(T object) throws PersistException {
    if (object.getId() != null) {
      throw new PersistException("Object is already saved.");
    }
    T persistInstance;
    // add record
    String sql = getCreateQuery();
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      prepareStatementForInsert(statement, object);
      int count = statement.executeUpdate();
      if (count != 1) {
        throw new PersistException("On persist modify more then 1 record: " + count);
      }
    } catch (Exception e) {
      throw new PersistException(e);
    }
    // get a record that was added
    sql = getSelectQuery() + " WHERE id = LAST_INSERT_ID();";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      ResultSet rs = statement.executeQuery();
      List<T> list = parseResultSet(rs);
      if ((list == null) || (list.size() != 1)) {
        throw new PersistException("Exception on findByPK new persist data.");
      }
      persistInstance = list.iterator().next();
    } catch (Exception e) {
      throw new PersistException(e);
    }
    return persistInstance;
  }

  public void update(T object) throws PersistException {
    if (object.getId() == null) {
      throw new PersistException("Object isn't saved.");
    }
    String sql = getUpdateQuery();
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      prepareStatementForUpdate(preparedStatement, object);
      int count = preparedStatement.executeUpdate();
      if (count != 1) {
        throw new PersistException("On update modify more then 1 record: " + count);
      }
    } catch (SQLException e) {
      throw new PersistException(e);
    }
  }

  public void delete(T object) throws PersistException {
    if (object.getId() == null) {
      throw new PersistException("Object isn't saved.");
    }
    String sql = getDeleteQuery();
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      try {
        statement.setObject(1, object.getId());
      } catch (Exception e) {
        throw new PersistException(e);
      }
      int count = statement.executeUpdate();
      if (count != 1) {
        throw new PersistException("On delete modify more then 1 record: " + count);
      }
    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  public void closeConnection() {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
