package br.com.digitalparking.parking.application.event;

public interface ParkingCloseEventPublisher {

  void publishEvent(final ParkingCloseEvent parkingCloseEvent);
}
