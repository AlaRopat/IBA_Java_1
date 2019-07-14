package by.iba.web.service;

import by.iba.web.dao.DaoFactory;
import by.iba.web.dao.DaoFactory_Impl;
import by.iba.web.dao.UserDao;
import by.iba.web.entity.User;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;
import by.iba.web.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class UserService {

  private static final Logger LOGGER = Logger.getLogger(UserService.class);

  private DaoFactory<Connection> daoFactory;

  public UserService() {
    this.daoFactory = new DaoFactory_Impl();
  }

  public boolean checkLogin(String login, String pass) {
    try {
      UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.getContext(), User.class);
      return userDao.isValidUser(login, pass);
    } catch (PersistException | ConnectionPoolException e) {
      LOGGER.error(e.getMessage());
    }
    return false;
  }

  public Optional<User> getUserByLogin(String login) throws ServiceException {
    try {
      UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.getContext(), User.class);
      return Optional.ofNullable(userDao.getUserByLogin(login));
    } catch (PersistException | ConnectionPoolException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }

  public void saveUser(User user) throws ServiceException {
    try {
      UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.getContext(), User.class);
      userDao.save(user);
    } catch (PersistException | ConnectionPoolException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }
}
