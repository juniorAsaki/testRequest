package SecurityTest.example.testRequest.web;

import SecurityTest.example.testRequest.configuration.JwtUtils;
import SecurityTest.example.testRequest.services.AppUserService;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import SecurityTest.example.testRequest.services.dto.AuthentificationDTO;
import SecurityTest.example.testRequest.services.dto.EmailDTO;
import SecurityTest.example.testRequest.services.dto.ResetPasswordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticateRessource {

  private final AppUserService appUserService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("inscription")
  public ResponseEntity<?> inscription(@RequestBody AppUserDTO appUserDTO) {
    log.debug("Received request to inscription {}" , appUserDTO);
    try {
      return new ResponseEntity<>(this.appUserService.inscription(appUserDTO) , HttpStatus.OK);
    }catch (Exception e){
      System.err.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Probleme de connexion !!");
    }
  }

  @PostMapping("connexion")
  public ResponseEntity<?> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
    log.debug("Received request to connexion {}" , authentificationDTO);

    try {
      final Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authentificationDTO.email() , authentificationDTO.password())
      );

      if (authentication.isAuthenticated()){
        Map<String , String> authData = new HashMap<>();
        authData.put("token" , this.jwtUtils.generateToken(authentificationDTO.email()));
        authData.put("type" , "Bearer");
        return new ResponseEntity<>(authData , HttpStatus.OK);
      }

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password");
    }catch (AuthenticationException authException){
      log.error(authException.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authException.getMessage());
    }
  }

  @PostMapping("activation")
  public ResponseEntity<?> activationOfCodeOTP(@RequestBody Map<String, String> activation) {
    this.appUserService.activation(activation);
    return new ResponseEntity<>("Email confirmer avec succèss !!" , HttpStatus.OK);
  }

  @PostMapping("reset-password-00")
  public ResponseEntity<?> resetPassword00(@RequestBody EmailDTO emailDTO) {
    log.debug("Received request to reset password {}" , emailDTO);

    AppUserDTO appUserDTO = appUserService.findByEmail(emailDTO.email()).orElse(null);

    if (appUserDTO == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found");
    }

    try {
      appUserService.requestPasswordReset(appUserDTO.getEmail());
      return ResponseEntity.status(HttpStatus.OK).body("Votre code de réinitialisation vous a été envoyer par email");
    }catch (Exception e){
      System.err.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Probleme de connexion !!");
    }
  }

  @PostMapping("reset-password-01")
  public ResponseEntity<?> resetPassword01(@RequestBody ResetPasswordDTO resetPasswordDTO) {
    log.debug("Received request to reset password {}" , resetPasswordDTO);

    if (resetPasswordDTO.getCode() != null) {
      appUserService.resetPassword(resetPasswordDTO);
      return ResponseEntity.status(HttpStatus.OK).body("Mot de passe réinitialiser avec succès");
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Reinitialisation du mot de passe a échouée !");
  }

}
