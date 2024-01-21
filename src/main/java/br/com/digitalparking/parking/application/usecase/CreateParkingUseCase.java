package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.parking.model.enums.ParkingState.OPEN;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateParkingUseCase {
  private final ParkingService parkingService;
  private final UserFromSecurityContext userFromSecurityContext;
  private final VehicleService vehicleService;

  public CreateParkingUseCase(ParkingService parkingService,
      UserFromSecurityContext userFromSecurityContext, VehicleService vehicleService) {
    this.parkingService = parkingService;
    this.userFromSecurityContext = userFromSecurityContext;
    this.vehicleService = vehicleService;
  }

  @Transactional
  public Parking execute(Parking parking) {
    var user = userFromSecurityContext.getUser();
    var vehicle = vehicleService.findVehicleByIdRequired(parking.getVehicle().getId());
    updateAttributesToSave(parking, user, vehicle);
    return parkingService.save(parking);
  }

  private void updateAttributesToSave(Parking parking, User user, Vehicle vehicle) {
    parking.setUser(user);
    parking.setVehicle(vehicle);
    parking.setParkingState(OPEN);
    parking.setInitialParking(LocalDateTime.now());
    calculateFinalParkingTime(parking);
  }

  private void calculateFinalParkingTime(Parking parking) {
    if (parking.getParkingTime() != null && parking.getParkingTime() > 0) {
      parking.setFinalParking(parking.getInitialParking().plusHours(parking.getParkingTime()));
    }
  }
}
