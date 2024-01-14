package br.com.digitalparking.user.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.testData.user.UserTestData;
import br.com.digitalparking.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.digitalparking.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.digitalparking.user.application.validator.UserPasswordStrengthValidator;
import br.com.digitalparking.user.model.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  @Spy
  private UserPasswordStrengthValidator userPasswordStrengthValidator;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;
  @InjectMocks
  private CreateUserUseCase createUserUseCase;

  @Test
  void shouldCreateNewUserWhenAllUserAttributesAreCorrect() {
    var user = UserTestData.createNewUser();
    var originalPassword = user.getPassword();
    when(userService.save(user)).then(returnsFirstArg());

    var userSaved = createUserUseCase.execute(user);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail()).isEqualTo(user.getEmail());
    assertThat(userSaved.getCpf()).isEqualTo(user.getCpf());
    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail());
    verify(userPasswordStrengthValidator).validate(originalPassword);
    verify(passwordEncoder).encode(originalPassword);
    verify(userCpfAlreadyRegisteredValidator).validate(user.getCpf());
    verify(userService).save(user);
  }

  @Test
  void shouldThrowExceptionWhenUserPasswordIsInvalid() {
    var user = UserTestData.createNewUser();
    user.setPassword("123456");

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        ValidatorException.class);

    verify(userPasswordStrengthValidator).validate(user.getPassword());
    verify(userEmailAlreadyRegisteredValidator, never()).validate(user.getEmail());
    verify(userService, never()).save(user);
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = UserTestData.createNewUser();
    doThrow(DuplicatedException.class).when(userEmailAlreadyRegisteredValidator)
        .validate(user.getEmail());

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        DuplicatedException.class);

    verify(userPasswordStrengthValidator).validate(user.getPassword());
    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail());
    verify(userService, never()).save(user);
  }
}
