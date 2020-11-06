package pl.com.decsoft.domain.addressbook.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.decsoft.domain.addressbook.AddressBookEntity;
import pl.com.decsoft.domain.addressbook.AddressBookRepository;
import pl.com.decsoft.domain.addressbook.AddressBookService;
import pl.com.decsoft.domain.addressbook.QAddressBookEntity;
import pl.com.decsoft.domain.addressbook.photo.DeletePhotoService;
import pl.com.decsoft.domain.addressbook.rest.dto.AddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.EditAddressBookDTO;

import java.util.Optional;

@RestController
@RequestMapping("/address-books")
@CrossOrigin
@RequiredArgsConstructor
public class AddressBookController {
  private final AddressBookService addressBookService;
  private final AddressBookRepository addressBookRepository;
  private final DeletePhotoService deletePhotoService;
  private final ObjectMapper objectMapper;

  private static final String LIKE_FIX = "%";

  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping
  public Page<AddressBookDTO> page(@RequestParam Optional<String> query, Pageable pageable) {
    BooleanBuilder bb = new BooleanBuilder();

    query.ifPresent(q -> bb.andAnyOf(
        bb.or(QAddressBookEntity.addressBookEntity.firstName.like(transformQuery(q))),
        bb.or(QAddressBookEntity.addressBookEntity.lastName.like(transformQuery(q))),
        bb.or(QAddressBookEntity.addressBookEntity.email.like(transformQuery(q)))));

    return Optional.ofNullable(bb.getValue())
        .map(predicate -> addressBookRepository.findAll(predicate, pageable))
        .orElseGet(() -> addressBookRepository.findAll(pageable))
        .map(AddressBookEntity::toDTO);
  }

  private String transformQuery(String q) {
    return LIKE_FIX + q + LIKE_FIX;
  }

  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public AddressBookDTO findBId(@PathVariable Long id) {
    return addressBookRepository.findById(id)
        .map(AddressBookEntity::toDTO)
        .orElseThrow(() -> new AddressBookNotFoundException(id));
  }

  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @PostMapping
  public AddressBookDTO save(@RequestPart Optional<MultipartFile> file,
                             @RequestPart String dto) throws JsonProcessingException {
    return addressBookService.create(objectMapper.readValue(dto, CreateAddressBookDTO.class), file).toDTO();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{id}")
  public AddressBookDTO edit(@PathVariable Long id,
                             @RequestPart Optional<MultipartFile> file,
                             @RequestPart String dto) throws JsonProcessingException {
    return addressBookService.update(id, objectMapper.readValue(dto, EditAddressBookDTO.class), file).toDTO();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    addressBookRepository.deleteById(id);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}/photo")
  public void deletePhoto(@PathVariable Long id) {
    AddressBookEntity addressBook = addressBookRepository.findById(id)
        .orElseThrow(() -> new AddressBookNotFoundException(id));

    deletePhotoService.delete(addressBook.getPhoto());
    addressBook = addressBook.deletePhoto(); //maybe unnecessary?

    addressBookRepository.save(addressBook);
  }
}
