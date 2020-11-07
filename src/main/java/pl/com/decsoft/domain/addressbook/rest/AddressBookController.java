package pl.com.decsoft.domain.addressbook.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.decsoft.domain.addressbook.AddressBookEntity;
import pl.com.decsoft.domain.addressbook.AddressBookRepository;
import pl.com.decsoft.domain.addressbook.AddressBookService;
import pl.com.decsoft.domain.addressbook.photo.DeletePhotoService;
import pl.com.decsoft.domain.addressbook.rest.dto.AddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.EditAddressBookDTO;

import javax.validation.Valid;
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

  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping
  public Page<AddressBookDTO> page(@RequestParam Optional<String> query, Pageable pageable) {
    return addressBookService.page(query, pageable)
        .map(AddressBookEntity::toDTO);
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

    @Valid
    CreateAddressBookDTO createAddressBookDTO = objectMapper.readValue(dto, CreateAddressBookDTO.class);

    return addressBookService.create(createAddressBookDTO, file).toDTO();
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{id}")
  public AddressBookDTO edit(@PathVariable Long id,
                             @RequestPart Optional<MultipartFile> file,
                             @RequestPart String dto) throws JsonProcessingException {

    @Valid
    EditAddressBookDTO editAddressBookDTO = objectMapper.readValue(dto, EditAddressBookDTO.class);

    return addressBookService.update(id, editAddressBookDTO, file).toDTO();
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
