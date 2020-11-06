package pl.com.decsoft.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

    return new User(user.getUsername(), user.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())));
  }
}
