package br.com.digitalparking.parking.application.validator;

import static br.com.digitalparking.user.model.messages.UserMessages.USER_DEFAULT_PAYMENT_METHOD_REQUIRED;

import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.user.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserDefaultPaymentMethodRequiredValidator {

  public void validate(User user) {
    if (user.getUserPaymentMethod() == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), "user default payment method",
              USER_DEFAULT_PAYMENT_METHOD_REQUIRED));
    }
  }
}
