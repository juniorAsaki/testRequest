package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidationMapper extends EntityMapper<ValidationDTO, Validation> {

}
