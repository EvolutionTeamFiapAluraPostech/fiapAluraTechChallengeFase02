package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParkingWith;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetParkingByIdApiTest {

  private final String URL_PARKING = "/parking/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  GetParkingByIdApiTest(MockMvc mockMvc, EntityManager entityManager,
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

  private Parking createAndPersistParking(User user, Vehicle vehicle){
    var parking = createNewParkingWith(user, vehicle);
    return entityManager.merge(parking);
  }

  @Test
  void shouldReturnOkWhenFindParkingById() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle);

    var request = get(URL_PARKING + parking.getId())
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isOk());
  }

  @Test
  void shouldReturnNotFoundWhenParkingDoesNotExist() throws Exception {
    var user = createUserWithVehicle();
    var request = get(URL_PARKING + UUID.randomUUID())
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenParkingUuidIsInvalid() throws Exception {
    var user = createUserWithVehicle();
    var request = get(URL_PARKING + "Abc")
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }
}
