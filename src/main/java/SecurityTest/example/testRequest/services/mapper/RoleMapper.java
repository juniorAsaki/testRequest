package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.Role;
import SecurityTest.example.testRequest.services.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
