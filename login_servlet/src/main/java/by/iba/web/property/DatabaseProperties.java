package by.iba.web.property;

import java.util.ResourceBundle;

public class DatabaseProperties {
  private static ResourceBundle resource;

  static {
    resource = ResourceBundle.getBundle("db");
  }

  public static String getProperty(String key) {
    return resource.getString(key);
  }
}
