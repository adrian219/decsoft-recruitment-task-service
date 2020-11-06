package pl.com.decsoft.domain.addressbook.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoStoreFactory {
  private final PhotoRepository photoRepository;
  private final LobHelper lobHelper;
  private final PhotoStoreProperties properties;

  @Bean
  public StorePhotoService storePhotoService() {
    switch (properties.getType()) {
      case BLOB:
        return new BlobStorePhotoService(photoRepository, PhotoStoreType.BLOB, lobHelper);
      case RESOURCE:
        return new ResourceStorePhotoService(photoRepository, PhotoStoreType.RESOURCE);
      default:
        throw new IllegalArgumentException("Illegal parameter service photo (store action): " + properties.getType());
    }
  }

  @Bean
  public DeletePhotoService deletePhotoService() {
    switch (properties.getType()) {
      case BLOB:
        return new BlobDeletePhotoService(photoRepository);
      case RESOURCE:
        return new ResourceDeletePhotoService(photoRepository);
      default:
        throw new IllegalArgumentException("Illegal parameter service photo (delete action): " + properties.getType());
    }
  }
}
