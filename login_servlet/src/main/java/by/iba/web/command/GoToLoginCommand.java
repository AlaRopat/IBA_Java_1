package by.iba.web.command;

import by.iba.web.property.PropertiesManager;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginCommand implements ActionCommand {
  @Override
  public String execute(HttpServletRequest request) {

    return PropertiesManager.getProperty("path.page.login");
  }
}
