package br.com.digitalparking.shared.application.validation;

import static br.com.digitalparking.shared.model.messages.SharedMessages.PAYMENT_METHOD_INVALID;

import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class PaymentMethodValidator {

  public void validate(String paymentMethod) {
    if (paymentMethod == null || paymentMethod.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "paymentMethod",
          PAYMENT_METHOD_INVALID));
    }
    var paymentValidated = Arrays.stream(PaymentMethod.values()).anyMatch(
        paymentMethodCorrect -> paymentMethodCorrect.name().equals(paymentMethod));
    if (!paymentValidated) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "paymentMethod",
          PAYMENT_METHOD_INVALID.formatted(paymentMethod)));
    }
  }
}
