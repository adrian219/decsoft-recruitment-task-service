package pl.com.decsoft.domain.addressbook.photo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileNameParser {

  public static String getFileExtension(String filename) {
    if (filename == null) {
      return null;
    }
    int dotIndex = filename.lastIndexOf(".");

    if (dotIndex == -1) return null;

    return filename.substring(dotIndex+1);
  }

  public static String getFilename(String filename) {
    if (filename == null) {
      return null;
    }
    int dotIndex = filename.lastIndexOf(".");

    if (dotIndex == -1) return filename;

    return filename.substring(0, dotIndex);
  }
}
