package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.vehicle.model.message.VehicleMessages.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.model.service.UserService;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class VehicleLicensePlateInOtherVehicleValidator {

  private final VehicleService vehicleService;
  private final UserService userService;

  public VehicleLicensePlateInOtherVehicleValidator(VehicleService vehicleService,
      UserService userService) {
    this.vehicleService = vehicleService;
    this.userService = userService;
  }

  public void validate(String licensePlate, String vehicleUuid, UUID userUuid) {
    var user = userService.findUserByIdRequired(userUuid);
    var vehicles = vehicleService.findVehicleByLicensePlate(licensePlate);
    for (Vehicle vehicle : vehicles) {
      if (user.userHasVehicleWithLicensePlate(licensePlate)
          && vehicleLicensePlateAlreadyExistsInOtherVehicle(vehicleUuid, vehicle)) {
        throw new DuplicatedException(
            new FieldError(this.getClass().getSimpleName(), "license plate",
                VEHICLE_LICENSE_PLATE_ALREADY_EXISTS.formatted(licensePlate)));
      }
    }
  }

  private boolean vehicleLicensePlateAlreadyExistsInOtherVehicle(String vehicleUuid,
      Vehicle vehicle) {
    return !vehicle.getId().equals(UUID.fromString(vehicleUuid));
  }
}
