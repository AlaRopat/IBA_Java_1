package by.iba.web.dao;

import by.iba.web.entity.Person;
import by.iba.web.entity.User;
import by.iba.web.exception.PersistException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PersonDao extends AbstractJDBCDao<Person, Integer> {

  private static final String SELECT_QUERY = "SELECT id, name, phone, email FROM persons";
  private static final String INSERT_QUERY =
      "INSERT INTO persons (name,phone,email) VALUES(?,?,?);";
  private static final String UPDATE_QUERY =
      "UPDATE persons SET name = ?, phone = ?, email = ? WHERE id= ?;";
  private static final String DELETE_QUERY = "DELETE FROM persons WHERE id= ?;";




  private class PersistPerson extends Person {

    public void setId(int id) {
      super.setId(id);
    }
  }

  public PersonDao() {
    super();
  }

  @Override
  public String getSelectQuery() {
    return SELECT_QUERY;
  }

  @Override
  public String getCreateQuery() {
    return INSERT_QUERY;
  }

  @Override
  public String getUpdateQuery() {
    return UPDATE_QUERY;
  }

  @Override
  public String getDeleteQuery() {
    return DELETE_QUERY;
  }

  @Override
  public List<Person> parseResultSet(ResultSet resultSet) throws PersistException {
    List<Person> result = new LinkedList<>();
    try {
      while (resultSet.next()) {
        PersistPerson person = new PersistPerson();
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setPhone(resultSet.getString("phone"));
        person.setEmail(resultSet.getString("email"));
        result.add(person);
      }
    } catch (Exception e) {
      throw new PersistException(e);
    }
    return result;
  }

  @Override
  protected void prepareStatementForInsert(PreparedStatement preparedStatement, Person object)
      throws PersistException {
    try {
      preparedStatement.setString(1, object.getName());
      preparedStatement.setString(2, object.getPhone());
      preparedStatement.setString(3, object.getEmail());

    } catch (Exception e) {
      throw new PersistException(e);
    }
  }

  @Override
  protected void prepareStatementForUpdate(PreparedStatement preparedStatement, Person object)
      throws PersistException {
    try {
      preparedStatement.setString(1, object.getName());
      preparedStatement.setString(2, object.getPhone());
      preparedStatement.setString(3, object.getEmail());
      preparedStatement.setInt(4, object.getId());
    } catch (Exception e) {
      throw new PersistException(e);
    }
  }


}
