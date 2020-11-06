package pl.com.decsoft.domain.addressbook;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.com.decsoft.domain.addressbook.phone.PhoneEntity;
import pl.com.decsoft.domain.addressbook.photo.PhotoEntity;
import pl.com.decsoft.domain.addressbook.rest.dto.AddressBookDTO;
import pl.com.decsoft.domain.addressbook.rest.dto.CreateAddressBookDTO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_address_books")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AddressBookEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Email
  @Column(name = "email")
  private String email;

  @OneToMany(mappedBy = "addressBook", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PhoneEntity> phones = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  private PhotoEntity photo;

  @Version
  @Column(name = "version")
  private Integer version;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private Instant createdDate;

  @LastModifiedBy
  @Column(name = "modified_by")
  private String modifiedBy;

  @LastModifiedDate
  @Column(name = "modified_date")
  private Instant modifiedDate;

  @PrePersist
  void onPrePersist() {
    createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
    createdDate = Instant.now();
    modifiedBy = createdBy;
    modifiedDate = createdDate;
  }

  @PreUpdate
  void onPreUpdate() {
    modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
    modifiedDate = Instant.now();
  }

  public static AddressBookEntity create(CreateAddressBookDTO dto) {
    AddressBookEntity entity = new AddressBookEntity();
    Set<PhoneEntity> phones = dto.getPhones()
        .stream()
        .map(phoneDTO -> PhoneEntity.create(entity, phoneDTO))
        .collect(Collectors.toSet());

    return entity.update(dto.getFirstName(), dto.getLastName(), dto.getEmail(), phones);
  }

  public AddressBookEntity update(String firstName, String lastName, String email, Set<PhoneEntity> phones) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    updatePhones(phones);

    return this;
  }

  public AddressBookEntity updatePhones(Set<PhoneEntity> phones) {
    this.phones.retainAll(phones);
    this.phones.addAll(phones);
    return this;
  }

  public AddressBookEntity updatePhoto(PhotoEntity photo) {
    this.photo = photo;
    return this;
  }

  public AddressBookEntity deletePhoto() {
    this.photo = null;
    return this;
  }

  public AddressBookDTO toDTO() {
    return AddressBookDTO.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .phones(Optional.ofNullable(phones)
            .map(phoneEntities -> phoneEntities
                .stream()
                .map(PhoneEntity::toDTO)
                .collect(Collectors.toList()))
            .orElseGet(LinkedList::new))
        .photoId(Optional.ofNullable(photo)
            .map(PhotoEntity::getId)
            .orElse(null))
        .build();
  }
}
