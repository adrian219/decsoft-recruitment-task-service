package pl.com.decsoft.domain.addressbook.photo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PhotoNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 23423421231L;

  public PhotoNotFoundException(Long id) {
    super(String.format("Photo not found with id: %d", id));
  }
}
