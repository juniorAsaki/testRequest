package SecurityTest.example.testRequest.services.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDTO {

  @NotBlank(message = "L'email est requis")
  @Email(message = "L'email doit être valide")
  private String email;

  @NotBlank(message = "Le code OTP est requis")
  private Integer code;

  @NotBlank(message = "Le mot de passe est requis")
  private String newPassword;

  @NotBlank(message = "La confirmation du mot de passe est requise")
  private String confirmPassword;
}
