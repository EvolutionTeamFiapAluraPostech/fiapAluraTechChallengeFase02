package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.enums.ParkingState.CLOSED;
import static br.com.digitalparking.parking.model.enums.ParkingState.OPEN;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParking;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingStateAvailableValidatorTest {

  @Spy
  private ParkingStateAvailableValidator parkingStateAvailableValidator;

  @Test
  void shouldValidateParkingState() {
    var parking = createParking();
    parking.setParkingState(OPEN);
    assertDoesNotThrow(() -> parkingStateAvailableValidator.validate(parking));
  }

  @Test
  void shouldThrowExceptionWhenParkingStateIsClosed() {
    var parking = createParking();
    parking.setParkingState(CLOSED);
    assertThrows(ValidatorException.class, () -> parkingStateAvailableValidator.validate(parking));
  }
}