package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_PAYMENT_METHOD;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_PAYMENT_VALUE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.PARKING_PAYMENT_TEMPLATE_INPUT;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParkingWith;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
@WireMockTest(httpPort = 7070)
class PutParkingPaymentApiTest {

  private static final String URL_PARKING = "/parking/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  PutParkingPaymentApiTest(MockMvc mockMvc, EntityManager entityManager,
      TestAuthentication testAuthentication) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
    this.testAuthentication = testAuthentication;
  }

  private User createUserWithVehicle() {
    var vehicle = createAndPersistVehicle();
    var user = createNewUser();
    user.add(vehicle);
    return entityManager.merge(user);
  }

  private Vehicle createAndPersistVehicle() {
    var vehicle = createNewVehicle();
    return entityManager.merge(vehicle);
  }

  private Parking createAndPersistParking(User user, Vehicle vehicle) {
    var parking = createNewParkingWith(user, vehicle);
    return entityManager.merge(parking);
  }

  private String createParkingPaymentInput() {
    return PARKING_PAYMENT_TEMPLATE_INPUT.formatted(
        DEFAULT_PARKING_PAYMENT_METHOD.name(), DEFAULT_PARKING_PAYMENT_VALUE);
  }

  private String createParkingPaymentWithoutPaymentMethodInput() {
    return PARKING_PAYMENT_TEMPLATE_INPUT.formatted(
        "", DEFAULT_PARKING_PAYMENT_VALUE);
  }

  private String createParkingPaymentWithPaymentValueZeroInput() {
    return PARKING_PAYMENT_TEMPLATE_INPUT.formatted(
        DEFAULT_PARKING_PAYMENT_METHOD, BigDecimal.ZERO);
  }

  @Test
  void shouldCreateOrUpdateParkingPayment() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle);
    var parkingInput = createParkingPaymentInput();
    stubFor(WireMock.put("/notifications/" + parking.getId()).willReturn(ok()));

    var request = put(URL_PARKING + parking.getId() + "/payment")
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var parkingFound = entityManager.find(Parking.class, UUID.fromString(id));
    assertThat(parkingFound).isNotNull();
    assertThat(parkingFound.getParkingPayment()).isNotNull();
    assertThat(parkingFound.getParkingPayment().getPaymentMethod()).isEqualTo(
        DEFAULT_PARKING_PAYMENT_METHOD);
    assertThat(parkingFound.getParkingPayment().getPaymentValue()).isEqualTo(
        DEFAULT_PARKING_PAYMENT_VALUE);
  }

  @Test
  void shouldReturnNotFoundWhenParkingWasNotFound() throws Exception {
    var user = createUserWithVehicle();
    var parkingInput = createParkingPaymentInput();

    var request = put(URL_PARKING + UUID.randomUUID() + "/payment")
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenParkingPaymentMethodWasNotProvided() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle);
    var parkingInput = createParkingPaymentWithoutPaymentMethodInput();

    var request = put(URL_PARKING + parking.getId() + "/payment")
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenParkingPaymentValueIsZero() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle);
    var parkingInput = createParkingPaymentWithPaymentValueZeroInput();

    var request = put(URL_PARKING + parking.getId() + "/payment")
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }
}
