package SecurityTest.example.testRequest.web;

import SecurityTest.example.testRequest.services.ForumService;
import SecurityTest.example.testRequest.services.dto.ForumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/forum")
public class ForumResource {

    private final ForumService forumService;

    @PostMapping
    public ForumDTO createForum(@RequestBody ForumDTO forumDTO) {
        return forumService.createForum(forumDTO);
    }

    @GetMapping
    public List<ForumDTO> getAllForums() {
        return forumService.getForums();
    }

    @GetMapping("/{id}")
    public ForumDTO getForumById(@PathVariable Long id) {
        return forumService.getForum(id).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
    }

}
