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
public class Person implements Identified<Integer> {
  private Integer id;
  @NonNull private String name;
  @NonNull private String email;
  @NonNull private String phone;
}
