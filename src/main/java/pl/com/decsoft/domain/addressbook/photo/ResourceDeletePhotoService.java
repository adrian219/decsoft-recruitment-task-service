package pl.com.decsoft.domain.addressbook.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceDeletePhotoService implements DeletePhotoService {
  private final PhotoRepository photoRepository;

  @Override
  public void delete(PhotoEntity photo) {
    throw new IllegalStateException("Not implemented yet!");

//    photoRepository.delete(photo);
  }
}
