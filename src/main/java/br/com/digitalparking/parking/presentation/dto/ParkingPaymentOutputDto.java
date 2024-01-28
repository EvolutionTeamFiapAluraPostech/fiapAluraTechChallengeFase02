package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;

@Tag(name = "ParkingPaymentOutputDto", description = "DTO de saída para representação de pagamento de estacionamento")
public record ParkingPaymentOutputDto(
    @Schema(example = "PIX, CREDIT_CARD, DEBIT_CARD", description = "Tipos de pagamentos aceitos")
    String paymentMethod,
    @Schema(example = "NOT_PAID, PAID", description = "Pagamento realizado ou não")
    String paymentState,
    @Schema(example = "5.00", description = "Valor do pagamento")
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
