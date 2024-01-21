package br.com.digitalparking.parking.model.service;

import static br.com.digitalparking.parking.model.message.ParkingMessages.PARKING_NOT_FOUND;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.exception.NoResultException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class ParkingService {

  private final ParkingRepository parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {
    this.parkingRepository = parkingRepository;
  }

  public Parking save(Parking parking) {
    return parkingRepository.save(parking);
  }

  public Parking findById(UUID uuid) {
    return parkingRepository.findById(uuid).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "uuid",
            PARKING_NOT_FOUND.formatted(uuid))));
  }
}
