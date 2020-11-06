package pl.com.decsoft.domain.addressbook.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@RequiredArgsConstructor
class BlobStorePhotoService implements StorePhotoService {
  private final PhotoRepository photoRepository;
  private final PhotoStoreType photoStoreType;
  private final LobHelper lobHelper;

  @Override
  public PhotoEntity upload(MultipartFile file) {
    LobHelper.BlobData blob;
    try {
      blob = lobHelper.createBlob(file.getInputStream(), file.getSize());
    } catch (IOException e) {
      throw new IllegalStateException("Cannot processing image", e);
    }

    PhotoEntity photo = PhotoEntity.builder()
        .content(blob.getBlob())
        .photoStoreType(photoStoreType)
        .fileName(FileNameParser.getFilename(file.getOriginalFilename()))
        .fileExtension(FileNameParser.getFileExtension(file.getOriginalFilename()))
        .mediaType(file.getContentType())
        .size(blob.getSize())
        .build();

    return photoRepository.save(photo);
  }
}
