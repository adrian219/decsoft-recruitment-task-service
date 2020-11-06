package pl.com.decsoft.domain.addressbook;

import org.springframework.web.multipart.MultipartFile;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.EditAddressBookDTO;

import java.util.Optional;

public interface AddressBookService {

  AddressBookEntity create(CreateAddressBookDTO dto, Optional<MultipartFile> file);

  AddressBookEntity update(Long id, EditAddressBookDTO dto, Optional<MultipartFile> file);
}
