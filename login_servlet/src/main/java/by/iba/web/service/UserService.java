package by.iba.web.service;

import by.iba.web.dao.UserDao;

public class UserService {

  public static boolean checkLogin(String login, String pass) {
    UserDao userDao = new UserDao();

    return userDao.isValidUser(login, pass);
  }
}
