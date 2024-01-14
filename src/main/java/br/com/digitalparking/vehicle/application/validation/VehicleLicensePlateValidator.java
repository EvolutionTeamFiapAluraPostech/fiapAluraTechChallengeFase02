package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.vehicle.model.message.VehicleMessages.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.infrastructure.security.UserAuditInfo;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class VehicleLicensePlateValidator {

  private final UserAuditInfo userAuditInfo;

  public VehicleLicensePlateValidator(UserAuditInfo userAuditInfo) {
    this.userAuditInfo = userAuditInfo;
  }

  public void validate(Vehicle vehicle) {
    var user = userAuditInfo.getUser();
    var isVehicleFound = user.userHas(vehicle);
    if (isVehicleFound) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "vehicle",
          VEHICLE_LICENSE_PLATE_ALREADY_EXISTS.formatted(vehicle.getLicensePlate())));
    }
  }

}
