package SecurityTest.example.testRequest;

import SecurityTest.example.testRequest.services.RoleService;
import SecurityTest.example.testRequest.services.dto.RoleDTO;
import SecurityTest.example.testRequest.services.enumeration.TypeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TestRequestApplication {

	private final RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(TestRequestApplication.class, args);
	}


}
