package br.com.digitalparking.shared.testData.parking;

import static br.com.digitalparking.parking.model.enums.ParkingState.CLOSED;
import static br.com.digitalparking.parking.model.enums.ParkingType.FIXED;
import static br.com.digitalparking.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.DEFAULT_VEHICLE_UUID_STRING;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ParkingTestData {

  public static final String DEFAULT_PARKING_LATITUDE = "-23.56404";
  public static final String DEFAULT_PARKING_LONGITUDE = "-46.65219";
  public static final String DEFAULT_PARKING_STREET = "Av Paulista, n. 1000";
  public static final String DEFAULT_PARKING_NEIGHBORHOOD = "Bela Vista";
  public static final String DEFAULT_PARKING_CITY = "São Paulo";
  public static final String DEFAULT_PARKING_STATE = "SP";
  public static final String DEFAULT_PARKING_COUNTRY = "Brasil";
  public static final ParkingType DEFAULT_PARKING_TYPE = FIXED;
  public static final String DEFAULT_PARKING_TIME = "2-Hours";
  public static final ParkingState DEFAULT_PARKING_PARKING_STATE = CLOSED;

  public static final String PARKING_TEMPLATE_INPUT = """
      {"vehicleId": "%s", "userId": "%s", "latitude": "%s", "longitude": "%s", "street": "%s",
      "neighborhood": "%s", "city": "%s", "state": "%s", "country": "%s", "parkingType": "%s",
      "parkingTime": "%s"}
      """;

  public static final String PARKING_INPUT = PARKING_TEMPLATE_INPUT.formatted(
      DEFAULT_VEHICLE_UUID_STRING, DEFAULT_USER_UUID_FROM_STRING, DEFAULT_PARKING_LATITUDE,
      DEFAULT_PARKING_LONGITUDE, DEFAULT_PARKING_STREET, DEFAULT_PARKING_NEIGHBORHOOD,
      DEFAULT_PARKING_CITY, DEFAULT_PARKING_STATE, DEFAULT_PARKING_COUNTRY,
      DEFAULT_PARKING_TYPE.name(), DEFAULT_PARKING_TIME);

  public static Parking createNewParking() {
    var vehicle = createVehicle();
    var user = createUser();
    var initialParking = LocalDateTime.now();
    var parkingTime = ParkingTime.valueOfDescription(DEFAULT_PARKING_TIME);
    var finalParking = initialParking.plusHours(parkingTime.getHour());

    return Parking.builder()
        .vehicle(vehicle)
        .user(user)
        .latitude(new BigDecimal(DEFAULT_PARKING_LATITUDE))
        .longitude(new BigDecimal(DEFAULT_PARKING_LONGITUDE))
        .street(DEFAULT_PARKING_STREET)
        .neighborhood(DEFAULT_PARKING_NEIGHBORHOOD)
        .city(DEFAULT_PARKING_CITY)
        .country(DEFAULT_PARKING_COUNTRY)
        .parkingType(DEFAULT_PARKING_TYPE)
        .parkingTime(parkingTime.getHour())
        .initialParking(initialParking)
        .finalParking(finalParking)
        .parkingState(DEFAULT_PARKING_PARKING_STATE)
        .build();
  }
}
