package SecurityTest.example.testRequest.repositories;

import SecurityTest.example.testRequest.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum , Long> {
}
