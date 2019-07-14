package by.iba.web.entity;

import by.iba.web.dao.Identified;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements Identified<Integer> {
  private Integer id;
  @NonNull private String login;
  @NonNull private String password;
  @NonNull private Role role;
}
