package pl.com.decsoft.user;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_users")
@Getter
public class UserEntity {

  @Id
  @Column(name = "username")
  private String username;

  @NotNull
  @Length(max = 255)
  @Column(name = "password")
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;
}
