package br.com.digitalparking.vehicle.infrastructure.repository;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

  Optional<Vehicle> findByLicensePlate(String licensePlate);

}
