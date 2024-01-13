package br.com.digitalparking.user.application.usecase;

import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByNameUseCase {

  private final UserService userService;

  public GetUsersByNameUseCase(UserService userService) {
    this.userService = userService;
  }

  public Page<User> execute(String name, Pageable pageable) {
    return userService.findByNamePageable(name, pageable);
  }
}
