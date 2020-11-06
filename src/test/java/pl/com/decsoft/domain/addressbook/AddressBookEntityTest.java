package pl.com.decsoft.domain.addressbook;

import org.junit.jupiter.api.Test;
import pl.com.decsoft.domain.addressbook.photo.PhotoEntity;
import pl.com.decsoft.domain.addressbook.rest.dto.AddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.PhoneDTO;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookEntityTest {

  @Test
  void givenCreateAddressBookDTO_whenMappingToAddressBookEntity_thenAlwaysFieldsWasMappingCorrectly() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .firstName("Adrian")
        .lastName("Wieczorek")
        .email("email@mail.pl")
        .phones(new LinkedList<>())
        .build();

    //when
    AddressBookEntity addressBookEntity = AddressBookEntity.create(dto);

    //then
    assertEquals("Adrian", addressBookEntity.getFirstName());
    assertEquals("Wieczorek", addressBookEntity.getLastName());
    assertEquals("email@mail.pl", addressBookEntity.getEmail());
    assertEquals(0, addressBookEntity.getPhones().size());
  }

  @Test
  void givenAddressBookEntity_whenMappingToAddressBookDTO_thenAlwaysFieldsWasMappingCorrectly() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .firstName("Tomek")
        .lastName("Atomek")
        .email("inny_mail@gmail.com")
        .phones(Collections.singletonList(PhoneDTO.builder().build()))
        .build();
    AddressBookEntity addressBookEntity = AddressBookEntity.create(dto);
    addressBookEntity.updatePhoto(PhotoEntity.builder()
        .id(18L)
        .build());

    //when
    AddressBookDTO addressBookDTO = addressBookEntity.toDTO();

    //then
    assertEquals("Tomek", addressBookDTO.getFirstName());
    assertEquals("Atomek", addressBookDTO.getLastName());
    assertEquals("inny_mail@gmail.com", addressBookDTO.getEmail());
    assertEquals(1, addressBookDTO.getPhones().size());
    assertEquals(18L, addressBookEntity.getPhoto().getId());
  }
}