package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.ALTERNATIVE_VEHICLE_UUID_STRING;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.model.service.UserService;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleLicensePlateInOtherVehicleValidatorTest {

  @Mock
  private VehicleService vehicleService;
  @Mock
  private UserService userService;
  @InjectMocks
  private VehicleLicensePlateInOtherVehicleValidator vehicleLicensePlateInOtherVehicleValidator;

  @Test
  void shouldValidateVehicleLicensePlateWhenLicensePlateDoesNotExistsInOtherVehicle() {
    var vehicle = createVehicle();
    var user = createUser();
    user.add(vehicle);
    when(vehicleService.findVehicleByLicensePlate(vehicle.getLicensePlate())).thenReturn(
        List.of(vehicle));
    when(userService.findUserByIdRequired(user.getId())).thenReturn(user);

    assertDoesNotThrow(
        () -> vehicleLicensePlateInOtherVehicleValidator.validate(vehicle.getLicensePlate(),
            vehicle.getId().toString(), user.getId()));
  }

  @Test
  void shouldThrowExceptionWhenVehicleLicensePlateExistsInOtherVehicle() {
    var user = createUser();
    var otherVehicle = createNewVehicle();
    otherVehicle.setId(UUID.randomUUID());
    user.add(otherVehicle);
    var vehicle = createVehicle();
    when(vehicleService.findVehicleByLicensePlate(vehicle.getLicensePlate())).thenReturn(
        List.of(otherVehicle));
    when(userService.findUserByIdRequired(user.getId())).thenReturn(user);

    assertThrows(DuplicatedException.class,
        () -> vehicleLicensePlateInOtherVehicleValidator.validate(vehicle.getLicensePlate(),
            ALTERNATIVE_VEHICLE_UUID_STRING, user.getId()));
  }

}