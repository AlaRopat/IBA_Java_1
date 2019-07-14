import by.iba.web.entity.User;
import by.iba.web.exception.ConnectionPoolException;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHelper {
  private static final Logger LOGGER = Logger.getLogger(DataBaseHelper.class);

  private static final String INSERT_QUERY = "INSERT INTO users (login,passw,role) VALUES(?,?,?);";
  private Connection connect;

  public DataBaseHelper(Connection connect) throws ConnectionPoolException {
    this.connect = connect;
  }

  public PreparedStatement getPreparedStatement() throws ConnectionPoolException {
    PreparedStatement ps;
    try {
      LOGGER.info("Statement is preparing: " + INSERT_QUERY);
      return connect.prepareStatement(INSERT_QUERY);
    } catch (SQLException e) {
      throw new ConnectionPoolException("Database access error", e);
    }
  }

  public boolean insertUser(PreparedStatement preparedStatement, User user)
      throws ConnectionPoolException {
    try {
      preparedStatement.setString(1, user.getLogin());
      preparedStatement.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
      preparedStatement.setString(3, user.getRole().getRoleName());
      return true;
    } catch (SQLException e) {
      throw new ConnectionPoolException("Database access error", e);
    }
  }

  public void closeStatement(PreparedStatement ps) throws ConnectionPoolException {
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        throw new ConnectionPoolException("Database access error", e);
      }
      LOGGER.info("Connection was closed");
    }
  }
}
