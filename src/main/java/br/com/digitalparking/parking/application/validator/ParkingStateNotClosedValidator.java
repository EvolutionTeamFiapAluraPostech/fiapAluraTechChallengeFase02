package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_HAS_ALREADY_BEEN_CLOSED;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.shared.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ParkingStateNotClosedValidator {

  public void validate(Parking parking) {
    if (parking != null && ParkingState.CLOSED.equals(parking.getParkingState())) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "parkingState",
          PARKING_HAS_ALREADY_BEEN_CLOSED));
    }
  }
}
