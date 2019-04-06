package by.iba.web.entity;

import by.iba.web.dao.Identified;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Person implements Identified<Integer> {
  private Integer id;
  private String name;
  private String email;
  private String phone;
}
