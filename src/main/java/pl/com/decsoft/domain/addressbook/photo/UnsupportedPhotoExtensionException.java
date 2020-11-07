package pl.com.decsoft.domain.addressbook.photo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UnsupportedPhotoExtensionException extends RuntimeException {
  private static final long serialVersionUID = 243242638L;

  public UnsupportedPhotoExtensionException() {
    super("Unsupported photo extension!");
  }
}
