package pl.com.decsoft.domain.addressbook.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateAddressBookDTO {
  private String firstName;
  private String lastName;
  private String email;
  private List<PhoneDTO> phones;
}
