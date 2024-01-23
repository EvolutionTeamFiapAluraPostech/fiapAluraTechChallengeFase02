package br.com.digitalparking.user.application.usecase;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.application.validation.PaymentMethodValidator;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import br.com.digitalparking.user.model.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserPaymentMethodUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private PaymentMethodValidator paymentMethodValidator;
  @InjectMocks
  private UpdateUserPaymentMethodUserUseCase updateUserPaymentMethodUserUseCase;

  @Test
  void shouldUpdateUserPaymentMethod() {
    var user = createUser();
    var userPaymentMethod = UserPaymentMethod.builder()
        .paymentMethod(PaymentMethod.CREDIT_CARD)
        .user(user)
        .build();
    user.setUserPaymentMethod(userPaymentMethod);
    when(userService.findUserByIdRequired(user.getId())).thenReturn(user);
    when(userService.save(Mockito.any())).thenReturn(user);
    var paymentMethod = "Pix";

    var userUpdated = updateUserPaymentMethodUserUseCase.execute(user.getId().toString(),
        paymentMethod);

    assertThat(userUpdated).isNotNull();
    assertThat(userUpdated).usingRecursiveComparison().ignoringFields("userPaymentMethod")
        .isEqualTo(user);
    assertThat(userUpdated.getUserPaymentMethod()).isNotNull();
    assertThat(userUpdated.getUserPaymentMethod().getPaymentMethod()).isEqualTo(PaymentMethod.PIX);
    verify(uuidValidator).validate(user.getId().toString());
    verify(paymentMethodValidator).validate(paymentMethod);
  }

  @Test
  void shouldUpdateUserPaymentMethodWhenUserDoesNotHavePaymentMethodSaved() {
    var user = createUser();
    user.setUserPaymentMethod(null);
    when(userService.findUserByIdRequired(user.getId())).thenReturn(user);
    when(userService.save(Mockito.any())).thenReturn(user);
    var paymentMethod = "Pix";

    var userUpdated = updateUserPaymentMethodUserUseCase.execute(user.getId().toString(),
        paymentMethod);

    assertThat(userUpdated).isNotNull();
    assertThat(userUpdated).usingRecursiveComparison().ignoringFields("userPaymentMethod")
        .isEqualTo(user);
    assertThat(userUpdated.getUserPaymentMethod()).isNotNull();
    assertThat(userUpdated.getUserPaymentMethod().getPaymentMethod()).isEqualTo(PaymentMethod.PIX);
    verify(uuidValidator).validate(user.getId().toString());
    verify(paymentMethodValidator).validate(paymentMethod);
  }
}