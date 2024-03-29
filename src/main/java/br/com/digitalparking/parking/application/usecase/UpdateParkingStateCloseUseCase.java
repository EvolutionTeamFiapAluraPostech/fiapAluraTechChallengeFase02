package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.parking.model.enums.ParkingState.CLOSED;

import br.com.digitalparking.parking.application.event.ParkingCloseEvent;
import br.com.digitalparking.parking.application.event.ParkingCloseEventPublisher;
import br.com.digitalparking.parking.application.validator.ParkingPaymentStatePaidValidator;
import br.com.digitalparking.parking.application.validator.ParkingStateNotClosedValidator;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateParkingStateCloseUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final ParkingStateNotClosedValidator parkingStateNotClosedValidator;
  private final ParkingPaymentStatePaidValidator parkingPaymentStatePaidValidator;
  private final ParkingCloseEventPublisher parkingCloseEventPublisher;

  public UpdateParkingStateCloseUseCase(ParkingService parkingService, UuidValidator uuidValidator,
      ParkingStateNotClosedValidator parkingStateNotClosedValidator,
      ParkingPaymentStatePaidValidator parkingPaymentStatePaidValidator,
      ParkingCloseEventPublisher parkingCloseEventPublisher) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
    this.parkingStateNotClosedValidator = parkingStateNotClosedValidator;
    this.parkingPaymentStatePaidValidator = parkingPaymentStatePaidValidator;
    this.parkingCloseEventPublisher = parkingCloseEventPublisher;
  }

  @Transactional
  public void execute(String uuid) {
    uuidValidator.validate(uuid);
    var parking = parkingService.findById(UUID.fromString(uuid));
    parkingStateNotClosedValidator.validate(parking);
    parkingPaymentStatePaidValidator.validate(parking);
    parking.setParkingState(CLOSED);
    var parkingSaved = parkingService.save(parking);
    notifyParkingClose(parkingSaved);
  }

  private void notifyParkingClose(Parking parkingSaved) {
    var parkingCloseEvent = new ParkingCloseEvent(parkingSaved);
    parkingCloseEventPublisher.publishEvent(parkingCloseEvent);
  }
}
