package by.iba.web.dao;

import by.iba.web.exception.ConnectionPoolException;
import by.iba.web.exception.PersistException;

public interface DaoFactory<Context> {

  Context getContext() throws ConnectionPoolException;

  GenericDao getDao(Context context, Class dtoClass) throws PersistException;

}