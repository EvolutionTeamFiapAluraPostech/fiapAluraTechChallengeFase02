package br.com.digitalparking.user.application.usecase;

import br.com.digitalparking.shared.application.validation.PaymentMethodValidator;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import br.com.digitalparking.user.model.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserPaymentMethodUserUseCase {

  private final UserService userService;
  private final UuidValidator uuidValidator;
  private final PaymentMethodValidator paymentMethodValidator;

  public UpdateUserPaymentMethodUserUseCase(UserService userService, UuidValidator uuidValidator,
      PaymentMethodValidator paymentMethodValidator) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
    this.paymentMethodValidator = paymentMethodValidator;
  }

  @Transactional
  public User execute(String userUuid, String paymentMethod) {
    uuidValidator.validate(userUuid);
    var user = userService.findUserByIdRequired(UUID.fromString(userUuid));
    paymentMethodValidator.validate(paymentMethod);
    setPaymentMethodTo(paymentMethod, user);
    return userService.save(user);
  }

  private void setPaymentMethodTo(String paymentMethodDescription, User user) {
    var paymentMethod = PaymentMethod.valueOfDescription(paymentMethodDescription);
    var userPaymentMethod = UserPaymentMethod.builder().user(user)
        .paymentMethod(paymentMethod).build();
    user.setUserPaymentMethod(userPaymentMethod);
  }
}
