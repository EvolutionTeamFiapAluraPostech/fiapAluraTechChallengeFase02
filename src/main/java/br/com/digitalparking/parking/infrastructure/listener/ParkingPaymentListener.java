package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingPaymentEvent;
import br.com.digitalparking.parking.infrastructure.httpclient.ParkingNotification;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingPaymentListener {

  private final ParkingNotification parkingNotification;

  public ParkingPaymentListener(ParkingNotification parkingNotification) {
    this.parkingNotification = parkingNotification;
  }

  @Async
  @EventListener
  public void notifyParkingPayment(ParkingPaymentEvent parkingPaymentEvent) {
    var parkingOutputDto = ParkingOutputDto.from(parkingPaymentEvent.parking());
    parkingNotification.notifyParkingPayment(parkingOutputDto.id(), parkingOutputDto);
  }
}
