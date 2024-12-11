package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.AppUser;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {

}
