package by.iba.web.command;

import by.iba.web.dao.PersonDao;
import by.iba.web.entity.ListService;
import by.iba.web.exception.PersistException;
import by.iba.web.property.PropertiesManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ShowPersonsCommand implements ActionCommand {
  private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

  @Override
  public String execute(HttpServletRequest request) {
    PersonDao personDao = new PersonDao();

    try {
      request.getSession().setAttribute("personList", personDao.getAll());
    } catch (PersistException e) {

      LOGGER.error(e.getMessage());
      request
              .getSession()
              .setAttribute("addUserError", PropertiesManager.getProperty("message.add.error.user"));
      return PropertiesManager.getProperty("path.page.app.error");
    }
    return PropertiesManager.getProperty("path.page.show.persons");
  }
}
