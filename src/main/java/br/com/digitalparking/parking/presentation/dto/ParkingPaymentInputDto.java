package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.ParkingPayment;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ParkingPaymentInputDto(
    @NotBlank
    String paymentMethod,
    @Positive
    BigDecimal paymentValue
) {

    public static ParkingPayment to(ParkingPaymentInputDto parkingPaymentInputDto) {
        return ParkingPayment.builder()
            .paymentMethod(PaymentMethod.valueOf(parkingPaymentInputDto.paymentMethod))
            .paymentValue(parkingPaymentInputDto.paymentValue)
            .build();
    }
}
