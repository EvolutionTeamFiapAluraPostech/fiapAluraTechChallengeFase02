package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.model.enums.PaymentState.NOT_PAID;
import static br.com.digitalparking.shared.model.enums.PaymentState.PAID;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParkingWith;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingPayment;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.model.enums.PaymentState;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
@WireMockTest(httpPort = 7070)
class PutParkingStateClosedApiTest {

  private static final String URL_PARKING = "/parking/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;


  @Autowired
  PutParkingStateClosedApiTest(MockMvc mockMvc, EntityManager entityManager,
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

  private Parking createAndPersistParking(User user, Vehicle vehicle, PaymentState paymentState) {
    var parking = createNewParkingWith(user, vehicle);
    var parkingPayment = createParkingPayment();
    parkingPayment.setPaymentState(paymentState);
    parking.setParkingPayment(parkingPayment);
    return entityManager.merge(parking);
  }

  @Test
  void shouldUpdateParkingStateClosedWhenParkingPaymentIsPaid() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle, PAID);

    var request = put(URL_PARKING + parking.getId() + "/close")
        .contentType(APPLICATION_JSON)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNoContent());
  }

  @Test
  void shouldReturnBadRequestWhenParkingPaymentIsNotPaid() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle, NOT_PAID);

    var request = put(URL_PARKING + parking.getId() + "/close")
        .contentType(APPLICATION_JSON)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenParkingWasNotFound() throws Exception {
    var user = createUserWithVehicle();

    var request = put(URL_PARKING + UUID.randomUUID() + "/close")
        .contentType(APPLICATION_JSON)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNotFound());
  }
}
