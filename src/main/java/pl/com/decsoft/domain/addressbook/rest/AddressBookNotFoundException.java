package pl.com.decsoft.domain.addressbook.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AddressBookNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 123123211231L;

  public AddressBookNotFoundException(Long id) {
    super(String.format("Address book not found with id: %d", id));
  }
}
