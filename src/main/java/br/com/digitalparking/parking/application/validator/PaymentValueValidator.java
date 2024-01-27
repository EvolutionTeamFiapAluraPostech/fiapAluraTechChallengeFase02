package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PAYMENT_VALUE_INVALID;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.shared.exception.ValidatorException;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class PaymentValueValidator {

  public void validate(ParkingPayment parkingPayment) {
    if (parkingPayment.getPaymentValue().compareTo(BigDecimal.ZERO) == 0) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), "parkingPaymentValue",
              PARKING_PAYMENT_VALUE_INVALID));
    }
  }
}
