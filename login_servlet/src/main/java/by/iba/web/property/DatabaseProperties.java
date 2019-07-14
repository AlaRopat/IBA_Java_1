package by.iba.web.property;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {

  private static Properties resourceBundle = new Properties();

  static {
    try (InputStream is =
        PropertiesManager.class.getClassLoader().getResourceAsStream("db.properties")) {
      resourceBundle.load(is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String key) {
    return resourceBundle.getProperty(key);
  }
}
