package br.com.digitalparking.user.application.validator;

import static br.com.digitalparking.user.model.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserEmailAlreadyRegisteredValidator {

  private final UserService userService;

  public UserEmailAlreadyRegisteredValidator(UserService userService) {
    this.userService = userService;
  }

  public void validate(String email) {
    var user = userService.findByEmail(email);
    if (user.isPresent()) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "email",
          USER_EMAIL_ALREADY_EXISTS.formatted(email)));
    }
  }
}
