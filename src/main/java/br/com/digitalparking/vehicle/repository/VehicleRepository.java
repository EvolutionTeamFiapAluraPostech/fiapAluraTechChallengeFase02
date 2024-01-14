package br.com.digitalparking.vehicle.repository;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

}
