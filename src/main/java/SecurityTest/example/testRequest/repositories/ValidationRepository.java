package SecurityTest.example.testRequest.repositories;

import SecurityTest.example.testRequest.models.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
  Optional<Validation> findByCode(String code);

}
