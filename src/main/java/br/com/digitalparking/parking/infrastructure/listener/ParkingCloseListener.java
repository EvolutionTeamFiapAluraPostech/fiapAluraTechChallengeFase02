package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingCloseEvent;
import br.com.digitalparking.parking.infrastructure.httpclient.ParkingNotification;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingCloseListener {

  private final ParkingNotification parkingNotification;

  public ParkingCloseListener(ParkingNotification parkingNotification) {
    this.parkingNotification = parkingNotification;
  }

  @Async
  @EventListener
  public void notifyParkingClose(ParkingCloseEvent parkingCloseEvent) {
    var parkingOutputDto = ParkingOutputDto.from(parkingCloseEvent.parking());
    parkingNotification.notifyParkingClose(parkingOutputDto.id(), parkingOutputDto);
  }
}
