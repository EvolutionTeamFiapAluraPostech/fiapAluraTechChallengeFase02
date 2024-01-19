package br.com.digitalparking.shared.model.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

  PIX("Pix"),
  CREDIT_CARD("Credit Card"),
  DEBIT_CARD("Debit Card");

  private final String description;

  PaymentMethod(String description) {
    this.description = description;
  }

}
