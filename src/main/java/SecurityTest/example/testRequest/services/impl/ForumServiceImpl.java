package SecurityTest.example.testRequest.services.impl;

import SecurityTest.example.testRequest.models.Forum;
import SecurityTest.example.testRequest.repositories.ForumRepository;
import SecurityTest.example.testRequest.services.ForumService;
import SecurityTest.example.testRequest.services.dto.ForumDTO;
import SecurityTest.example.testRequest.services.mapper.ForumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

    private final ForumMapper forumMapper;

    @Override
    public ForumDTO createForum(ForumDTO forumDTO) {

        Forum forum = forumMapper.toForum(forumDTO);
        forum.setCreated(Instant.now());

        return forumMapper.toForumDTO(forumRepository.save(forum));
    }

    @Override
    public void deleteForum(Long forumId) {
        forumRepository.deleteById(forumId);
    }

    @Override
    public Optional<ForumDTO> getForum(Long forumId) {
        return forumRepository.findById(forumId).map(forumMapper::toForumDTO);
    }

    @Override
    public List<ForumDTO> getForums() {
        return forumRepository.findAll().stream().map(forumMapper::toForumDTO).collect(Collectors.toList());
    }
}
