package pl.com.decsoft.security.rest.dto;

import lombok.Getter;

@Getter
public class JwtRequestDTO {
  private String username;
  private String password;
}
