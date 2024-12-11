package SecurityTest.example.testRequest.services.impl;

import SecurityTest.example.testRequest.repositories.RoleRepository;
import SecurityTest.example.testRequest.services.RoleService;
import SecurityTest.example.testRequest.services.dto.RoleDTO;
import SecurityTest.example.testRequest.services.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  public RoleDTO save(RoleDTO roleDTO) {
    return roleMapper.toDto(roleRepository.save(roleMapper.toEntity(roleDTO)));
  }

  @Override
  public Optional<RoleDTO> findByRoleName(String roleName) {
    return this.roleRepository.findByRoleName(roleName).map(roleMapper::toDto);
  }

  @Override
  public List<RoleDTO> findAll() {
    return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
  }
}
