package br.com.digitalparking.user.application.usecase;

import static br.com.digitalparking.user.model.messages.UserMessages.USER_CPF_NOT_FOUND;

import br.com.digitalparking.shared.exception.NoResultException;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class GetUserByCpfUseCase {

  private final UserService userService;

  public GetUserByCpfUseCase(UserService userService) {
    this.userService = userService;
  }

  public User execute(String cpf) {
    return userService.findByCpf(cpf).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "cpf",
            USER_CPF_NOT_FOUND.formatted(cpf))));
  }
}
