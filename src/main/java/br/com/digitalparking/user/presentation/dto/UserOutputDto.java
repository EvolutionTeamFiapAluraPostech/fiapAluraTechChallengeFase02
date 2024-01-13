package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import org.springframework.data.domain.Page;

public record UserOutputDto(
    String id,
    String name,
    String email
) {

  public UserOutputDto(User user) {
      this(user.getId() != null ? user.getId().toString() : null,
          user.getName(),
          user.getEmail());
  }

  public static Page<UserOutputDto> toPage(Page<User> usersPage) {
    return usersPage.map(UserOutputDto::new);
  }

  public static UserOutputDto from(User user){
    return new UserOutputDto(user);
  }

}
