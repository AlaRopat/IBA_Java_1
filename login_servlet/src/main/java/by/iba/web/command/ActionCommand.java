package by.iba.web.command;

import by.iba.web.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {

  String execute(HttpServletRequest request) throws ServiceException;
}