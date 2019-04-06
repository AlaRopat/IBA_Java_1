package by.iba.web.entity;

import by.iba.web.dao.Identified;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements Identified<Integer> {
  private Integer id;
  private String login;
  private String password;
  private Role role;
}
