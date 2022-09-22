package web.restapi.boilerplate.modules.auth.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class SignUpDto {
  @Getter
  @Setter
  private String email;
  @Getter
  @Setter
  private String password;
  @Getter
  @Setter
  private String password2;

  @Getter
  @Setter
  private Set<String> roles;

}
