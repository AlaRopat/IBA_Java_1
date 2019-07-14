package by.iba.web.command;

import by.iba.web.exception.ServiceException;
import by.iba.web.property.PropertiesManager;
import by.iba.web.service.PersonService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ShowPersonsCommand implements ActionCommand {
  private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

  @Override
  public String execute(HttpServletRequest request) {
    PersonService personService = new PersonService();
    try {
      request.getSession().setAttribute("personList", personService.getAllPersons());
    } catch (ServiceException e) {

      LOGGER.error(e.getMessage());
      request.getSession().setAttribute("showPersonsError", e.getMessage());
      return PropertiesManager.getProperty("path.page.show.persons");
    }
    return PropertiesManager.getProperty("path.page.show.persons");
  }
}
