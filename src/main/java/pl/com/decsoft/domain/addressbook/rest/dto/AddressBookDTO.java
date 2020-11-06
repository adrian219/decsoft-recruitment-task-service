package pl.com.decsoft.domain.addressbook.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AddressBookDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private List<PhoneDTO> phones;
  private String email;
  private Long photoId;
}
