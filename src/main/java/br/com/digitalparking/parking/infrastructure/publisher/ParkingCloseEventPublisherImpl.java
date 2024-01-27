package br.com.digitalparking.parking.infrastructure.publisher;

import br.com.digitalparking.parking.application.event.ParkingCloseEvent;
import br.com.digitalparking.parking.application.event.ParkingCloseEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ParkingCloseEventPublisherImpl implements ParkingCloseEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ParkingCloseEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publishEvent(ParkingCloseEvent parkingCloseEvent) {
    applicationEventPublisher.publishEvent(parkingCloseEvent);
  }
}
