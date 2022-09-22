package web.restapi.boilerplate.models.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UsersRepo extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndIsValidate(String email, Boolean isValidate);
  Optional<User> findByUsername(String username);
}
