package SecurityTest.example.testRequest.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AppUserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean rememberMe;

    @JsonIgnore
    private boolean actif = false;

    @JsonIgnore
    private RoleDTO role;

}
