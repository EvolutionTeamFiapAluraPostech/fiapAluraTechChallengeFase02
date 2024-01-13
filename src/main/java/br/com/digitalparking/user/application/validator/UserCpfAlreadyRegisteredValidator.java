package br.com.digitalparking.user.application.validator;

import static br.com.digitalparking.user.model.messages.UserMessages.USER_CPF_ALREADY_EXISTS;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserCpfAlreadyRegisteredValidator {

  private final UserService userService;

  public UserCpfAlreadyRegisteredValidator(UserService userService) {
    this.userService = userService;
  }

  public void validate(String cpf) {
    var user = userService.findByCpf(cpf);
    if (user.isPresent()) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cpf",
          USER_CPF_ALREADY_EXISTS.formatted(cpf)));
    }
  }
}
