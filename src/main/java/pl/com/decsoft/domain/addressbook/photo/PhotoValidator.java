package pl.com.decsoft.domain.addressbook.photo;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoValidator {
  private static final List<String> SUPPORTED_EXTENSIONS = ImmutableList.of("png", "jpg", "jpeg");

  public boolean validate(String extension) {
    return Optional.ofNullable(extension)
        .map(this::trim)
        .map(e -> SUPPORTED_EXTENSIONS.contains(e.toLowerCase()))
        .orElse(false);
  }

  public boolean validate(MultipartFile file) {
    return Optional.ofNullable(file)
        .map(f -> validate(FileNameParser.getFileExtension(f.getOriginalFilename())))
        .orElse(false);
  }

  private String trim(String extension) {
    return extension.indexOf(".") == 0 ? extension.substring(1) : extension;
  }
}
