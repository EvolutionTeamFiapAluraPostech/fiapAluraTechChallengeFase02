package br.com.digitalparking.vehicle.presentation.api;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.ALTERNATIVE_VEHICLE_COLOR;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.ALTERNATIVE_VEHICLE_DESCRIPTION;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.ALTERNATIVE_VEHICLE_LICENSE_PLATE;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.VEHICLE_INPUT_TO_UPDATE;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createVehicle;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.testData.user.UserTestData;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class PutVehicleApiTest {

  private static final String URL_VEHICLES = "/vehicles/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  PutVehicleApiTest(MockMvc mockMvc, EntityManager entityManager,
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
    var user = UserTestData.createNewUser();
    return entityManager.merge(user);
  }

  @Test
  void shouldUpdateVehicle() throws Exception {
    var user = createAndPersistUser();
    var vehicle = createAndPersistVehicle();

    var request = MockMvcRequestBuilders.put(URL_VEHICLES + vehicle.getId())
        .contentType(APPLICATION_JSON)
        .content(VEHICLE_INPUT_TO_UPDATE)
        .with(testAuthentication.defineAuthenticatedUser(user));
    var mvcResult = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var vehicleFound = entityManager.find(Vehicle.class, UUID.fromString(id));
    assertThat(vehicleFound).isNotNull();
    assertThat(vehicleFound.getDescription()).isEqualTo(ALTERNATIVE_VEHICLE_DESCRIPTION);
    assertThat(vehicleFound.getLicensePlate()).isEqualTo(ALTERNATIVE_VEHICLE_LICENSE_PLATE);
    assertThat(vehicleFound.getColor()).isEqualTo(ALTERNATIVE_VEHICLE_COLOR);
  }

  @Test
  void shouldReturnBadRequestWhenVehicleWasNotFoundToUpdate() throws Exception {
    var vehicleUuid = UUID.randomUUID();

    var request = put(URL_VEHICLES + vehicleUuid)
        .contentType(APPLICATION_JSON)
        .content(VEHICLE_INPUT_TO_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleUuidIsInvalid() throws Exception {
    var vehicleUuid = "abc";

    var request = put(URL_VEHICLES + vehicleUuid)
        .contentType(APPLICATION_JSON)
        .content(VEHICLE_INPUT_TO_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

}
