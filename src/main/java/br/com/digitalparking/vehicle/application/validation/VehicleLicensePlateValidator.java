package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.vehicle.model.message.VehicleMessages.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class VehicleLicensePlateValidator {

  private final UserFromSecurityContext userFromSecurityContext;

  public VehicleLicensePlateValidator(UserFromSecurityContext userFromSecurityContext) {
    this.userFromSecurityContext = userFromSecurityContext;
  }

  public void validate(Vehicle vehicle) {
    var user = userFromSecurityContext.getUser();
    var isVehicleFound = checkIfTheVehicleWasAlredyRegistredByUser(vehicle, user);
    if (isVehicleFound) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "vehicle",
          VEHICLE_LICENSE_PLATE_ALREADY_EXISTS.formatted(vehicle.getLicensePlate())));
    }
  }

  private boolean checkIfTheVehicleWasAlredyRegistredByUser(Vehicle vehicle, User user) {
    return user != null && user.userHas(vehicle);
  }

}
