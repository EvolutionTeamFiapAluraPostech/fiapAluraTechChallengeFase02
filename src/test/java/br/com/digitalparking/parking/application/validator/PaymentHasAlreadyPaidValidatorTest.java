package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.enums.PaymentState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentHasAlreadyPaidValidatorTest {

  @Spy
  private PaymentHasAlreadyPaidValidator paymentHasAlreadyPaidValidator;

  @Test
  void shouldValidatePaymentWhenParkingPaymentWasNotPaid() {
    var parking = createParkingWithPayment();
    assertDoesNotThrow(
        () -> paymentHasAlreadyPaidValidator.validate(parking.getParkingPayment()));
  }

  @Test
  void shouldThrowExceptionWhenParkingPaymentHasAlreadyBeenPaid() {
    var parking = createParkingWithPayment();
    parking.getParkingPayment().setPaymentState(PaymentState.PAID);
    assertThrows(ValidatorException.class,
        () -> paymentHasAlreadyPaidValidator.validate(parking.getParkingPayment()));
  }
}