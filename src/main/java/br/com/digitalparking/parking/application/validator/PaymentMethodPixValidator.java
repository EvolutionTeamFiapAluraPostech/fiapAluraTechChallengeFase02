package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.enums.ParkingType.FIXED;
import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PIX_PAYMENT_METHOD_INVALID;
import static br.com.digitalparking.shared.model.enums.PaymentMethod.PIX;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class PaymentMethodPixValidator {

  public void validate(Parking parking) {
    if (parking.getParkingType() != null && parking.getParkingPayment() != null
        && parking.getParkingPayment().getPaymentMethod() != null) {
      if (parking.getParkingPayment().getPaymentMethod().equals(PIX)) {
        var paymentMethodValid = parking.getParkingType().equals(FIXED);
        if (!paymentMethodValid) {
          throw new ValidatorException(
              new FieldError(this.getClass().getSimpleName(), "Pix payment method",
                  PARKING_PIX_PAYMENT_METHOD_INVALID.formatted(
                      parking.getParkingType().getDescription())));
        }
      }
    }
  }
}
