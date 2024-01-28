package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateParkingUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final UserFromSecurityContext userFromSecurityContext;
  private final VehicleService vehicleService;

  public UpdateParkingUseCase(ParkingService parkingService, UuidValidator uuidValidator,
      UserFromSecurityContext userFromSecurityContext, VehicleService vehicleService) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
    this.userFromSecurityContext = userFromSecurityContext;
    this.vehicleService = vehicleService;
  }

  @Transactional
  public Parking execute(String uuid, Parking parking) {
    uuidValidator.validate(uuid);
    var user = userFromSecurityContext.getUser();
    var parkingSaved = parkingService.findById(UUID.fromString(uuid));
    var vehicle = vehicleService.findVehicleByIdRequired(parking.getVehicle().getId());
    var parkingToSave = updateAttributes(parkingSaved, parking, user, vehicle);
    return parkingService.save(parkingToSave);
  }

  private Parking updateAttributes(Parking parkingSaved, Parking parking, User user,
      Vehicle vehicle) {
    parkingSaved.setVehicle(vehicle);
    parkingSaved.setUser(user);
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
