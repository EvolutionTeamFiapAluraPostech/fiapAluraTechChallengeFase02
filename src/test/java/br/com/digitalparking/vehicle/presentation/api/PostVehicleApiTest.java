package br.com.digitalparking.vehicle.presentation.api;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_COLOR;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_DESCRIPTION;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_LICENSE_PLATE;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.VEHICLE_INPUT;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.VEHICLE_TEMPLATE_INPUT;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.util.StringUtil;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostVehicleApiTest {

  private static final String URL_VEHICLES = "/vehicles";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  public PostVehicleApiTest(MockMvc mockMvc, EntityManager entityManager,
      TestAuthentication testAuthentication) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
    this.testAuthentication = testAuthentication;
  }

  @Test
  void shouldCreateVehicle() throws Exception {
    var user = createUser();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(VEHICLE_INPUT)
        .with(testAuthentication.defineAuthenticatedUser(user));

    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var vehicleFound = entityManager.find(Vehicle.class, UUID.fromString(id));
    assertThat(vehicleFound).isNotNull();
    assertThat(vehicleFound.getDescription()).isEqualTo(DEFAULT_VEHICLE_DESCRIPTION);
    assertThat(vehicleFound.getLicensePlate()).isEqualTo(DEFAULT_VEHICLE_LICENSE_PLATE);
    assertThat(vehicleFound.getColor()).isEqualTo(DEFAULT_VEHICLE_COLOR);
  }

  @Test
  void shouldReturnBadRequestWhenVehicleDescriptionWasNotFilled() throws Exception {
    var user = createUser();

    var vehicle = Vehicle.builder()
        .description("")
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleDescriptionLengthIsGreaterThan500Characters() throws Exception {
    var user = createUser();
    var vehicleDescription = StringUtil.generateStringLength(501);

    var vehicle = Vehicle.builder()
        .description(vehicleDescription)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleLicensePlateWasNotFilled() throws Exception {
    var user = createUser();

    var vehicle = Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate("")
        .color(DEFAULT_VEHICLE_COLOR)
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleLicensePlateLengthIsGreaterThan10Characters() throws Exception {
    var user = createUser();
    var vehicleLicensePlate = StringUtil.generateStringLength(20);

    var vehicle = Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(vehicleLicensePlate)
        .color(DEFAULT_VEHICLE_COLOR)
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleColorWasNotFilled() throws Exception {
    var user = createUser();

    var vehicle = Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color("")
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenVehicleColorLengthIsGreaterThan50Characters() throws Exception {
    var user = createUser();
    var vehicleColor = StringUtil.generateStringLength(51);

    var vehicle = Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(vehicleColor)
        .build();

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(
            VEHICLE_TEMPLATE_INPUT.formatted(vehicle.getDescription(), vehicle.getLicensePlate(),
                vehicle.getColor()))
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnConflictWhenVehicleWasAlreadyRegistered() throws Exception {
    var user = createUser();
    var vehicle = Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .build();
    user.add(vehicle);
    entityManager.merge(user);

    var request = post(URL_VEHICLES)
        .contentType(APPLICATION_JSON)
        .content(VEHICLE_INPUT)
        .with(testAuthentication.defineAuthenticatedUser(user));

    mockMvc.perform(request).andExpect(status().isConflict());
  }
}