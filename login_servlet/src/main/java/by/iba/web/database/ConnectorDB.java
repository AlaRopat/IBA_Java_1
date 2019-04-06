package by.iba.web.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorDB {

  private static Properties resource = new Properties();

  static {
    try (InputStream is = ConnectorDB.class.getClassLoader().getResourceAsStream("db.properties")) {
      resource.load(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() throws SQLException {
    String url = resource.getProperty("db.url");
    String user = resource.getProperty("db.user");
    String pass = resource.getProperty("db.password");

    return DriverManager.getConnection(url, user, pass);
  }
}
