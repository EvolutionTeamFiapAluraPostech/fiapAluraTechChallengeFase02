package br.com.digitalparking.parking.presentation.api;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.infrastructure.TestAuthentication;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.persistence.EntityManager;
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

  @Test
  void shouldUpdateParkingStateClosedWhenParkingPaymentIsPaid() {

  }
}
