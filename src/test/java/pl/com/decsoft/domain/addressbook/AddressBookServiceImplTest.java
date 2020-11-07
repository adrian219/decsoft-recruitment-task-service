package pl.com.decsoft.domain.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import pl.com.decsoft.DecsoftRecruitmentTaskServiceApplication;
import pl.com.decsoft.domain.addressbook.phone.PhoneType;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.EditAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.PhoneDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DecsoftRecruitmentTaskServiceApplication.class)
class AddressBookServiceImplTest {

  @Autowired
  private AddressBookServiceImpl addressBookService;

  @Autowired
  private AddressBookRepository addressBookRepository;

  @BeforeEach
  void setUp() {
    addressBookRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenPageableAndPrepareOneRecord_whenGettingListOfAddressBooks_thenReturnsOneRecords() {
    //given
    Pageable pageable = PageRequest.of(0, 2);
    AddressBookEntity addressBook = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number("503445140")
            .build()))
        .build());
    addressBook = addressBookRepository.save(addressBook);
    final Long id = addressBook.getId();

    //when
    Page<AddressBookEntity> page = addressBookService.page(Optional.empty(), pageable);

    //then
    assertEquals(1, page.stream().count());
    assertTrue(page.stream().anyMatch(item -> item.getId().equals(id)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenPageableAndPrepareTwoRecord_whenGettingListOfAddressBooksWithQueryByFirstName_thenReturnsOneRecords() {
    //given
    Pageable pageable = PageRequest.of(0, 5);
    AddressBookEntity addressBook1 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number("503445140")
            .build()))
        .build());
    addressBook1 = addressBookRepository.save(addressBook1);
    final Long id1 = addressBook1.getId();

    AddressBookEntity addressBook2 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("nowak@ziomal.pl")
        .firstName("Tomek")
        .lastName("Atomek")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.WORK)
            .number("111111111")
            .build()))
        .build());
    addressBook2 = addressBookRepository.save(addressBook2);
    final Long id2 = addressBook2.getId();

    //when
    Page<AddressBookEntity> page = addressBookService.page(Optional.of("Adrian"), pageable);

    //then
    assertEquals(1, page.stream().count());
    assertTrue(page.stream().anyMatch(item -> item.getId().equals(id1)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenPageableAndPrepareTwoRecord_whenGettingListOfAddressBooksWithQueryByPhone_thenReturnsOneRecords() {
    //given
    Pageable pageable = PageRequest.of(0, 5);
    AddressBookEntity addressBook1 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number("503445140")
            .build()))
        .build());
    addressBook1 = addressBookRepository.save(addressBook1);
    final Long id1 = addressBook1.getId();

    AddressBookEntity addressBook2 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("nowak@ziomal.pl")
        .firstName("Tomek")
        .lastName("Atomek")
        .phones(Arrays.asList(
            PhoneDTO.builder()
                .phoneType(PhoneType.WORK)
                .number("111111111")
                .build(),
            PhoneDTO.builder()
                .phoneType(PhoneType.HOME)
                .number("222222222")
                .build()))
        .build());
    addressBook2 = addressBookRepository.save(addressBook2);
    final Long id2 = addressBook2.getId();

    //when
    Page<AddressBookEntity> page = addressBookService.page(Optional.of("111111111"), pageable);

    //then
    assertEquals(1, page.stream().count());
    assertTrue(page.stream().anyMatch(item -> item.getId().equals(id2)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenPageableAndPrepareTwoRecord_whenGettingListOfAddressBooksWithQuery_thenReturnsEmptyResults() {
    //given
    Pageable pageable = PageRequest.of(0, 5);
    AddressBookEntity addressBook1 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number("503445140")
            .build()))
        .build());
    addressBookRepository.save(addressBook1);

    AddressBookEntity addressBook2 = AddressBookEntity.create(CreateAddressBookDTO.builder()
        .email("nowak@ziomal.pl")
        .firstName("Tomek")
        .lastName("Atomek")
        .phones(Arrays.asList(
            PhoneDTO.builder()
                .phoneType(PhoneType.WORK)
                .number("111111111")
                .build(),
            PhoneDTO.builder()
                .phoneType(PhoneType.HOME)
                .number("222222222")
                .build()))
        .build());
    addressBookRepository.save(addressBook2);

    //when
    Page<AddressBookEntity> page = addressBookService.page(Optional.of("undefined phrase"), pageable);

    //then
    assertEquals(0, page.stream().count());
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenCreateAddressBookDto_whenSavingObject_thenListOfAddressBooksIncreaseByOne() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number(" 444555345 ")
            .build()))
        .build();
    AddressBookEntity addressBook = addressBookService.create(dto, Optional.empty());
    long id = addressBook.getId();

    //when
    Page<AddressBookEntity> page = addressBookService.page(Optional.empty(), Pageable.unpaged());

    //then
    assertEquals(1, page.stream().count());
    assertTrue(page.stream().anyMatch(item -> item.getId().equals(id)));
    assertTrue(page.stream().anyMatch(item -> "Wieczorek".equals(item.getLastName())));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenCreateAddressBookDto_whenSavingObject_thenReturnsImplementedData() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number(" 444555345 ")
            .build()))
        .build();

    //when
    AddressBookEntity addressBook = addressBookService.create(dto, Optional.empty());
    long id = addressBook.getId();

    //then
    assertEquals(id, addressBook.getId());
    assertEquals("Wieczorek", addressBook.getLastName());
    assertEquals("Adrian", addressBook.getFirstName());
    assertEquals("theadrian219@mgail.com", addressBook.getEmail());
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getNumber().equals("444555345")));
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getPhoneType().equals(PhoneType.HOME)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenEditAddressBookDto_whenSavingObject_thenReturnEditingData() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number(" 444555345 ")
            .build()))
        .build();
    long id = addressBookService.create(dto, Optional.empty()).getId();


    EditAddressBookDTO editDto = EditAddressBookDTO.builder()
        .id(id)
        .email("innymail@gmail.pl")
        .firstName("Tomek  ")
        .lastName("Atomek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.WORK)
            .number("444444444")
            .build()))
        .build();

    //when
    AddressBookEntity addressBook = addressBookService.update(id, editDto, Optional.empty());

    //then
    assertEquals(id, addressBook.getId());
    assertEquals("Tomek", addressBook.getFirstName());
    assertEquals("Atomek", addressBook.getLastName());
    assertEquals("innymail@gmail.pl", addressBook.getEmail());
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getNumber().equals("444444444")));
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getPhoneType().equals(PhoneType.WORK)));
    assertNull(addressBook.getPhoto());
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenEditAddressBookDtoWithPhoto_whenSavingObject_thenReturnsImplementedDataWithPhoto() {
    //given
    CreateAddressBookDTO dto = CreateAddressBookDTO.builder()
        .email("theadrian219@mgail.com")
        .firstName("Adrian")
        .lastName("Wieczorek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.HOME)
            .number(" 444555345 ")
            .build()))
        .build();
    long id = addressBookService.create(dto, Optional.empty()).getId();

    EditAddressBookDTO editDto = EditAddressBookDTO.builder()
        .id(id)
        .email("innymail@gmail.pl")
        .firstName("Tomek  ")
        .lastName("Atomek ")
        .phones(Collections.singletonList(PhoneDTO.builder()
            .phoneType(PhoneType.WORK)
            .number("444444444")
            .build()))
        .build();

    MultipartFile file = new MockMultipartFile("filename.jpg", "filename.jpg", "application/jpeg", new byte[1]);

    //when
    AddressBookEntity addressBook = addressBookService.update(id, editDto, Optional.of(file));

    //then
    assertEquals(id, addressBook.getId());
    assertEquals("Tomek", addressBook.getFirstName());
    assertEquals("Atomek", addressBook.getLastName());
    assertEquals("innymail@gmail.pl", addressBook.getEmail());
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getNumber().equals("444444444")));
    assertTrue(addressBook.getPhones().stream().anyMatch(phone -> phone.getPhoneType().equals(PhoneType.WORK)));
    assertNotNull(addressBook.getPhoto());
  }
}