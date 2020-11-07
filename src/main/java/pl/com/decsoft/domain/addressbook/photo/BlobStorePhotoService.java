package pl.com.decsoft.domain.addressbook.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
class BlobStorePhotoService implements StorePhotoService {
  private final PhotoRepository photoRepository;
  private final LobHelper lobHelper;
  private final PhotoValidator photoValidator;

  @Override
  public PhotoEntity upload(MultipartFile file) {
    String fileExtension = FileNameParser.getFileExtension(file.getOriginalFilename());

    validatePhoto(fileExtension);

    LobHelper.BlobData blob;
    try {
      blob = lobHelper.createBlob(file.getInputStream(), file.getSize());
    } catch (IOException e) {
      throw new IllegalStateException("Cannot processing image", e);
    }

    PhotoEntity photo = PhotoEntity.builder()
        .content(blob.getBlob())
        .photoStoreType(PhotoStoreType.BLOB)
        .fileName(FileNameParser.getFilename(file.getOriginalFilename()))
        .fileExtension(fileExtension)
        .mediaType(file.getContentType())
        .size(blob.getSize())
        .build();

    return photoRepository.save(photo);
  }

  private void validatePhoto(String fileExtension) {
    if (!photoValidator.validate(fileExtension)) {
      throw new UnsupportedPhotoExtensionException();
    }
  }
}
