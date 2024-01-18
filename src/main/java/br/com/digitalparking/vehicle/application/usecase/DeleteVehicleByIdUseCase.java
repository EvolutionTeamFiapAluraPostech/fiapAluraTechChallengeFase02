package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteVehicleByIdUseCase {

  private final VehicleService vehicleService;
  private final UuidValidator uuidValidator;

  public DeleteVehicleByIdUseCase(VehicleService vehicleService, UuidValidator uuidValidator) {
    this.vehicleService = vehicleService;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  public void execute(String vehicleUuid) {
    uuidValidator.validate(vehicleUuid);
    var vehicle = vehicleService.findVehicleByIdRequired(UUID.fromString(vehicleUuid));
    vehicleService.deleteVehicleById(vehicle);
  }
}
