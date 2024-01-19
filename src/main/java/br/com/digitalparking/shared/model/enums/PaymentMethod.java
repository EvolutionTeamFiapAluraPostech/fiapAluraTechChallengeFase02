package br.com.digitalparking.shared.model.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

  PIX("Pix"),
  CARTAO_CREDITO("Cartão de crédito"),
  CARTAO_DEBITO("Cartão de débito");

  private final String description;

  PaymentMethod(String description) {
    this.description = description;
  }

}
