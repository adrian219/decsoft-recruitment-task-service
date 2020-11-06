package pl.com.decsoft.domain.addressbook.photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "t_photos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "photo_store_type")
  private PhotoStoreType photoStoreType;

  @OneToOne(fetch = FetchType.LAZY)
  private PhotoEntity photo;

  @Lob
  @Column(name = "content")
  private Blob content;

  @Length(max = 255)
  @Column(name = "dir_path")
  private String dirPath;

  @NotNull
  @Column(name = "file_size")
  private long size;

  @Length(max = 255)
  @Column(name = "media_type")
  private String mediaType;

  @NotNull
  @Length(max = 255)
  @Column(name = "file_name")
  private String fileName;

  @NotNull
  @Length(max = 100)
  @Column(name = "file_extension")
  private String fileExtension;

  @Version
  @Column(name = "version")
  private int version;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PhotoEntity)) return false;
    PhotoEntity that = (PhotoEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getDisplayName() {
    return fileName + "." + fileExtension;
  }
}
