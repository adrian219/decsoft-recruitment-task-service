package pl.com.decsoft.domain.addressbook.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
class ResourceStorePhotoService implements StorePhotoService {
  private final PhotoRepository photoRepository;
  private final PhotoStoreType photoStoreType;

  @Override
  public PhotoEntity upload(MultipartFile file) {
    throw new IllegalStateException("Not implemented yet!");
  }
}
