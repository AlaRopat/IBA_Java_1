package by.iba.web.command;

import by.iba.web.property.PropertiesManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

  @Override
  public String execute(HttpServletRequest request) {
    request.getSession().invalidate();
    return PropertiesManager.getProperty("path.page.login");
  }
}
