package by.iba.web.entity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum Role {
  USER("user"),
  ADMIN("admin");

  private String roleName;

  Role(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return this.roleName;
  }

  private static Map<String, Role> LOOK_UP = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

  static {
    for (Role item : Role.values()) {
      LOOK_UP.put(item.getRoleName(), item);
    }
    LOOK_UP = Collections.unmodifiableMap(LOOK_UP);
  }

  public static Role getFromString(String name) {
    try {
      return LOOK_UP.get(name.toUpperCase());
    } catch (Exception e) {
      return null;
    }
  }
}
