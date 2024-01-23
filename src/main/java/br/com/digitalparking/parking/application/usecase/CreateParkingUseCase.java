package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.parking.model.enums.ParkingState.UNAVAILABLE;

import br.com.digitalparking.parking.application.validator.ParkingTimeValidator;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.shared.model.enums.PaymentState;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateParkingUseCase {

  private static final BigDecimal PRICE_PER_HOUR = new BigDecimal(5);
  private final ParkingService parkingService;
  private final UserFromSecurityContext userFromSecurityContext;
  private final VehicleService vehicleService;
  private final ParkingTimeValidator parkingTimeValidator;

  public CreateParkingUseCase(ParkingService parkingService,
      UserFromSecurityContext userFromSecurityContext, VehicleService vehicleService,
      ParkingTimeValidator parkingTimeValidator) {
    this.parkingService = parkingService;
    this.userFromSecurityContext = userFromSecurityContext;
    this.vehicleService = vehicleService;
    this.parkingTimeValidator = parkingTimeValidator;
  }

  @Transactional
  public Parking execute(Parking parking) {
    var user = userFromSecurityContext.getUser();
    var vehicle = vehicleService.findVehicleByIdRequired(parking.getVehicle().getId());
    parkingTimeValidator.validate(parking);
    updateAttributesToSave(parking, user, vehicle);
    return parkingService.save(parking);
  }

  private void updateAttributesToSave(Parking parking, User user, Vehicle vehicle) {
    parking.setUser(user);
    parking.setVehicle(vehicle);
    parking.setParkingState(UNAVAILABLE);
    parking.setInitialParking(LocalDateTime.now());
    calculateFinalParkingTime(parking);
    calculateParkingPrice(parking);
  }

  private void calculateParkingPrice(Parking parking) {
    if (parking.getFinalParking() != null) {
      var hour = calculateNumberOfHoursParking(parking);
      var price = PRICE_PER_HOUR.multiply(new BigDecimal(hour));
      generateParkingPayment(parking, price);
    }
  }

  private void generateParkingPayment(Parking parking, BigDecimal price) {
    var parkingPayment = ParkingPayment.builder()
        .parking(parking)
        .paymentValue(price)
        .paymentMethod(PaymentMethod.CREDIT_CARD)
        .paymentState(PaymentState.NOT_PAID)
        .build();
    parking.setParkingPayment(parkingPayment);
  }

  private long calculateNumberOfHoursParking(Parking parking) {
    return ChronoUnit.HOURS.between(parking.getInitialParking(), parking.getFinalParking());
  }

  private void calculateFinalParkingTime(Parking parking) {
    if (parking.getParkingTime() != null && parking.getParkingTime() > 0) {
      parking.setFinalParking(parking.getInitialParking().plusHours(parking.getParkingTime()));
    }
  }
}
