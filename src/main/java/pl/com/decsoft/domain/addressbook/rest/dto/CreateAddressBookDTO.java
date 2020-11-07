package pl.com.decsoft.domain.addressbook.rest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class CreateAddressBookDTO {
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private String email;
  private List<PhoneDTO> phones;
}
