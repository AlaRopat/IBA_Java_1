package by.iba.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import by.iba.web.entity.Role;
import by.iba.web.entity.User;
import by.iba.web.exception.PersistException;
import by.iba.web.exception.UserNotFoundException;

public class UserDao extends AbstractJDBCDao<User, Integer> {

  private static final Logger LOGGER = Logger.getLogger(UserDao.class);

  private static final String SELECT_QUERY = "SELECT id, login, passw, role FROM users";
  private static final String INSERT_QUERY = "INSERT INTO users (login,passw,role) VALUES(?,?,?);";
  private static final String UPDATE_QUERY =
      "UPDATE users SET login= ?, passw = ?, role = ?  WHERE id= ?;";
  private static final String DELETE_QUERY = "DELETE FROM users WHERE id= ?;";
  private static final String USER_BY_LOGIN_QUERY =
      "SELECT login, passw, role from users where login=?;";

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
        result.add(parseUser(resultSet));
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
      preparedStatement.setString(2, BCrypt.hashpw(object.getPassword(), BCrypt.gensalt()));
      preparedStatement.setString(3, object.getRole().getRoleName());

    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  @Override
  protected void prepareStatementForUpdate(PreparedStatement preparedStatement, User object)
      throws PersistException {
    try {
      preparedStatement.setString(1, object.getLogin());
      preparedStatement.setString(2, BCrypt.hashpw(object.getPassword(), BCrypt.gensalt()));
      preparedStatement.setString(3, object.getRole().getRoleName());
      preparedStatement.setInt(4, object.getId());
    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  public boolean isValidUser(final String login, final String password) {

    try (PreparedStatement ps = createUserByLoginPrepareStatement(login);
        ResultSet resultSet = ps.executeQuery(); ) {

      if (resultSet.next()) {
        return BCrypt.checkpw(password, resultSet.getString("passw"));
      }

    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }

    return false;
  }

  public User getUserByLogin(String login) {
    User user = null;

    try (PreparedStatement ps = createUserByLoginPrepareStatement(login);
        ResultSet resultSet = ps.executeQuery(); ) {

      if (resultSet.next()) {
        user = parseUser(resultSet);
      }

    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return Optional.ofNullable(user)
        .orElseThrow(
            () ->
                new UserNotFoundException(String.format("User with login - %s not found", login)));
  }

  private User parseUser(ResultSet resultSet) throws SQLException {
    PersistUser user = new PersistUser();
    user.setId(resultSet.getInt("id"));
    user.setPassword(resultSet.getString("passw"));
    user.setLogin(resultSet.getString("login"));
    user.setRole(Role.getFromString(resultSet.getString("role")));
    return user;
  }

  private PreparedStatement createUserByLoginPrepareStatement(String login) throws SQLException {
    PreparedStatement ps = connection.prepareStatement(USER_BY_LOGIN_QUERY);
    ps.setString(1, login);
    return ps;
  }
}
