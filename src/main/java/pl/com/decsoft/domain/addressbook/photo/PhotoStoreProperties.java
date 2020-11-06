package pl.com.decsoft.domain.addressbook.photo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("photo-store")
public class PhotoStoreProperties {

  private final PhotoStoreType type = PhotoStoreType.BLOB;
  private final Config config = new Config();

  @Getter
  @Setter
  public static class Config {
    private Resource resource = new Resource();
  }

  @Getter
  @Setter
  public static class Resource {
    private String path;
    private String accessId;
    private String secretId;
  }
}
