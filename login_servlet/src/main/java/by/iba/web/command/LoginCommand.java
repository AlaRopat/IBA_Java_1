package by.iba.web.command;

import by.iba.web.entity.User;
import by.iba.web.exception.ServiceException;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements ActionCommand {

  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String USER_ATTRIBUTE = "user";

  @Override
  public String execute(HttpServletRequest request) throws ServiceException {
    String login = request.getParameter(LOGIN);
    String pass = request.getParameter(PASSWORD);
    UserService userService = new UserService();

    if (userService.checkLogin(login, pass)) {
      Optional<User> user = userService.getUserByLogin(login);
      request.getSession().setAttribute(USER_ATTRIBUTE, user.get());

      return PropertiesManager.getProperty("path.page.welcome");
    }

    request
        .getSession()
        .setAttribute("errorLoginPassMessage", PropertiesManager.getProperty("message.loginerror"));
    return PropertiesManager.getProperty("path.page.login");
  }
}
