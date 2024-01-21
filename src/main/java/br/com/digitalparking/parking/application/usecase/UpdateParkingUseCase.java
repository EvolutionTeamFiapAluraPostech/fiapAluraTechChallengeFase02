package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateParkingUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;

  public UpdateParkingUseCase(ParkingService parkingService, UuidValidator uuidValidator) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  public Parking execute(String uuid, Parking parking) {
    uuidValidator.validate(uuid);
    var parkingSaved = parkingService.findById(UUID.fromString(uuid));
    var parkingToSave = updateAttributes(parkingSaved, parking);
    return parkingService.save(parkingToSave);
  }

  private Parking updateAttributes(Parking parkingSaved, Parking parking) {
    parkingSaved.setVehicle(parking.getVehicle());
    parkingSaved.setUser(parking.getUser());
    parkingSaved.setLatitude(parking.getLatitude());
    parkingSaved.setLongitude(parking.getLongitude());
    parkingSaved.setStreet(parking.getStreet());
    parkingSaved.setNeighborhood(parking.getNeighborhood());
    parkingSaved.setCity(parking.getCity());
    parkingSaved.setState(parking.getState());
    parkingSaved.setCountry(parking.getCountry());
    parkingSaved.setParkingState(parking.getParkingState());
    parkingSaved.setParkingType(parking.getParkingType());
    parkingSaved.setParkingTime(parking.getParkingTime());
    parkingSaved.setInitialParking(parking.getInitialParking());
    parkingSaved.setFinalParking(parking.getFinalParking());
    return parkingSaved;
  }
}
