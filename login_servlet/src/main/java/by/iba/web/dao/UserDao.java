package by.iba.web.dao;

import by.iba.web.entity.User;
import by.iba.web.exception.PersistException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDao extends AbstractJDBCDao<User, Integer> {

  private static final String SELECT_QUERY = "SELECT id, login, passw FROM users";
  private static final String INSERT_QUERY =
      "INSERT INTO users (login,passw) VALUES(?,?);";
  private static final String UPDATE_QUERY =
      "UPDATE users SET login= ?, passw = ? WHERE id= ?;";
  private static final String DELETE_QUERY = "DELETE FROM users WHERE id= ?;";
  private static final String USER_BY_LOGIN_PASSWORD_QUERY =
      "SELECT login, passw from users where login=? and passw=?;";




  private class PersistUser extends User {

    public void setId(int id) {
      super.setId(id);
    }
  }

  public UserDao() {
    super();
  }

  @Override
  public String getSelectQuery() {
    return SELECT_QUERY;
  }

  @Override
  public String getCreateQuery() {
    return INSERT_QUERY;
  }

  @Override
  public String getUpdateQuery() {
    return UPDATE_QUERY;
  }

  @Override
  public String getDeleteQuery() {
    return DELETE_QUERY;
  }

  @Override
  public List<User> parseResultSet(ResultSet resultSet) throws PersistException {
    List<User> result = new LinkedList<>();
    try {
      while (resultSet.next()) {
        PersistUser user = new PersistUser();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("passw"));
        user.setLogin(resultSet.getString("login"));
        result.add(user);
      }
    } catch (Exception e) {
      throw new PersistException(e);
    }
    return result;
  }

  @Override
  protected void prepareStatementForInsert(PreparedStatement preparedStatement, User object)
      throws PersistException {
    try {
      preparedStatement.setString(1, object.getLogin());
      preparedStatement.setString(2, object.getPassword());

    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  @Override
  protected void prepareStatementForUpdate(PreparedStatement preparedStatement, User object)
      throws PersistException {
    try {
      preparedStatement.setString(1, object.getLogin());
      preparedStatement.setString(2, object.getPassword());
      preparedStatement.setInt(3, object.getId());
    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  public boolean isValidUser(final String login, final String password) {

    PreparedStatement ps = null;
    try {

      ps = connection.prepareStatement(USER_BY_LOGIN_PASSWORD_QUERY);
      ps.setString(1, login);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();
      return rs.first();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }
}
