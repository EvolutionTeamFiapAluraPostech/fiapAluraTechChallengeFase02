package br.com.digitalparking.vehicle.model.service;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  public VehicleService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  public Vehicle save(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

}
