package br.com.digitalparking.parking.infrastructure.repository;

import br.com.digitalparking.parking.model.entity.Parking;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

}
