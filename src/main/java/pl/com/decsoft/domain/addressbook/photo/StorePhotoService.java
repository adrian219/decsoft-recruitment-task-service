package pl.com.decsoft.domain.addressbook.photo;

import org.springframework.web.multipart.MultipartFile;

public interface StorePhotoService {

  PhotoEntity upload(MultipartFile file);
}
