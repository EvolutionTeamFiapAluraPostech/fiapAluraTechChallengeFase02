package br.com.digitalparking.shared.model.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum PaymentMethod {

  PIX("Pix"),
  CREDIT_CARD("Credit Card"),
  DEBIT_CARD("Debit Card");

  private final String description;
  private static final Map<String, PaymentMethod> BY_DESCRIPTION = new HashMap<>();

  PaymentMethod(String description) {
    this.description = description;
  }

  static {
    for (PaymentMethod paymentMethod : values()) {
      BY_DESCRIPTION.put(paymentMethod.description, paymentMethod);
    }
  }

  public static PaymentMethod valueOfDescription(String description) {
    return BY_DESCRIPTION.get(description);
  }
}
