package by.iba.web.factory;

import by.iba.web.command.ActionCommand;
import by.iba.web.command.CommandType;
import by.iba.web.command.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

  public ActionCommand defineCommand(HttpServletRequest request) {
    ActionCommand current = new EmptyCommand();
    String action = request.getRequestURI();
    if (action == null || action.isEmpty()) {
      return current;
    }
    try {
      CommandType currentEnum =
              CommandType.getEnumFromString(CommandType.class, action.toUpperCase());
      current = currentEnum.getCurrentCommand();
    } catch (IllegalArgumentException e) {
      request.getSession().setAttribute("wrongAction", action);
    }
    return current;
  }
}
