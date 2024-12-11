package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.Forum;
import SecurityTest.example.testRequest.services.dto.ForumDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForumMapper extends EntityMapper<ForumDTO, Forum> {

}
