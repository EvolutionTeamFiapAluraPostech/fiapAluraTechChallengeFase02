package br.com.digitalparking.vehicle.presentation.api;

import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.api.JsonUtil;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.testData.user.VehicleTestData;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetVehicleByIdApiTest {

  private static final String URL_VEHICLES = "/vehicles/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  public GetVehicleByIdApiTest(MockMvc mockMvc, EntityManager entityManager,
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

    var request = get(URL_VEHICLES + vehicle.getId())
        .with(testAuthentication.defineAuthenticatedUser(user));
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var vehicleFound = JsonUtil.fromJson(contentAsString, Vehicle.class);
    var vehicleDtoFound = VehicleOutputDto.from(vehicleFound);
    var vehicleOutputDtoExpected = VehicleOutputDto.from(vehicle);
    assertThat(vehicleDtoFound).usingRecursiveComparison().isEqualTo(vehicleOutputDtoExpected);
  }

  @Test
  void shouldReturnNotFoundWhenVehicleDoesNotExist() throws Exception {
    var request = get(URL_VEHICLES + UUID.randomUUID());
    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleHasInvalidUuid() throws Exception {
    var request = get(URL_VEHICLES + "abc");
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }
}
