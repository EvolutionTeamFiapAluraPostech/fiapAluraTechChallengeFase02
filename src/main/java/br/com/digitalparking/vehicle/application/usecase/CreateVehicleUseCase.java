package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.service.UserService;
import br.com.digitalparking.vehicle.application.validation.VehicleLicensePlateValidator;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateVehicleUseCase {

  private final VehicleService vehicleService;
  private final VehicleLicensePlateValidator vehicleLicensePlateValidator;
  private final UserFromSecurityContext userFromSecurityContext;
  private final UserService userService;

  public CreateVehicleUseCase(VehicleService vehicleService,
      VehicleLicensePlateValidator vehicleLicensePlateValidator, UserFromSecurityContext userFromSecurityContext,
      UserService userService) {
    this.vehicleService = vehicleService;
    this.vehicleLicensePlateValidator = vehicleLicensePlateValidator;
    this.userFromSecurityContext = userFromSecurityContext;
    this.userService = userService;
  }

  @Transactional
  public Vehicle execute(Vehicle vehicle){
    vehicleLicensePlateValidator.validate(vehicle.getLicensePlate());
    var vehicleSaved = vehicleService.save(vehicle);
    var user = userFromSecurityContext.getUser();
    user.add(vehicleSaved);
    userService.save(user);
    return vehicleSaved;
  }
}
