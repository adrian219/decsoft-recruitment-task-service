package pl.com.decsoft.domain.addressbook.phone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.com.decsoft.domain.addressbook.AddressBookEntity;
import pl.com.decsoft.domain.addressbook.rest.dto.PhoneDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "t_phones")
@Getter
@NoArgsConstructor
public class PhoneEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @NotNull
  @JoinColumn(name = "address_book_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private AddressBookEntity addressBook;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "phone_type")
  private PhoneType phoneType;

  @Length(max = 70)
  @Column(name = "number")
  private String number;

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

  private PhoneEntity(AddressBookEntity addressBook, PhoneType phoneType, String number) {
    this.addressBook = addressBook;
    this.phoneType = phoneType;
    this.number = Optional.ofNullable(number).map(String::trim).orElse(number);
  }

  public static PhoneEntity create(AddressBookEntity addressBook, PhoneDTO dto) {
    return new PhoneEntity(addressBook, dto.getPhoneType(), dto.getNumber());
  }

  public PhoneEntity update(PhoneType phoneType, String number) {
    this.phoneType = phoneType;
    this.number = number;
    return this;
  }

  public PhoneDTO toDTO() {
    return PhoneDTO.builder()
        .id(id)
        .phoneType(phoneType)
        .number(number)
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PhoneEntity)) return false;
    PhoneEntity that = (PhoneEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
