package SecurityTest.example.testRequest.services.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class ValidationDTO {

  private Instant creation;
  private Instant expiration;
  private Instant activation;
  private String code;

  private AppUserDTO appUser;
}
