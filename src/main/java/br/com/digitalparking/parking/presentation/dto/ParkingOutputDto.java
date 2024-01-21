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
    LocalDateTime finalParking) {

  public ParkingOutputDto(Parking parking) {
    this(parking.getId().toString(), VehicleOutputDto.from(parking.getVehicle()),
        UserOutputDto.from(parking.getUser()), parking.getLatitude().toString(),
        parking.getLongitude().toString(), parking.getStreet(),
        parking.getNeighborhood(), parking.getCity(),
        parking.getState(), parking.getCountry(),
        parking.getState(), parking.getParkingType().name(),
        parking.getParkingTime() > 0 ?
            ParkingTime.valueOfHour(parking.getParkingTime())
            .getDescription() : "",
        parking.getInitialParking(), parking.getFinalParking());
  }

  public static ParkingOutputDto from(Parking parking) {
    return new ParkingOutputDto(parking);
  }
}
