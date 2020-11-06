package pl.com.decsoft.domain.addressbook.photo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.decsoft.domain.addressbook.photo.PhotoEntity;
import pl.com.decsoft.domain.addressbook.photo.PhotoRepository;

import java.sql.SQLException;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {
  private final PhotoRepository photoRepository;

  @Transactional
  @GetMapping("/{id}")
  public ResponseEntity<Resource> getPhoto(@PathVariable Long id) throws SQLException {
    PhotoEntity photoEntity = photoRepository.findById(id)
        .orElseThrow(() -> new PhotoNotFoundException(id));

    Resource inputStreamResource = new ByteArrayResource(photoEntity.getContent().getBytes(1, (int) photoEntity.getSize()));
    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "photo;filename=" + photoEntity.getDisplayName()
            .replace(" ", "_")
            .replace(",", "_"))
        .contentType(MediaType.valueOf(photoEntity.getMediaType()))
        .contentLength(photoEntity.getSize())
        .body(inputStreamResource);
  }
}
