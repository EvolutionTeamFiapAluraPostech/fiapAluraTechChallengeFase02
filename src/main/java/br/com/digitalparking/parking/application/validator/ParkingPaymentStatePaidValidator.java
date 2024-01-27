package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PAYMENT_WAS_NOT_PAID;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.enums.PaymentState;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ParkingPaymentStatePaidValidator {

  public void validate(Parking parking) {
    if (parking != null && PaymentState.NOT_PAID.equals(
        parking.getParkingPayment().getPaymentState())) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "paymentState",
          PARKING_PAYMENT_WAS_NOT_PAID));
    }
  }
}
