package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.Forum;
import SecurityTest.example.testRequest.services.dto.ForumDTO;
import org.springframework.stereotype.Component;

@Component
public class ForumMapper {

    public Forum toForum(ForumDTO forumDTO) {

        if (forumDTO == null) {
            return null;
        }

        Forum forum = new Forum();
        forum.setTitle(forumDTO.getTitle());
        forum.setDescription(forumDTO.getDescription());
        return forum;
    }

    public ForumDTO toForumDTO(Forum forum) {
        if (forum == null) {
            return null;
        }

        ForumDTO forumDTO = new ForumDTO();
        forumDTO.setTitle(forum.getTitle());
        forumDTO.setDescription(forum.getDescription());
        return forumDTO;
    }
}
