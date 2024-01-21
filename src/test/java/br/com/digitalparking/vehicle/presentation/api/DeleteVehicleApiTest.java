package br.com.digitalparking.vehicle.presentation.api;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class DeleteVehicleApiTest {

  private static final String URL_VEHICLES = "/vehicles/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  DeleteVehicleApiTest(MockMvc mockMvc, EntityManager entityManager,
      TestAuthentication testAuthentication) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
    this.testAuthentication = testAuthentication;
  }

  private Vehicle createAndPersistVehicle() {
    var vehicle = createVehicle();
    return entityManager.merge(vehicle);
  }

  private User createAndPersistUser() {
    var user = createUser();
    return entityManager.merge(user);
  }

  @Test
  void shouldDeleteVehicleWhenVehicleExists() throws Exception {
    var vehicle = createAndPersistVehicle();
    var user = createAndPersistUser();

    var request = delete(URL_VEHICLES + vehicle.getId())
        .contentType(APPLICATION_JSON)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNoContent());

    var vehicleFound = entityManager.find(Vehicle.class, vehicle.getId());
    assertThat(vehicleFound).isNotNull();
    assertThat(vehicleFound.getDeleted()).isEqualTo(Boolean.TRUE);
  }

  @Test
  void shouldReturnNotFoundWhenVehicleUuidWasNotFound() throws Exception {
    var request = delete(URL_VEHICLES + UUID.randomUUID())
        .contentType(APPLICATION_JSON);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }
}
