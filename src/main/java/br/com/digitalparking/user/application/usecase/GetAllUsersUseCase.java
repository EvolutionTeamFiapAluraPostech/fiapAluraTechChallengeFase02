package br.com.digitalparking.user.application.usecase;

import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsersUseCase {

  private final UserService userService;

  public GetAllUsersUseCase(UserService userService) {
    this.userService = userService;
  }

  public Page<User> execute(Pageable pageable){
    return userService.getAllUsersPaginated(pageable);
  }
}
