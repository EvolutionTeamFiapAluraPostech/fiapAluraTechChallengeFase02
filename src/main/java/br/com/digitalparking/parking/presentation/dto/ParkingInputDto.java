package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.util.StringUtils;

public record ParkingInputDto(
    @NotBlank
    String vehicleId,
    @NotBlank
    String userId,
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
    @NotBlank
    String parkingType,
    String parkingTime,
    @NotBlank
    String paymentMethod) {

  public static Parking to(ParkingInputDto parkingInputDto) {
    var vehicle = Vehicle.builder().id(UUID.fromString(parkingInputDto.vehicleId)).build();
    var user = User.builder().id(UUID.fromString(parkingInputDto.userId)).build();
    var latitude =
        parkingInputDto.latitude != null ? new BigDecimal(parkingInputDto.latitude) : null;
    var longitude =
        parkingInputDto.longitude != null ? new BigDecimal(parkingInputDto.longitude) : null;
    var hour = 0;
    if (StringUtils.hasLength(parkingInputDto.parkingTime)) {
      var parkingTime = ParkingTime.valueOfDescription(parkingInputDto.parkingTime);
      hour = parkingTime.getHour();
    }

    return Parking.builder()
        .vehicle(vehicle)
        .user(user)
        .latitude(latitude)
        .longitude(longitude)
        .street(parkingInputDto.street)
        .neighborhood(parkingInputDto.neighborhood)
        .city(parkingInputDto.city)
        .state(parkingInputDto.state)
        .country(parkingInputDto.country)
        .parkingType(ParkingType.valueOf(parkingInputDto.parkingType))
        .parkingTime(hour)
        .paymentMethod(PaymentMethod.valueOf(parkingInputDto.paymentMethod))
        .build();
  }
}
