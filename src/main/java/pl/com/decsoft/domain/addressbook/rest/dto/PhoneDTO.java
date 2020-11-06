package pl.com.decsoft.domain.addressbook.rest.dto;

import lombok.Builder;
import lombok.Getter;
import pl.com.decsoft.domain.addressbook.phone.PhoneType;

@Getter
@Builder
public class PhoneDTO {
  private final Long id;
  private final PhoneType phoneType;
  private final String number;
}
