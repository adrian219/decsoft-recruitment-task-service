package pl.com.decsoft.domain.addressbook.rest.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class EditAddressBookDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private List<PhoneDTO> phones;
  private Long photoId;
}
