package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PAYMENT_HAS_ALREADY_BEEN_PAID;
import static br.com.digitalparking.shared.model.enums.PaymentState.PAID;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.shared.exception.ValidatorException;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class PaymentHasAlreadyPaidValidator {

  public void validate(ParkingPayment parkingPayment) {
    if (parkingPayment != null && parkingPayment.getPaymentValue().compareTo(BigDecimal.ZERO) != 0
        && parkingPayment.getPaymentState().equals(PAID)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), "parkingPaymentState",
              PARKING_PAYMENT_HAS_ALREADY_BEEN_PAID));
    }
  }
}
