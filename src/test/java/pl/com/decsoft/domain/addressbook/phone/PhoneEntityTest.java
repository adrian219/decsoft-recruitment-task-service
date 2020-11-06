package pl.com.decsoft.domain.addressbook.phone;

import org.junit.jupiter.api.Test;
import pl.com.decsoft.domain.addressbook.AddressBookEntity;
import pl.com.decsoft.domain.addressbook.rest.dto.PhoneDTO;

import static org.junit.jupiter.api.Assertions.*;

class PhoneEntityTest {

  @Test
  void givenPhoneDTO_whenMappingToPhoneEntity_thenAlwaysFieldsWasMappingCorrectly() {
    //given
    PhoneDTO dto = PhoneDTO.builder()
        .id(1L)
        .phoneType(PhoneType.HOME)
        .number("555555555")
        .build();

    AddressBookEntity addressBookEntity = new AddressBookEntity();

    //when
    PhoneEntity phoneEntity = PhoneEntity.create(addressBookEntity, dto);
    
    //then
    assertEquals(1L, phoneEntity.getId());
    assertEquals(addressBookEntity, phoneEntity.getAddressBook());
    assertEquals(PhoneType.HOME, phoneEntity.getPhoneType());
    assertEquals("555555555", phoneEntity.getNumber());
  }

  @Test
  void givenPhoneEntity_whenMappingToPhoneDTO_thenAlwaysFieldsWasMappingCorrectly() {
    //given
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    PhoneEntity phoneEntity = PhoneEntity.create(addressBookEntity, PhoneDTO.builder()
        .id(2L)
        .phoneType(PhoneType.WORK)
        .number("323123124")
        .build());

    //when
    PhoneDTO phoneDTO = phoneEntity.toDTO();

    //then
    assertEquals(1L, phoneDTO.getId());
    assertEquals(PhoneType.WORK, phoneDTO.getPhoneType());
    assertEquals("323123124", phoneDTO.getNumber());
  }
}