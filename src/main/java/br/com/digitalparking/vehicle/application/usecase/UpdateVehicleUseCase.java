package br.com.digitalparking.vehicle.application.usecase;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.vehicle.application.validation.VehicleLicensePlateInOtherVehicleValidator;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateVehicleUseCase {

  private final VehicleService vehicleService;
  private final UuidValidator uuidValidator;
  private final VehicleLicensePlateInOtherVehicleValidator vehicleLicensePlateInOtherVehicleValidator;

  public UpdateVehicleUseCase(VehicleService vehicleService, UuidValidator uuidValidator,
      VehicleLicensePlateInOtherVehicleValidator vehicleLicensePlateInOtherVehicleValidator) {
    this.vehicleService = vehicleService;
    this.uuidValidator = uuidValidator;
    this.vehicleLicensePlateInOtherVehicleValidator = vehicleLicensePlateInOtherVehicleValidator;
  }

  @Transactional
  public Vehicle execute(String vehicleUuid, Vehicle vehicleWithUpdatedAttributes) {
    uuidValidator.validate(vehicleUuid);
    vehicleLicensePlateInOtherVehicleValidator.validate(
        vehicleWithUpdatedAttributes.getLicensePlate(), vehicleUuid);
    var vehicleSaved = vehicleService.findVehicleByIdRequired(UUID.fromString(vehicleUuid));
    var vehicleToUpdate = updateAttributesToVehicle(vehicleSaved, vehicleWithUpdatedAttributes);
    return vehicleService.save(vehicleToUpdate);
  }

  private Vehicle updateAttributesToVehicle(Vehicle vehicleSaved,
      Vehicle vehicleWithUpdatedAttributes) {
    vehicleSaved.setDescription(vehicleWithUpdatedAttributes.getDescription());
    vehicleSaved.setLicensePlate(vehicleWithUpdatedAttributes.getLicensePlate());
    vehicleSaved.setColor(vehicleWithUpdatedAttributes.getColor());
    return vehicleSaved;
  }
}
