package web.restapi.boilerplate.modules.auth;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import web.restapi.boilerplate.modules.auth.dto.SignInDto;
import web.restapi.boilerplate.modules.auth.dto.SignUpDto;


@RestController
@RequestMapping("api/auth")
public class AuthController {
  @Autowired
  AuthService authService;  

  @PostMapping("signup")
  public Object signUp(@RequestBody SignUpDto dto) {
    return authService.signUp(dto);
  }

  @PostMapping("signin")
  public Object signIn(@RequestBody SignInDto dto) {
    return authService.signIn(dto);
  }
}
