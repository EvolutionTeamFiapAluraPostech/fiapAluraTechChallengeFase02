package br.com.digitalparking.user.application.validator;

import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.model.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCpfAlreadyRegisteredValidatorTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;

  @Test
  void shouldValidateWhenUserCpfDoesNotExist() {
    var user = createNewUser();
    when(userService.findByCpf(user.getCpf())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userCpfAlreadyRegisteredValidator.validate(user.getCpf()));
  }

  @Test
  void shouldThrowExceptionWhenUserCpfAlreadyExists() {
    var user = createUser();
    when(userService.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

    assertThrows(DuplicatedException.class,
        () -> userCpfAlreadyRegisteredValidator.validate(user.getCpf()));
  }

}