package SecurityTest.example.testRequest.services.mapper;

public interface EntityMapper <D , E>{
  E toEntity(D d);
  D toDto(E e);
}
