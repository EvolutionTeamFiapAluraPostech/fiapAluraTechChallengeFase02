package br.com.digitalparking.vehicle.application.validation;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.message.VehicleMessages;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class VehicleLicensePlateInOtherVehicleValidator {

  private final VehicleService vehicleService;

  public VehicleLicensePlateInOtherVehicleValidator(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  public void validate(String licensePlate, String vehicleUuid) {
    var vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
    if (vehicle.isPresent() && vehicleLicensePlateAlreadyExistsInOtherVehicle(vehicleUuid,
        vehicle.get())) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "license plate",
          VehicleMessages.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS.formatted(licensePlate)));
    }
  }

  private boolean vehicleLicensePlateAlreadyExistsInOtherVehicle(String vehicleUuid,
      Vehicle vehicle) {
    return !vehicle.getId().equals(UUID.fromString(vehicleUuid));
  }
}
