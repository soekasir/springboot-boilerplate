package web.restapi.boilerplate.modules.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import web.restapi.boilerplate.helpers.BaseResponse;
import web.restapi.boilerplate.models.role.ERole;
import web.restapi.boilerplate.models.role.Role;
import web.restapi.boilerplate.models.role.RoleRepo;
import web.restapi.boilerplate.models.user.User;
import web.restapi.boilerplate.models.user.UsersRepo;
import web.restapi.boilerplate.modules.auth.dto.SignInDto;
import web.restapi.boilerplate.modules.auth.dto.SignUpDto;
import web.restapi.boilerplate.security.jwt.JwtHelper;

@Service
public class AuthService {
  @Autowired
  private UsersRepo usersRepo;
  @Autowired
  private RoleRepo roleRepo;

  // @Autowired
  private JwtHelper jwtHelper;

  public AuthService(JwtHelper jwtHelper){
    this.jwtHelper=jwtHelper;
  }

  public BaseResponse signUp(SignUpDto dto) {
    BaseResponse response = new BaseResponse();
    User user = usersRepo.findByEmail(dto.getEmail()).orElse(null);

    {//failed to signup
      if (user != null) {
        response.setMessage("email sudah digunakan");
        return response;
      }
  
      if (!dto.getPassword().equals(dto.getPassword2())) {
        response.setMessage("password tidak sama");
        return response;
      }
    }

    {//succes to sign up
      User newUser = signUpDtotoUser(dto);
      usersRepo.save(newUser);
      response.setSuccess(true);
      response.setMessage("berhasil sign-up");
      response.setData(newUser.toResponseData());
      return response;
    }
  }

  public BaseResponse signIn(SignInDto dto) {
    BaseResponse response = new BaseResponse();
    User user = usersRepo.findByEmail(dto.getEmail()).orElse(null);

    { // failed to login
      if (user == null) {
        response.setMessage("email tidak ditemukan");
        return response;
      }

      if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
        response.setMessage("password salah");
        return response;
      }

      if (!user.getIsValidate()) {
        response.setMessage("email belum divalidasi");
        return response;
      }
    }

    { // success login
      String token=jwtHelper.generateToken(user.getEmail());

      response.setSuccess(true);
      response.setMessage("berhasil login");

      response.put("token", token);
      response.put("user",user.toResponseData());
      return response;
    }
  }

  public User signUpDtotoUser(SignUpDto dto) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    User newUser = new User();
    newUser.setEmail(dto.getEmail());
    newUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
    newUser.setValidator(UUID.randomUUID().toString());
    newUser.setIsValidate(false);
    newUser.setCreateAt(new Date());
    newUser.setUpdateAt(new Date());
    Set<String> strRoles=dto.getRoles();
    Set<Role> roles=new HashSet<Role>();

    if (strRoles==null) {
      Role userRole = roleRepo.findByName(ERole.USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepo.findByName(ERole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
            case "superadmin":
            Role superAdminRole = roleRepo.findByName(ERole.SUPERADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(superAdminRole);

            break;
          default:
            Role userRole = roleRepo.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    newUser.setRoles(roles);
    return newUser;
  }

}
