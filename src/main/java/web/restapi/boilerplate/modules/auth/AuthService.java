package web.restapi.boilerplate.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import web.restapi.boilerplate.helpers.BaseResponse;
import web.restapi.boilerplate.models.entities.User;
import web.restapi.boilerplate.models.repositories.UsersRepo;
import web.restapi.boilerplate.modules.auth.dto.SignInDto;
import web.restapi.boilerplate.modules.auth.dto.SignUpDto;

@Service
public class AuthService {
  @Autowired
  private UsersRepo usersRepo;

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
      User newUser = dto.toUser();
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
      response.setSuccess(true);
      response.setMessage("berhasil login");
      response.setData(user.toResponseData());
      return response;
    }
  }

}
