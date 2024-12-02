package SecurityTest.example.testRequest.services.mapper;

import SecurityTest.example.testRequest.models.User;
import SecurityTest.example.testRequest.services.dto.UserDTO;

public class UserMapper {

    public UserDTO ToUserDTO(User user) {

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRememberMe(user.isRememberMe());
        return userDTO;
    }

    public User ToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRememberMe(userDTO.isRememberMe());
        return user;
    }
}
