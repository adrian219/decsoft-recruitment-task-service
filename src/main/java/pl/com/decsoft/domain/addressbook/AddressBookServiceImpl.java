package pl.com.decsoft.domain.addressbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.decsoft.domain.addressbook.phone.PhoneEntity;
import pl.com.decsoft.domain.addressbook.phone.PhoneRepository;
import pl.com.decsoft.domain.addressbook.photo.DeletePhotoService;
import pl.com.decsoft.domain.addressbook.photo.StorePhotoService;
import pl.com.decsoft.domain.addressbook.rest.AddressBookNotFoundException;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.EditAddressBookDTO;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressBookServiceImpl implements AddressBookService {
  private final AddressBookRepository addressBookRepository;
  private final PhoneRepository phoneRepository;
  private final StorePhotoService storePhotoService;
  private final DeletePhotoService deletePhotoService;

  @Override
  public AddressBookEntity create(CreateAddressBookDTO dto, Optional<MultipartFile> file) {
    AddressBookEntity addressBook = AddressBookEntity.create(dto);

    file.ifPresent(f -> addressBook.updatePhoto(storePhotoService.upload(f)));

    return addressBookRepository.save(addressBook);
  }

  @Override
  public AddressBookEntity update(Long id, EditAddressBookDTO dto, Optional<MultipartFile> file) {
    AddressBookEntity addressBook = addressBookRepository.findById(id)
        .orElseThrow(() -> new AddressBookNotFoundException(id));

    Set<PhoneEntity> phones = getPhoneEntities(dto, addressBook);
    addressBook.update(dto.getFirstName(), dto.getLastName(), dto.getEmail(), phones);

    Optional.ofNullable(dto.getPhotoId())
        .ifPresent(photoId -> addressBook.deletePhoto());

    file.ifPresent(f -> {
      Optional.ofNullable(addressBook.getPhoto())
          .ifPresent(p -> deletePhotoService.delete(addressBook.getPhoto()));
      addressBook.updatePhoto(storePhotoService.upload(f));
    });

    return addressBookRepository.save(addressBook);
  }

  private Set<PhoneEntity> getPhoneEntities(EditAddressBookDTO dto, AddressBookEntity addressBook) {
    return dto.getPhones()
        .stream()
        .map(item -> Optional.ofNullable(item.getId())
            .map(phoneRepository::findById)
            .orElseGet(() -> Optional.of(PhoneEntity.create(addressBook, item)))
            .map(phone -> phone.update(item.getPhoneType(), item.getNumber())))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }
}
