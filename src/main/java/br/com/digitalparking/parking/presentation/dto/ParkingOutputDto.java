package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.user.presentation.dto.UserOutputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import java.time.LocalDateTime;

public record ParkingOutputDto(
    String id,
    VehicleOutputDto vehicleOutputDto,
    UserOutputDto userOutputDto,
    String latitude,
    String longitude,
    String street,
    String neighborhood,
    String city,
    String state,
    String country,
    String parkingState,
    String parkingType,
    String parkingTime,
    LocalDateTime initialParking,
    LocalDateTime finalParking,
    ParkingPaymentOutputDto parkingPaymentOutputDto) {

  public ParkingOutputDto(Parking parking) {
    this(parking.getId().toString(),
        parking.getVehicle() != null ? VehicleOutputDto.from(parking.getVehicle()) : null,
        parking.getUser() != null ? UserOutputDto.from(parking.getUser()) : null,
        parking.getLatitude().toString(), parking.getLongitude().toString(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(),
        parking.getState(), parking.getCountry(),
        parking.getParkingState() != null ? parking.getParkingState().name() : null,
        parking.getParkingType() != null ? parking.getParkingType().name() : null,
        parking.getParkingTime() > 0 ?
            ParkingTime.valueOfHour(parking.getParkingTime())
                .getDescription() : "",
        parking.getInitialParking(), parking.getFinalParking(),
        parking.getParkingPayment() != null ? ParkingPaymentOutputDto.from(
            parking.getParkingPayment()) : null);
  }

  public static ParkingOutputDto from(Parking parking) {
    return new ParkingOutputDto(parking);
  }
}
