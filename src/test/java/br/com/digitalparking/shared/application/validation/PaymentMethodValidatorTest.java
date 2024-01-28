package br.com.digitalparking.shared.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentMethodValidatorTest {

  @Spy
  private PaymentMethodValidator paymentMethodValidator;

  @ParameterizedTest
  @ValueSource(strings = {"PIX", "CREDIT_CARD", "DEBIT_CARD"})
  void shouldValidatePaymentMethod(String paymentMethod) {
    assertDoesNotThrow(() -> paymentMethodValidator.validate(paymentMethod));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"pIX", "CREDIT CARD", "DEBIT CARD", "Dinheiro", "@123", "."})
  void shouldThrowExceptionWhenPaymentMethodIsInvalid(String paymentMethod) {
    assertThrows(ValidatorException.class, () -> paymentMethodValidator.validate(paymentMethod));
  }

}