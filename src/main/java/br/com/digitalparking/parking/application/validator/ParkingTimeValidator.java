package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PARKING_TIME_INVALID;
import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_PARKING_TYPE_INVALID;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ParkingTimeValidator {

  public void validate(Parking parking) {
    if (parking == null || parking.getParkingType() == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "parkingType",
          PARKING_PARKING_TYPE_INVALID));
    }
    if (!parking.isParkingTimeValidForParkingType()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "parkingTime",
          PARKING_PARKING_TIME_INVALID));
    }
  }
}
