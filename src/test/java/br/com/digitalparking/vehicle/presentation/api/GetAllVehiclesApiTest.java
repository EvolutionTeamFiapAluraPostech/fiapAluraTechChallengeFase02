package br.com.digitalparking.vehicle.presentation.api;

import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.testData.vehicle.VehicleTestData;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetAllVehiclesApiTest {

  private static final String URL_VEHICLES = "/vehicles";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  public GetAllVehiclesApiTest(MockMvc mockMvc, EntityManager entityManager,
      TestAuthentication testAuthentication) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
    this.testAuthentication = testAuthentication;
  }

  private User createAndPersistNewUser(Vehicle vehicle) {
    var user = createNewUser();
    user.add(vehicle);
    return entityManager.merge(user);
  }

  private Vehicle createAndPersistNewVehicle() {
    var vehicle = VehicleTestData.createNewVehicle();
    return entityManager.merge(vehicle);
  }

  @Test
  void shouldReturnVehicleWhenVehicleExistsAndLinkedToUser() throws Exception {
    var vehicle = createAndPersistNewVehicle();
    var user = createAndPersistNewUser(vehicle);

    var request = get(URL_VEHICLES)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andReturn();
  }
}
