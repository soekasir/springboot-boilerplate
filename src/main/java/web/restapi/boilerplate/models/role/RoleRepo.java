package web.restapi.boilerplate.models.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
