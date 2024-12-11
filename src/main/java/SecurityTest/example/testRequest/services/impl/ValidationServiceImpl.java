package SecurityTest.example.testRequest.services.impl;

import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.repositories.ValidationRepository;
import SecurityTest.example.testRequest.services.EmailService;
import SecurityTest.example.testRequest.services.ValidationService;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;
import SecurityTest.example.testRequest.services.mapper.AppUserMapper;
import SecurityTest.example.testRequest.services.mapper.ValidationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class ValidationServiceImpl implements ValidationService {


  private  ValidationRepository validationRepository;
  private EmailService emailService;
  private ValidationMapper validationMapper;


  @Override
  public String enregistrer(AppUserDTO appUserDTO) {

    ValidationDTO validationDTO = generateOtp(appUserDTO);

    ValidationDTO validationSave = this.save(validationDTO);
    emailService.envoyer(validationSave);

    return "Votre code de validation vous a été envoyer par email !";

  }

  @Override
  public ValidationDTO generateOtp(AppUserDTO appUserDTO) {

    ValidationDTO validationDTO = new ValidationDTO();
    validationDTO.setAppUser(appUserDTO);

    Instant creation = Instant.now();
    validationDTO.setCreation(creation);

    Instant expiration = creation.plus(10 , ChronoUnit.MINUTES);
    validationDTO.setExpiration(expiration);

    Random random = new Random();
    int randomInt = random.nextInt(999999);
    String code = String.format("%06d", randomInt);

    validationDTO.setCode(code);

    return this.save(validationDTO);
  }

  @Override
  public ValidationDTO lireEnFonctionDuCode(String code) {

    log.debug(code);

    ValidationDTO validationDTO = this.validationRepository.findByCode(code).map(
            validationMapper::toDto
    ).orElse(null);

    return validationDTO;
  }

  @Override
  public ValidationDTO save(ValidationDTO validationDTO) {
    log.debug("save Validation {}", validationMapper.toEntity(validationDTO));


    return validationMapper.toDto(validationRepository.save(validationMapper.toEntity(validationDTO)));
  }
}
