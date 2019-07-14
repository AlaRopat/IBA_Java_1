package by.iba.web.service;

import by.iba.web.dao.DaoFactory;
import by.iba.web.dao.DaoFactory_Impl;
import by.iba.web.dao.PersonDao;
import by.iba.web.entity.Person;
import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;
import by.iba.web.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class PersonService {

  private static final Logger LOGGER = Logger.getLogger(UserService.class);

  private DaoFactory<Connection> daoFactory;

  public PersonService() {
    this.daoFactory = new DaoFactory_Impl();
  }

  public void savePerson(Person person) throws ServiceException {
    try {
      PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.getContext(), Person.class);
      personDao.save(person);
    } catch (PersistException | ConnectionPoolException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }

  public List<Person> getAllPersons() throws ServiceException {
    try {
      PersonDao personDao = (PersonDao) daoFactory.getDao(daoFactory.getContext(), Person.class);
      return personDao.getAll();
    } catch (PersistException | ConnectionPoolException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }
}
