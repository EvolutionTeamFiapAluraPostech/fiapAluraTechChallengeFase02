package br.com.digitalparking.parking.application.event;

public interface ParkingPaymentEventPublisher {

  void publishEvent(final ParkingPaymentEvent parkingPaymentEvent);
}
