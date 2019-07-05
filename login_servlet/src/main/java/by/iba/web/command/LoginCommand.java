package by.iba.web.command;

import javax.servlet.http.HttpServletRequest;

import by.iba.web.dao.UserDao;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.UserService;

public class LoginCommand implements ActionCommand {

  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String USER_ATTRIBUTE = "user";

  @Override
  public String execute(HttpServletRequest request) {
    String login = request.getParameter(LOGIN);
    String pass = request.getParameter(PASSWORD);

    if (UserService.checkLogin(login, pass)) {
      UserDao userDao = new UserDao();
      request.getSession().setAttribute(USER_ATTRIBUTE, userDao.getUserByLogin(login));

      return PropertiesManager.getProperty("path.page.welcome");
    }
    request
        .getSession()
        .setAttribute("errorLoginPassMessage", PropertiesManager.getProperty("message.loginerror"));
    return PropertiesManager.getProperty("path.page.login");
  }
}
