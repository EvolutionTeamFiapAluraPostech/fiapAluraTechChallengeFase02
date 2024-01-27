package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentValueValidatorTest {

  @Spy
  private PaymentValueValidator paymentValueValidator;

  @Test
  void shouldValidatePaymentWhenPaymentValueIsGreaterThanZero() {
    var parking = createParkingWithPayment();
    assertDoesNotThrow(() -> paymentValueValidator.validate(parking.getParkingPayment()));
  }

  @Test
  void shouldThrowExceptionWhenPaymentValueIsZero() {
    var parking = createParkingWithPayment();
    parking.getParkingPayment().setPaymentValue(BigDecimal.ZERO);
    assertThrows(ValidatorException.class,
        () -> paymentValueValidator.validate(parking.getParkingPayment()));
  }
}