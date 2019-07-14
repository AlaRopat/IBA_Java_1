package by.iba.web.command;

import by.iba.web.entity.Role;
import by.iba.web.entity.User;
import by.iba.web.exception.ServiceException;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements ActionCommand {

  private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String ROLE = "role";

  @Override
  public String execute(HttpServletRequest request) {
    String login = request.getParameter(LOGIN);
    String password = request.getParameter(PASSWORD);
    String role = request.getParameter(ROLE);
    UserService userService = new UserService();

    if (userService.checkLogin(login, password)) {

      request
          .getSession()
          .setAttribute("userExists", PropertiesManager.getProperty("message.user.exist"));
      return PropertiesManager.getProperty("path.page.register");
    }

    User newUser =
        User.builder().login(login).password(password).role(Role.getFromString(role)).build();

    try {
      userService.saveUser(newUser);
    } catch (ServiceException e) {

      LOGGER.error(e.getMessage());
      request
          .getSession()
          .setAttribute("error_message", PropertiesManager.getProperty("message.add.error.user"));
      return PropertiesManager.getProperty("path.page.error");
    }

    request
        .getSession()
        .setAttribute("successRegister", PropertiesManager.getProperty("message.add.user"));
    return PropertiesManager.getProperty("path.page.success");
  }
}
