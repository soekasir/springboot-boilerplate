package web.restapi.boilerplate.modules.auth.dto;

import java.util.Date;
import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import web.restapi.boilerplate.models.entities.Role;
import web.restapi.boilerplate.models.entities.Users;

public class SignUpDto {
  @Getter @Setter
  private String email;
  @Getter @Setter
  private String password;
  @Getter @Setter
  private String password2;

  private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

  public Users toUser(){
    Users newUser=new Users();
    newUser.setEmail(email);
    newUser.setPassword(bCryptPasswordEncoder.encode(password));
    newUser.setValidator(UUID.randomUUID().toString());
    newUser.setIsValidate(false);
    newUser.setRole(Role.USER);
    newUser.setCreateAt(new Date());
    newUser.setUpdateAt(new Date());
    return newUser;
  }
}
