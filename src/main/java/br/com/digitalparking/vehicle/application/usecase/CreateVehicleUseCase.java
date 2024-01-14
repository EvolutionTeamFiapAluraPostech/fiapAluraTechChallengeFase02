package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.vehicle.application.validation.VehicleLicensePlateValidator;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateVehicleUseCase {

  private final VehicleService vehicleService;
  private final VehicleLicensePlateValidator vehicleLicensePlateValidator;

  public CreateVehicleUseCase(VehicleService vehicleService,
      VehicleLicensePlateValidator vehicleLicensePlateValidator) {
    this.vehicleService = vehicleService;
    this.vehicleLicensePlateValidator = vehicleLicensePlateValidator;
  }

  @Transactional
  public Vehicle execute(Vehicle vehicle){
    vehicleLicensePlateValidator.validate(vehicle);
    return vehicleService.save(vehicle);
  }
}
