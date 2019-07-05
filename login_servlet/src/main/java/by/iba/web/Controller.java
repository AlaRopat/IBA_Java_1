package by.iba.web;

import by.iba.web.command.ActionCommand;
import by.iba.web.factory.ActionFactory;
import by.iba.web.property.PropertiesManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "*.do")
public class Controller extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ActionFactory actionFactory = new ActionFactory();
    ActionCommand command = actionFactory.defineCommand(request);
    String page = command.execute(request);
    if (page == null) {
      page = PropertiesManager.getProperty("path.page.index");
      request
          .getSession()
          .setAttribute("nullPage", PropertiesManager.getProperty("message.nullpage"));
      response.sendRedirect(request.getContextPath() + page);
    } else {
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
      dispatcher.forward(request, response);
    }
  }
}
