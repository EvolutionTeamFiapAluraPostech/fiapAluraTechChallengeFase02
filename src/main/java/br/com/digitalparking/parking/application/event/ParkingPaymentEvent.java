package br.com.digitalparking.parking.application.event;

import br.com.digitalparking.parking.model.entity.Parking;

public record ParkingPaymentEvent(Parking parking) {

}
