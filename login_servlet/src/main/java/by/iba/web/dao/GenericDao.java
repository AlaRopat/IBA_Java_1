package by.iba.web.dao;

import by.iba.web.exception.PersistException;
import java.io.Serializable;
import java.util.List;


public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {


  T save(T object) throws PersistException;


  T get(PK key) throws PersistException;


  void update(T object) throws PersistException;


  void delete(T object) throws PersistException;


  List<T> getAll() throws PersistException;
}