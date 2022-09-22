package web.restapi.boilerplate.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import web.restapi.boilerplate.models.entities.Role;
import web.restapi.boilerplate.models.enums.ERole;

public interface RoleRepo extends JpaRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
