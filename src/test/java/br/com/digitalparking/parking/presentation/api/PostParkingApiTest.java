package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_CITY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_COUNTRY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_LATITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_LONGITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_NEIGHBORHOOD;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_STATE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_STREET;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.PARKING_TEMPLATE_INPUT;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostParkingApiTest {

  private final String URL_PARKING = "/parking";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  PostParkingApiTest(MockMvc mockMvc, EntityManager entityManager,
      TestAuthentication testAuthentication) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
    this.testAuthentication = testAuthentication;
  }

  private User createUserWithVehicle() {
    var vehicle = createAndPersistVehicle();
    var user = createNewUser();
    user.add(vehicle);
    var paymentMethod = createUserPaymentMethod(user);
    user.setUserPaymentMethod(paymentMethod);
    return entityManager.merge(user);
  }

  private User createUserWithoutVehicle() {
    var user = createNewUser();
    var paymentMethod = createUserPaymentMethod(user);
    user.setUserPaymentMethod(paymentMethod);
    return entityManager.merge(user);
  }

  private UserPaymentMethod createUserPaymentMethod(User user) {
    return UserPaymentMethod.builder()
        .user(user)
        .paymentMethod(PaymentMethod.CREDIT_CARD)
        .build();
  }

  private Vehicle createAndPersistVehicle() {
    var vehicle = createNewVehicle();
    return entityManager.merge(vehicle);
  }

  private String createParkingInput(User user, Vehicle vehicle, ParkingType parkingType,
      ParkingTime parkingTime) {
    return PARKING_TEMPLATE_INPUT.formatted(
        vehicle.getId().toString(), user.getId().toString(), DEFAULT_PARKING_LATITUDE,
        DEFAULT_PARKING_LONGITUDE, DEFAULT_PARKING_STREET, DEFAULT_PARKING_NEIGHBORHOOD,
        DEFAULT_PARKING_CITY, DEFAULT_PARKING_STATE, DEFAULT_PARKING_COUNTRY,
        parkingType.name(), parkingTime.getDescription());
  }

  @Test
  void shouldCreateParkingWhenAllParkingAttributesAreCorrectAndParkingTypeIsFixed()
      throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle, ParkingType.FIXED, ParkingTime.ONE);

    var request = post(URL_PARKING)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var parkingFound = entityManager.find(Parking.class, UUID.fromString(id));
    assertThat(parkingFound).isNotNull();
  }

  @Test
  void shouldCreateParkingWhenAllParkingAttributesAreCorrectAndParkingTypeIsFree()
      throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle, ParkingType.FLEX, ParkingTime.ONE);

    var request = post(URL_PARKING)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var parkingFound = entityManager.find(Parking.class, UUID.fromString(id));
    assertThat(parkingFound).isNotNull();
  }

  @Test
  void shouldReturnNotFoundWhenParkingUserWasNotFound() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = createVehicle();
    var parkingInput = createParkingInput(user, vehicle, ParkingType.FIXED, ParkingTime.ONE);

    var request = post(URL_PARKING)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnNotFoundWhenParkingUserVehicleWasNotFound() throws Exception {
    var user = createUserWithoutVehicle();
    var vehicle = createVehicle();
    var parkingInput = createParkingInput(user, vehicle, ParkingType.FIXED, ParkingTime.ONE);

    var request = post(URL_PARKING)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenParkingTimeIsFixedAndHourExpectedIsZero() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle, ParkingType.FIXED, ParkingTime.UNLIMITED);

    var request = post(URL_PARKING)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }
}
