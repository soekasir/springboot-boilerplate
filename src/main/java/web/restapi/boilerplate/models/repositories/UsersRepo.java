package web.restapi.boilerplate.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.restapi.boilerplate.models.entities.Users;


public interface UsersRepo extends JpaRepository<Users, String> {
  Optional<Users> findByEmail(String email);
}
