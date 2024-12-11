package SecurityTest.example.testRequest.services;

import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;

public interface ValidationService {

  String enregistrer(AppUserDTO appUserDTO);

  ValidationDTO lireEnFonctionDuCode(String code);

  ValidationDTO save(ValidationDTO validationDTO);

  ValidationDTO generateOtp(AppUserDTO appUserDTO);
}
