package web.restapi.boilerplate.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.restapi.boilerplate.helpers.BaseResponse;
import web.restapi.boilerplate.models.entities.Users;
import web.restapi.boilerplate.models.repositories.UsersRepo;
import web.restapi.boilerplate.modules.auth.dto.SignInDto;
import web.restapi.boilerplate.modules.auth.dto.SignUpDto;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class AuthService {
  @Autowired
  private UsersRepo usersRepo;

  public BaseResponse signUp(SignUpDto signUpDto){
    BaseResponse response=new BaseResponse();

    Users user=usersRepo.findByEmail(signUpDto.getEmail()).orElse(null);
    if(user != null){
      response.setMessage("email sudah digunakan");
      return response;
    }

    if(!signUpDto.getPassword().equals(signUpDto.getPassword2())){
      response.setMessage("password tidak sama");
      return response;
    }

    Users newUser=signUpDto.toUser();

    usersRepo.save(newUser);

    response.setSuccess(true);
    response.setMessage("berhasil sign-up");

    newUser.setPassword(null);
    response.setData(newUser);

    return response;
  }


  public BaseResponse signIn(SignInDto dto){
    BaseResponse response=new BaseResponse();
    Users user=usersRepo.findByEmail(dto.getEmail()).orElse(null);
    if(user==null){
      response.setMessage("email tidak ditemukan");
      return response;
    }

    if(!BCrypt.checkpw(dto.getPassword(), user.getPassword())){
      response.setMessage("password salah");
      return response;
    }

    response.setSuccess(true);
    response.setMessage("berhasil login");

    user.setValidator(null);
    user.setPassword(null);

    response.setData(user);

    return response;
  }
}
