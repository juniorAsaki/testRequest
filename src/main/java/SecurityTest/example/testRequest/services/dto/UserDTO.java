package SecurityTest.example.testRequest.services.dto;

import SecurityTest.example.testRequest.models.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String password;

    private boolean rememberMe;

    private RoleDTO role;

}
