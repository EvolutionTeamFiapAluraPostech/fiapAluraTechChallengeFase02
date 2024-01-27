package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.model.enums.PaymentState.PAID;

import br.com.digitalparking.parking.application.event.ParkingPaymentEvent;
import br.com.digitalparking.parking.application.event.ParkingPaymentEventPublisher;
import br.com.digitalparking.parking.application.validator.PaymentHasAlreadyPaidValidator;
import br.com.digitalparking.parking.application.validator.PaymentValueValidator;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrUpdateParkingPaymentUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final PaymentValueValidator paymentValueValidator;
  private final PaymentHasAlreadyPaidValidator paymentHasAlreadyPaidValidator;
  private final ParkingPaymentEventPublisher parkingPaymentEventPublisher;

  public CreateOrUpdateParkingPaymentUseCase(ParkingService parkingService,
      UuidValidator uuidValidator,
      PaymentValueValidator paymentValueValidator,
      PaymentHasAlreadyPaidValidator paymentHasAlreadyPaidValidator,
      ParkingPaymentEventPublisher parkingPaymentEventPublisher) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
    this.paymentValueValidator = paymentValueValidator;
    this.paymentHasAlreadyPaidValidator = paymentHasAlreadyPaidValidator;
    this.parkingPaymentEventPublisher = parkingPaymentEventPublisher;
  }

  @Transactional
  public Parking execute(String uuid, ParkingPayment parkingPayment) {
    uuidValidator.validate(uuid);
    var parkingFound = parkingService.findById(UUID.fromString(uuid));
    paymentHasAlreadyPaidValidator.validate(parkingFound.getParkingPayment());
    paymentValueValidator.validate(parkingPayment);
    var parkingToSave = updatePayment(parkingFound, parkingPayment);
    var parkingSaved = parkingService.save(parkingToSave);
    notifyParkingPayment(parkingSaved);
    return parkingSaved;
  }

  private void notifyParkingPayment(Parking parkingSaved) {
    var parkingPaymentEvent = new ParkingPaymentEvent(parkingSaved);
    parkingPaymentEventPublisher.publishEvent(parkingPaymentEvent);
  }

  private Parking updatePayment(Parking parkingSaved, ParkingPayment parkingPayment) {
    var parkingPaymentSaved = parkingSaved.getParkingPayment();
    if (parkingSaved.isParkingPaymentSaved()) {
      parkingPayment.setPaymentState(PAID);
      parkingSaved.setParkingPayment(parkingPayment);
    } else {
      parkingPaymentSaved.setPaymentState(PAID);
      parkingPaymentSaved.setPaymentMethod(parkingPayment.getPaymentMethod());
      parkingPaymentSaved.setPaymentValue(parkingPayment.getPaymentValue());
      parkingSaved.setParkingPayment(parkingPaymentSaved);
    }
    return parkingSaved;
  }
}
