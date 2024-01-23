package br.com.digitalparking.user.presentation.api;

import static br.com.digitalparking.shared.testData.user.UserTestData.USER_PAYMENT_METHOD_UPDATE;
import static br.com.digitalparking.shared.testData.user.UserTestData.createNewUser;
import static br.com.digitalparking.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PatchUserPaymentMethodApiTest {

  private static final String URL_USERS = "/users/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PatchUserPaymentMethodApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private User createAndPersistUser() {
    var user = createNewUser();
    var userPaymentMethod = UserPaymentMethod.builder()
        .paymentMethod(PaymentMethod.CREDIT_CARD)
        .user(user)
        .build();
    user.setUserPaymentMethod(userPaymentMethod);
    return entityManager.merge(user);
  }

  @Test
  void shouldUpdateUserPaymentMethod() throws Exception {
    var user = createAndPersistUser();

    var request = patch(URL_USERS + user.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_PAYMENT_METHOD_UPDATE);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var userFound = entityManager.find(User.class, UUID.fromString(id));
    var userPaymentMethod = userFound.getUserPaymentMethod();
    assertThat(userFound).isNotNull();
    assertThat(userPaymentMethod.getPaymentMethod()).isEqualTo(PaymentMethod.PIX);
  }
}
