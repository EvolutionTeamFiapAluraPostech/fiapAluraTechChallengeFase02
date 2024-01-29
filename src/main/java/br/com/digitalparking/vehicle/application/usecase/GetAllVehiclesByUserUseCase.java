package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAllVehiclesByUserUseCase {

  private final UserFromSecurityContext userFromSecurityContext;

  public GetAllVehiclesByUserUseCase(UserFromSecurityContext userFromSecurityContext) {
    this.userFromSecurityContext = userFromSecurityContext;
  }

  public List<Vehicle> execute() {
    var user = userFromSecurityContext.getUser();
    return user.getVehicles();
  }
}
