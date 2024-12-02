package SecurityTest.example.testRequest.services;

import SecurityTest.example.testRequest.services.dto.ForumDTO;

import java.util.List;
import java.util.Optional;

public interface ForumService {

    ForumDTO createForum(ForumDTO forumDTO);
    void deleteForum(Long forumId);
    Optional<ForumDTO> getForum(Long forumId);
    List<ForumDTO> getForums();
}
