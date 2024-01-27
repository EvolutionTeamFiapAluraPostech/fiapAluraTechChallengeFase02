package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.shared.model.enums.PaymentState.NOT_PAID;
import static br.com.digitalparking.shared.model.enums.PaymentState.PAID;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingPaymentStatePaidValidatorTest {

  @Spy
  private ParkingPaymentStatePaidValidator parkingPaymentStatePaidValidator;

  @Test
  void shouldValidateParkingPaymentWhenParkingPaymentStateIsPaid() {
    var parking = createParkingWithPayment();
    parking.getParkingPayment().setPaymentState(PAID);
    assertDoesNotThrow(() -> parkingPaymentStatePaidValidator.validate(parking));
  }

  @Test
  void shouldThrowExceptionWhenParkingPaymentStateIsNotPaid() {
    var parking = createParkingWithPayment();
    parking.getParkingPayment().setPaymentState(NOT_PAID);
    assertThrows(ValidatorException.class,
        () -> parkingPaymentStatePaidValidator.validate(parking));
  }

}