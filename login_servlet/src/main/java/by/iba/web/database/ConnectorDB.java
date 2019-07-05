package by.iba.web.database;

import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.property.DatabaseProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {

  private static final Logger LOGGER = Logger.getLogger(ConnectorDB.class);

  public static Connection getConnection() throws ConnectionPoolException {
    String url = DatabaseProperties.getProperty("db.url");
    String user = DatabaseProperties.getProperty("db.user");
    String pass = DatabaseProperties.getProperty("db.password");
    Connection connection;
    try {
      connection = DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      throw new ConnectionPoolException("Can't instantiate DB Driver. " + e.getMessage());
    }
    LOGGER.info("Connection was created: " + connection);

    return connection;
  }
}
