package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_CITY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_COUNTRY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_LATITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_LONGITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_NEIGHBORHOOD;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_PAYMENT_METHOD;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_STATE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_STREET;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_TIME;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_TYPE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.PARKING_TEMPLATE_INPUT;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.shared.testData.vehicle.VehicleTestData;
import br.com.digitalparking.user.model.entity.User;
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
    return entityManager.merge(user);
  }

  private Vehicle createAndPersistVehicle() {
    var vehicle = VehicleTestData.createNewVehicle();
    return entityManager.merge(vehicle);
  }

  private String createParkingInput(User user, Vehicle vehicle) {
    return PARKING_TEMPLATE_INPUT.formatted(
        vehicle.getId().toString(), user.getId().toString(), DEFAULT_PARKING_LATITUDE,
        DEFAULT_PARKING_LONGITUDE, DEFAULT_PARKING_STREET, DEFAULT_PARKING_NEIGHBORHOOD,
        DEFAULT_PARKING_CITY, DEFAULT_PARKING_STATE, DEFAULT_PARKING_COUNTRY,
        DEFAULT_PARKING_TYPE.name(), DEFAULT_PARKING_TIME,
        DEFAULT_PARKING_PAYMENT_METHOD.name());
  }

  @Test
  void shouldCreateParking() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle);

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
}
