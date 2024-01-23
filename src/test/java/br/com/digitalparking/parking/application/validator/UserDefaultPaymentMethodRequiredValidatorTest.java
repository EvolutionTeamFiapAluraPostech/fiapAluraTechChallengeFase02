package br.com.digitalparking.parking.application.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.shared.testData.user.UserTestData;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDefaultPaymentMethodRequiredValidatorTest {

  @Spy
  private UserDefaultPaymentMethodRequiredValidator userDefaultPaymentMethodRequiredValidator;

  @Test
  void shouldValidateUserDefaultPaymentMethodIsValid() {
    var user = UserTestData.createUser();
    var userPaymentMethod = UserPaymentMethod.builder()
        .id(UUID.randomUUID())
        .user(user)
        .paymentMethod(PaymentMethod.CREDIT_CARD).build();
    user.setUserPaymentMethod(userPaymentMethod);

    assertDoesNotThrow(() -> userDefaultPaymentMethodRequiredValidator.validate(user));
  }

  @Test
  void shouldThrowExceptionWhenUserDefaultPaymentMethodIsInvalid() {
    var user = UserTestData.createUser();
    user.setUserPaymentMethod(null);

    assertThrows(ValidatorException.class, () -> userDefaultPaymentMethodRequiredValidator.validate(user));
  }
}