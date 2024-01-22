package br.com.digitalparking.parking.model.entity;

import br.com.digitalparking.shared.model.entity.BaseEntity;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.shared.model.enums.PaymentState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "parking_management", name = "parking_payment")
public class ParkingPayment extends BaseEntity {

  @OneToOne(mappedBy = "parkingPayment")
  private Parking parking;
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;
  @Enumerated(EnumType.STRING)
  private PaymentState paymentState;
  private BigDecimal paymentValue;
}
