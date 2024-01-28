package br.com.digitalparking.shared.testData.parking;

import static br.com.digitalparking.parking.model.enums.ParkingState.BUSY;
import static br.com.digitalparking.parking.model.enums.ParkingType.FIXED;
import static br.com.digitalparking.shared.model.enums.PaymentMethod.CREDIT_CARD;
import static br.com.digitalparking.shared.model.enums.PaymentState.NOT_PAID;
import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ParkingTestData {

  public static final UUID DEFAULT_PARKING_UUID = UUID.randomUUID();
  public static final String DEFAULT_PARKING_UUID_STRING = DEFAULT_PARKING_UUID.toString();
  public static final String DEFAULT_PARKING_LATITUDE = "-23.56404";
  public static final String DEFAULT_PARKING_LONGITUDE = "-46.65219";
  public static final String DEFAULT_PARKING_STREET = "Av Paulista, n. 1000";
  public static final String DEFAULT_PARKING_NEIGHBORHOOD = "Bela Vista";
  public static final String DEFAULT_PARKING_CITY = "São Paulo";
  public static final String DEFAULT_PARKING_STATE = "SP";
  public static final String DEFAULT_PARKING_COUNTRY = "Brasil";
  public static final ParkingType DEFAULT_PARKING_TYPE = FIXED;
  public static final String DEFAULT_PARKING_TIME = "TWO";
  public static final ParkingState DEFAULT_PARKING_PARKING_STATE = BUSY;
  public static final PaymentMethod DEFAULT_PARKING_PAYMENT_METHOD = CREDIT_CARD;
  public static final BigDecimal DEFAULT_PARKING_PAYMENT_VALUE = new BigDecimal("5.00");
  public static final String ALTERNATE_PARKING_LATITUDE = "-23.58808";
  public static final String ALTERNATE_PARKING_LONGITUDE = "-46.63323";
  public static final String ALTERNATE_PARKING_STREET = "Rua Vergueiro, 3185";
  public static final String ALTERNATE_PARKING_NEIGHBORHOOD = "Vila Mariana";
  public static final String ALTERNATE_PARKING_CITY = "São Paulo";
  public static final String ALTERNATE_PARKING_STATE = "SP";
  public static final String ALTERNATE_PARKING_COUNTRY = "Brasil";
  public static final ParkingState ALTERNATE_PARKING_PARKING_STATE = BUSY;

  public static final String PARKING_TEMPLATE_INPUT = """
      {"vehicleId": "%s", "userId": "%s", "latitude": "%s", "longitude": "%s", "street": "%s",
      "neighborhood": "%s", "city": "%s", "state": "%s", "country": "%s", "parkingType": "%s",
      "parkingTime": "%s"}
      """;

  public static final String PARKING_UPDATE_TEMPLATE_INPUT = """
      {"vehicleId": "%s", "userId": "%s", "latitude": "%s", "longitude": "%s", "street": "%s",
      "neighborhood": "%s", "city": "%s", "state": "%s", "country": "%s", "parkingState": "%s",
      "parkingType": "%s", "parkingTime": "%s", "initialParking": "%s", "finalParking": "%s"}
      """;

  public static final String PARKING_PAYMENT_TEMPLATE_INPUT = """
      {"paymentMethod": "%s", "paymentValue": "%s"}
      """;

  public static Parking createNewParking() {
    var vehicle = createVehicle();
    var user = createUser();
    return createNewParkingWith(user, vehicle);
  }

  public static Parking createNewParkingWith(User user, Vehicle vehicle) {
    var initialParking = LocalDateTime.now();
    var parkingTime = ParkingTime.valueOf(DEFAULT_PARKING_TIME);
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

  public static Parking createParking() {
    var parking = createNewParking();
    parking.setId(UUID.randomUUID());
    return parking;
  }

  public static Parking createParkingWithPayment() {
    var parkingPayment = createParkingPayment();
    var parking = createParking();
    parking.setParkingPayment(parkingPayment);
    return parking;
  }

  public static ParkingPayment createParkingPayment() {
    return ParkingPayment.builder()
        .id(UUID.randomUUID())
        .paymentMethod(CREDIT_CARD)
        .paymentValue(new BigDecimal("5.00"))
        .paymentState(NOT_PAID)
        .build();
  }
}
