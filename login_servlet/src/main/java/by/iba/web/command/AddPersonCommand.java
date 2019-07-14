package by.iba.web.command;

import by.iba.web.entity.Person;
import by.iba.web.exception.ServiceException;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.PersonService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddPersonCommand implements ActionCommand {

  private static final Logger LOGGER = Logger.getLogger(AddPersonCommand.class);

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

    PersonService personService = new PersonService();
    Person newPerson = Person.builder().name(name).phone(phone).email(email).build();
    try {
      personService.savePerson(newPerson);
    } catch (ServiceException e) {

      LOGGER.error(e.getMessage());
      request
          .getSession()
          .setAttribute("error_message", e.getMessage());
      return PropertiesManager.getProperty("path.page.error");
    }

    request
        .getSession()
        .setAttribute("successAddPerson", PropertiesManager.getProperty("message.add.person"));
    return PropertiesManager.getProperty("path.page.success");
  }
}
