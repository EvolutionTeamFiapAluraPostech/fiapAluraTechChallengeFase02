package br.com.digitalparking.shared.infrastructure;

import br.com.digitalparking.user.model.entity.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@Component
public class TestAuthentication {

  public RequestPostProcessor defineAuthenticatedUser(User user) {
    return SecurityMockMvcRequestPostProcessors.user(user);
  }

}
