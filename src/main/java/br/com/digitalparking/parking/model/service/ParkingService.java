package br.com.digitalparking.parking.model.service;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.parking.model.entity.Parking;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

  private final ParkingRepository parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {
    this.parkingRepository = parkingRepository;
  }

  public Parking save(Parking parking) {
    return parkingRepository.save(parking);
  }
}
