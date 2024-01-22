package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.parking.model.enums.ParkingType.FLEX;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.digitalparking.shared.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingTimeValidatorTest {

  @Spy
  private ParkingTimeValidator parkingTimeValidator;

  @Test
  void shouldValidateParkingTimeWhenParkingTypeIsFixedAndParkingTimeIsGreaterThanZero() {
    var parking = createNewParking();
    assertDoesNotThrow(() -> parkingTimeValidator.validate(parking));
  }

  @Test
  void shouldThrowsExceptionWhenParkingIsNull() {
    assertThrows(ValidatorException.class, () -> parkingTimeValidator.validate(null));
  }

  @Test
  void shouldThrowsExceptionWhenParkingTypeIsNull() {
    var parking = createNewParking();
    parking.setParkingType(null);
    assertThrows(ValidatorException.class, () -> parkingTimeValidator.validate(parking));
  }

  @Test
  void shouldThrowsExceptionWhenParkingTypeIsFixedAndParkingTimeIsEqualToZero() {
    var parking = createNewParking();
    parking.setParkingTime(0);
    assertThrows(ValidatorException.class, () -> parkingTimeValidator.validate(parking));
  }

  @Test
  void shouldValidateParkingTimeWhenParkingTypeIsFreexAndParkingTimeIsEqualToZero() {
    var parking = createNewParking();
    parking.setParkingType(FLEX);
    parking.setParkingTime(0);
    assertDoesNotThrow(() -> parkingTimeValidator.validate(parking));
  }
}