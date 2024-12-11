package SecurityTest.example.testRequest.services.impl;

import SecurityTest.example.testRequest.models.AppUser;
import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.repositories.AppUserRepository;
import SecurityTest.example.testRequest.services.AppUserService;
import SecurityTest.example.testRequest.services.EmailService;
import SecurityTest.example.testRequest.services.RoleService;
import SecurityTest.example.testRequest.services.ValidationService;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import SecurityTest.example.testRequest.services.dto.ResetPasswordDTO;
import SecurityTest.example.testRequest.services.dto.RoleDTO;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;
import SecurityTest.example.testRequest.services.mapper.AppUserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

  private final AppUserRepository appUserRepository;

  private final PasswordEncoder passwordEncoder;

  private final AppUserMapper appUserMapper;

  private final ValidationService validationService;

  private final RoleService roleService;

  private final EmailService emailService;

  private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";


  @Override
  public String inscription(AppUserDTO appUserDTO) {

    if(!appUserDTO.getEmail().matches(EMAIL_REGEX)){
      throw new IllegalArgumentException("Adress Email est invalide");
    }

    if(!appUserDTO.getEmail().contains(".")){
      throw new IllegalArgumentException("Adress Email est invalide");
    }

    AppUserDTO appUserDTO1 = this.findByEmail(appUserDTO.getEmail()).orElse(null);

    if (appUserDTO1 != null) {
      throw new IllegalArgumentException("Votre mail est déjà utilisé");
    }

    String passwordEncoded = passwordEncoder.encode(appUserDTO.getPassword());
    appUserDTO.setPassword(passwordEncoded);

    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setRoleName("ROLE_USER");

    AppUserDTO appUserSave = this.saveAppUserWithRole(appUserDTO , roleDTO);

    log.debug(appUserSave.toString());

    return validationService.enregistrer(appUserSave);
  }

  @Override
  public void activation(Map<String, String> activation) {

    log.debug(activation.toString());

    ValidationDTO validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

    if (!activation.get("code").equals(validation.getCode())) {
      throw new IllegalArgumentException("Activation code incorrect");
    }

    if (Instant.now().isAfter(validation.getExpiration())){
      throw new IllegalArgumentException("Votre code a expiré");
    }

    AppUserDTO appUserActiver = this.findById(validation.getAppUser().getId()).orElseThrow(
            () -> new RuntimeException("User not found")
    );

    appUserActiver.setActif(true);
    this.save(appUserActiver);

  }

  @Override
  public Optional<AppUserDTO> findById(Long id) {
    return appUserRepository.findById(id).map(appUserMapper::toDto);
  }

  @Override
  public AppUserDTO save(AppUserDTO appUserDTO) {

    AppUser save = appUserRepository.save(appUserMapper.toEntity(appUserDTO));

    log.debug(save.toString());

    AppUserDTO appUserDTO1 = appUserMapper.toDto(save);

    log.debug(appUserDTO1.toString());
    return appUserDTO1;
  }

  @Override
  public AppUserDTO saveAppUserWithRole(AppUserDTO appUserDTO, RoleDTO roleDTO) {

    log.debug("save appuser with role , {} : {}" , appUserDTO , roleDTO);

    appUserDTO.setRole(roleDTO);
    return this.save(appUserDTO);
  }

  @Override
  public Optional<AppUserDTO> findByEmail(String email) {
      return appUserRepository.findByEmail(email).map(appUserMapper::toDto);
  }

  @Override
  public void requestPasswordReset(String email) {
      log.debug("request password reset {}", email);

      AppUserDTO appUserDTO = this.findByEmail(email).orElse(null);
      ValidationDTO validationDTO = validationService.generateOtp(appUserDTO);

      emailService.envoyer(validationDTO);
  }

  @Override
  public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
      log.debug("reset password {}", resetPasswordDTO);

    String code = String.valueOf(resetPasswordDTO.getCode());

      ValidationDTO validationDTO = validationService.lireEnFonctionDuCode(code);


    if (!resetPasswordDTO.getCode().equals(validationDTO.getCode())) {
      throw new IllegalArgumentException("Activation code incorrect");
    }

    if (Instant.now().isAfter(validationDTO.getExpiration())){
      throw new IllegalArgumentException("Votre code a expiré");
    }

    AppUserDTO appUserDTO = this.findByEmail(resetPasswordDTO.getEmail()).orElseThrow(
            () -> new RuntimeException("User not found")
    );

    appUserDTO.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
    this.save(appUserDTO);

  }
}
