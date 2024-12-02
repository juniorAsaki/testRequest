package SecurityTest.example.testRequest.web;

import SecurityTest.example.testRequest.services.dto.JWTTokenDTO;
import SecurityTest.example.testRequest.services.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthenticateRessource {

    @PostMapping
    public JWTTokenDTO authenticate(@RequestBody UserDTO userDTO) {

        return null;

    }
}
