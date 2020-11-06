package pl.com.decsoft.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("jwt")
public class JWTProperties {

  private final Config config = new Config();

  @Getter
  @Setter
  public static class Config {
    private Long validTime;
    private String secret;
  }
}
