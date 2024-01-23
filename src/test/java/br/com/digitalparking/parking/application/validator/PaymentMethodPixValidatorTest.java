package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentMethodPixValidatorTest {

  @Spy
  private PaymentMethodPixValidator paymentMethodPixValidator;

  @Test
  void shouldValidatePixPaymentMethodWhenParkingTypeIsFixed() {
    var parking = createParkingWithPayment();
    parking.setParkingType(ParkingType.FIXED);
    parking.getParkingPayment().setPaymentMethod(PaymentMethod.PIX);

    assertDoesNotThrow(() -> paymentMethodPixValidator.validate(parking));
  }

  @Test
  void shouldThrowExceptionWhenPaymentMethodIsPixAndParkingTypeIsFlex() {
    var parking = createParkingWithPayment();
    parking.setParkingType(ParkingType.FLEX);
    parking.getParkingPayment().setPaymentMethod(PaymentMethod.PIX);

    assertThrows(ValidatorException.class, () -> paymentMethodPixValidator.validate(parking));
  }
}