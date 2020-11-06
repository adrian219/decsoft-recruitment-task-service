package pl.com.decsoft.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.com.decsoft.security.JwtTokenHelper;
import pl.com.decsoft.security.rest.dto.JwtRequestDTO;
import pl.com.decsoft.security.rest.dto.JwtResponseDTO;

@RestController
@RequestMapping("/authentication")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenHelper jwtTokenHelper;
  private final UserDetailsService userDetailsService;

  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public static class InvalidCredentialsException extends RuntimeException {
    private static final long serialVersionUID = 599423420976638L;

    public InvalidCredentialsException(Throwable e) {
      super("INVALID_CREDENTIALS", e);
    }
  }

  @PostMapping("/token")
  public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody JwtRequestDTO dto) {
    authenticate(dto.getUsername(), dto.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
    final String token = jwtTokenHelper.generateToken(userDetails);

    return ResponseEntity.ok(JwtResponseDTO.withToken(token));
  }

  private void authenticate(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException e) {
      throw new InvalidCredentialsException(e);
    }
  }
}
