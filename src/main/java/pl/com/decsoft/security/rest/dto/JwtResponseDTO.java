package pl.com.decsoft.security.rest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtResponseDTO {
  private String token;

  public static JwtResponseDTO withToken(String token) {
    JwtResponseDTO dto = new JwtResponseDTO();
    dto.token = token;
    return dto;
  }
}
