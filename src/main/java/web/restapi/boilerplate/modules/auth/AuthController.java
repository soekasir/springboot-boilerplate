package web.restapi.boilerplate.modules.auth;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.restapi.boilerplate.modules.auth.dto.SignInDto;
import web.restapi.boilerplate.modules.auth.dto.SignUpDto;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("auth")
public class AuthController {
  @Autowired
  AuthService authService;  

  @PostMapping("signup")
  public Object signUp(@RequestBody SignUpDto req) {
    return authService.signUp(req);
  }

  @PostMapping("signin")
  public Object signIn(@RequestBody SignInDto req) {
    return authService.signIn(req);
  }
}
