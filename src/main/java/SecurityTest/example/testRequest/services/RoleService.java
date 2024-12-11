package SecurityTest.example.testRequest.services;

import SecurityTest.example.testRequest.services.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {

  RoleDTO save(RoleDTO roleDTO);

  Optional<RoleDTO> findByRoleName(String roleName);

  List<RoleDTO> findAll();
}
