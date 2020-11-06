package pl.com.decsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.com.decsoft.domain.addressbook.photo.PhotoStoreProperties;
import pl.com.decsoft.security.JWTProperties;

@SpringBootApplication
@EnableConfigurationProperties({PhotoStoreProperties.class, JWTProperties.class})
public class DecsoftRecruitmentTaskServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(DecsoftRecruitmentTaskServiceApplication.class, args);
  }
}
