package by.iba.web.command;

import by.iba.web.entity.ListService;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {

  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String USER_ATTRIBUTE = "user";

  @Override
  public String execute(HttpServletRequest request) {
    String login = request.getParameter(LOGIN);
    String pass = request.getParameter(PASSWORD);

    if (LoginService.checkLogin(login, pass)) {
      request.getSession().setAttribute(USER_ATTRIBUTE, login);
      return PropertiesManager.getProperty("path.page.welcome");
    }
    request
        .getSession()
        .setAttribute("errorLoginPassMessage", PropertiesManager.getProperty("message.loginerror"));
    return PropertiesManager.getProperty("path.page.login");
  }
}
