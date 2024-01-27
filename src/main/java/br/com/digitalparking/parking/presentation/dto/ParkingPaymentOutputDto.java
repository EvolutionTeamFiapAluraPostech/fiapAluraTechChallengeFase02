package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import java.math.BigDecimal;

public record ParkingPaymentOutputDto(
    String paymentMethod,
    String paymentState,
    BigDecimal paymentValue
) {

  public ParkingPaymentOutputDto(ParkingPayment parkingPayment) {
    this(parkingPayment.getPaymentMethod().name(),
        parkingPayment.getPaymentState().name(),
        parkingPayment.getPaymentValue());
  }

  public static ParkingPaymentOutputDto from(ParkingPayment parkingPayment) {
    return new ParkingPaymentOutputDto(parkingPayment);
  }
}
