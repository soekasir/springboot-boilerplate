package web.restapi.boilerplate.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.restapi.boilerplate.models.entities.User;



public interface UsersRepo extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndIsValidate(String email, Boolean isValidate);
}
