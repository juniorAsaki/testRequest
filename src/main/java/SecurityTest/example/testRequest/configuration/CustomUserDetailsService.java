package SecurityTest.example.testRequest.configuration;

import SecurityTest.example.testRequest.models.AppUser;
import SecurityTest.example.testRequest.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    return new User(appUser.getEmail() , appUser.getPassword() , getAuthority(appUser.getRole().getRoleName()));
  }

  private List<GrantedAuthority> getAuthority(String roleName) {

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(roleName));
    return authorities;
  }
}
