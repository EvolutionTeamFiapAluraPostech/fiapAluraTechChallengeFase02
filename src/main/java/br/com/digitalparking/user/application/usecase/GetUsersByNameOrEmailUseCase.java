package br.com.digitalparking.user.application.usecase;

import br.com.digitalparking.shared.model.entity.validator.EmailValidator;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByNameOrEmailUseCase {

  private final UserService userService;
  private final EmailValidator emailValidator;

  public GetUsersByNameOrEmailUseCase(
      UserService userService,
      EmailValidator emailValidator) {
    this.userService = userService;
    this.emailValidator = emailValidator;
  }

  public Page<User> execute(String name, String email, Pageable pageable) {
    emailValidator.validate(email);
    return userService.queryUserByNameLikeIgnoreCaseOrEmail(name, email, pageable);
  }
}
