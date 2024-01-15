package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetVehicleByIdAndUserUseCase {

  private final VehicleService vehicleService;
  private final UuidValidator uuidValidator;
  private final UserFromSecurityContext userFromSecurityContext;

  public GetVehicleByIdAndUserUseCase(VehicleService vehicleService, UuidValidator uuidValidator,
      UserFromSecurityContext userFromSecurityContext) {
    this.vehicleService = vehicleService;
    this.uuidValidator = uuidValidator;
    this.userFromSecurityContext = userFromSecurityContext;
  }

  public Vehicle execute(String uuid) {
    uuidValidator.validate(uuid);
    var vehicle = vehicleService.findVehicleByIdRequired(UUID.fromString(uuid));

    var user = userFromSecurityContext.getUser();
    if (user.userHas(vehicle)) {
      return vehicle;
    }
    return null;
  }
}
