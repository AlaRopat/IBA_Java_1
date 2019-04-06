package by.iba.web.command;

import by.iba.web.dao.PersonDao;
import by.iba.web.entity.Person;
import by.iba.web.exception.PersistException;
import by.iba.web.property.PropertiesManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddPersonCommand implements ActionCommand {

  private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

  private static final String ADD_NAME = "add_name";
  private static final String ADD_PHONE = "add_phone";
  private static final String ADD_EMAIL = "add_email";

  @Override
  public String execute(HttpServletRequest request) {

    String name = request.getParameter(ADD_NAME);
    String phone = request.getParameter(ADD_PHONE);
    String email = request.getParameter(ADD_EMAIL);
    if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {

      request
          .getSession()
          .setAttribute("errorMessage", PropertiesManager.getProperty("message.empty.fields"));
      return PropertiesManager.getProperty("path.page.welcome");
    }

    PersonDao personDao = new PersonDao();
    Person newPerson = Person.builder().name(name).phone(phone).email(email).build();
    try {
      personDao.save(newPerson);
    } catch (PersistException e) {

      LOGGER.error(e.getMessage());
      request
          .getSession()
          .setAttribute("addUserError", PropertiesManager.getProperty("message.add.error.user"));
      return PropertiesManager.getProperty("path.page.app.error");
    }

    request
        .getSession()
        .setAttribute("successAddPerson", PropertiesManager.getProperty("message.add.person"));
    return PropertiesManager.getProperty("path.page.success");
  }
}
