package br.com.digitalparking.parking.presentation.api;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_CITY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_COUNTRY;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_LATITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_LONGITUDE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_NEIGHBORHOOD;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_PARKING_STATE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_STATE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.ALTERNATE_PARKING_STREET;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_TIME;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_TYPE;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_UUID;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.PARKING_UPDATE_TEMPLATE_INPUT;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParkingWith;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createNewVehicle;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PutParkingApiTest {

  private static final String URL_PARKING = "/parking/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  private final TestAuthentication testAuthentication;

  @Autowired
  PutParkingApiTest(MockMvc mockMvc, EntityManager entityManager,
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

  private String createParkingInput(User user, Vehicle vehicle) {
    var initialParking = LocalDateTime.now();
    var finalParking = initialParking.plusHours(
        ParkingTime.valueOf(DEFAULT_PARKING_TIME).getHour());
    return PARKING_UPDATE_TEMPLATE_INPUT.formatted(
        vehicle.getId().toString(), user.getId().toString(), ALTERNATE_PARKING_LATITUDE,
        ALTERNATE_PARKING_LONGITUDE, ALTERNATE_PARKING_STREET, ALTERNATE_PARKING_NEIGHBORHOOD,
        ALTERNATE_PARKING_CITY, ALTERNATE_PARKING_STATE, ALTERNATE_PARKING_COUNTRY,
        ALTERNATE_PARKING_PARKING_STATE, DEFAULT_PARKING_TYPE.name(), DEFAULT_PARKING_TIME,
        initialParking, finalParking);
  }

  @Test
  void shouldUpdateParking() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parking = createAndPersistParking(user, vehicle);
    var parkingInput = createParkingInput(user, vehicle);

    var request = put(URL_PARKING + parking.getId())
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
    assertThat(parkingFound.getStreet()).isEqualTo(ALTERNATE_PARKING_STREET);
  }

  @Test
  void shouldReturnNotFoundWhenParkingWasNotFoundToUpdate() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle);

    var request = put(URL_PARKING + DEFAULT_PARKING_UUID)
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenParkingUuidIsInvalid() throws Exception {
    var user = createUserWithVehicle();
    var vehicle = user.getVehicles().get(0);
    var parkingInput = createParkingInput(user, vehicle);

    var request = put(URL_PARKING + "abc")
        .contentType(APPLICATION_JSON)
        .content(parkingInput)
        .with(testAuthentication.defineAuthenticatedUser(user));
    mockMvc.perform(request).andExpect(status().isBadRequest());
  }
}
