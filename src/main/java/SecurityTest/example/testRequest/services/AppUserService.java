package SecurityTest.example.testRequest.services;


import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import SecurityTest.example.testRequest.services.dto.ResetPasswordDTO;
import SecurityTest.example.testRequest.services.dto.RoleDTO;

import java.util.Map;
import java.util.Optional;

public interface AppUserService {

    String inscription(AppUserDTO appUserDTO);

    void activation(Map<String, String> activation);

    Optional<AppUserDTO> findById(Long id);

    AppUserDTO save(AppUserDTO appUserDTO);

    AppUserDTO saveAppUserWithRole(AppUserDTO appUserDTO , RoleDTO roleDTO);

    Optional<AppUserDTO> findByEmail(String email);

    void requestPasswordReset(String email);

    void resetPassword(ResetPasswordDTO resetPasswordDTO);

}
