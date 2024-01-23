package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.model.enums.PaymentState.PAID;

import br.com.digitalparking.parking.application.event.ParkingPaymentEvent;
import br.com.digitalparking.parking.application.event.ParkingPaymentEventPublisher;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;

@Service
public class CreateParkingPaymentUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final ParkingPaymentEventPublisher parkingPaymentEventPublisher;

  public CreateParkingPaymentUseCase(ParkingService parkingService, UuidValidator uuidValidator,
      ParkingPaymentEventPublisher parkingPaymentEventPublisher) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
    this.parkingPaymentEventPublisher = parkingPaymentEventPublisher;
  }

  @Transactional
  public Parking execute(String uuid, ParkingPayment parkingPayment) {
    uuidValidator.validate(uuid);
    var parkingFound = parkingService.findById(UUID.fromString(uuid));
    validatePaymentValue(parkingPayment);
    var parkingToSave = updatePayment(parkingFound, parkingPayment);
    var parkingSaved = parkingService.save(parkingToSave);
    notifyParkingPayment(parkingSaved);
    return parkingSaved;
  }

  private void notifyParkingPayment(Parking parkingSaved) {
    var parkingPaymentEvent = new ParkingPaymentEvent(parkingSaved);
    parkingPaymentEventPublisher.publishEvent(parkingPaymentEvent);
  }

  private void validatePaymentValue(ParkingPayment parkingPayment) {
    if (parkingPayment.getPaymentValue().compareTo(BigDecimal.ZERO) == 0) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), "parkingPaymentValue",
              "Payment value must be greater than zero."));
    }
  }

  private Parking updatePayment(Parking parkingSaved, ParkingPayment parkingPayment) {
    parkingPayment.setPaymentState(PAID);
    parkingSaved.setParkingPayment(parkingPayment);
    return parkingSaved;
  }
}
