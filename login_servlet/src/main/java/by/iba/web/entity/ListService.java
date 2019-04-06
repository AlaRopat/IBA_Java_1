package by.iba.web.entity;

import java.util.ArrayList;
import java.util.List;

public class ListService {
  private static List<Person> groupList = new ArrayList<>();

  static {
    groupList.add(new Person(1,"Anna", "anna@gmail.com", "+3752978934"));
    groupList.add(new Person(2,"Kate", "kate@gmail.com", "+3752978937"));
    groupList.add(new Person(3,"Olga", "olga@gmail.com", "+3752978967"));
  }

  public static List<Person> retrieveList() {
    return groupList;
  }

  public static void addPerson(Person person) {
    groupList.add(person);
  }
}
