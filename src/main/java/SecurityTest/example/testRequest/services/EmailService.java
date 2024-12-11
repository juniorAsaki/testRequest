package SecurityTest.example.testRequest.services;

import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;

public interface EmailService {

  void envoyer(ValidationDTO validation);
}
