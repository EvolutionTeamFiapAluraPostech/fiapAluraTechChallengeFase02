package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.ALTERNATIVE_VEHICLE_UUID_STRING;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createVehicle;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleLicensePlateInOtherVehicleValidatorTest {

  @Mock
  private VehicleService vehicleService;
  @InjectMocks
  private VehicleLicensePlateInOtherVehicleValidator vehicleLicensePlateInOtherVehicleValidator;

  @Test
  void shouldValidateVehicleLicensePlateWhenLicensePlateDoesNotExistsInOtherVehicle() {
    var vehicle = createVehicle();
    when(vehicleService.findVehicleByLicensePlate(vehicle.getLicensePlate())).thenReturn(
        Optional.empty());

    assertDoesNotThrow(
        () -> vehicleLicensePlateInOtherVehicleValidator.validate(vehicle.getLicensePlate(),
            vehicle.getId().toString()));
  }

  @Test
  void shouldThrowExceptionWhenVehicleLicensePlateExistsInOtherVehicle() {
    var vehicle = createVehicle();
    when(vehicleService.findVehicleByLicensePlate(vehicle.getLicensePlate())).thenReturn(
        Optional.of(vehicle));

    assertThrows(DuplicatedException.class,
        () -> vehicleLicensePlateInOtherVehicleValidator.validate(vehicle.getLicensePlate(),
            ALTERNATIVE_VEHICLE_UUID_STRING));
  }

}