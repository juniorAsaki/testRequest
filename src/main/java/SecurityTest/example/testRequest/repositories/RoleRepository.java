package SecurityTest.example.testRequest.repositories;

import SecurityTest.example.testRequest.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleName(String roleName);
}
