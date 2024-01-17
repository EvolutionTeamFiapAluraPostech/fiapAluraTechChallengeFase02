package br.com.digitalparking.vehicle.model.service;

import static br.com.digitalparking.vehicle.model.message.VehicleMessages.VEHICLE_ID_NOT_FOUND;

import br.com.digitalparking.shared.exception.NoResultException;
import br.com.digitalparking.vehicle.infrastructure.repository.VehicleRepository;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  public VehicleService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  public Vehicle save(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  public Vehicle findVehicleByIdRequired(UUID vehicleUuid) {
    return vehicleRepository.findById(vehicleUuid).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "id",
            VEHICLE_ID_NOT_FOUND.formatted(vehicleUuid))));
  }

  public Optional<Vehicle> findVehicleByLicensePlate(String licensePlate) {
    return vehicleRepository.findByLicensePlate(licensePlate);
  }
}
