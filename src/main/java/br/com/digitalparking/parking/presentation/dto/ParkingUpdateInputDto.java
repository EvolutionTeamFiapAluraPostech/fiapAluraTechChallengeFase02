package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.StringUtils;

public record ParkingUpdateInputDto(
    @NotBlank
    String vehicleId,
    String latitude,
    String longitude,
    @NotBlank
    String street,
    @NotBlank
    String neighborhood,
    @NotBlank
    String city,
    @NotBlank
    String state,
    @NotBlank
    String country,
    String parkingState,
    @NotBlank
    String parkingType,
    String parkingTime,
    LocalDateTime initialParking,
    LocalDateTime finalParking) {

  public static Parking to(ParkingUpdateInputDto parkingInputDto) {
    var vehicle = Vehicle.builder().id(UUID.fromString(parkingInputDto.vehicleId)).build();
    var latitude =
        parkingInputDto.latitude != null ? new BigDecimal(parkingInputDto.latitude) : null;
    var longitude =
        parkingInputDto.longitude != null ? new BigDecimal(parkingInputDto.longitude) : null;
    var hour = 0;
    if (StringUtils.hasLength(parkingInputDto.parkingTime)) {
      var parkingTime = ParkingTime.valueOf(parkingInputDto.parkingTime);
      hour = parkingTime.getHour();
    }

    return Parking.builder()
        .vehicle(vehicle)
        .latitude(latitude)
        .longitude(longitude)
        .street(parkingInputDto.street)
        .neighborhood(parkingInputDto.neighborhood)
        .city(parkingInputDto.city)
        .state(parkingInputDto.state)
        .country(parkingInputDto.country)
        .parkingState(ParkingState.valueOf(parkingInputDto.parkingState))
        .parkingType(ParkingType.valueOf(parkingInputDto.parkingType))
        .parkingTime(hour)
        .initialParking(parkingInputDto.initialParking)
        .finalParking(parkingInputDto.finalParking)
        .build();
  }
}
