package br.com.digitalparking.parking.infrastructure.publisher;

import br.com.digitalparking.parking.application.event.ParkingPaymentEvent;
import br.com.digitalparking.parking.application.event.ParkingPaymentEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ParkingPaymentEventPublisherImpl implements ParkingPaymentEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ParkingPaymentEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publishEvent(final ParkingPaymentEvent parkingPaymentEvent) {
    applicationEventPublisher.publishEvent(parkingPaymentEvent);
  }
}
