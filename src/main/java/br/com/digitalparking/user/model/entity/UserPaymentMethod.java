package br.com.digitalparking.user.model.entity;

import br.com.digitalparking.shared.model.entity.BaseEntity;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "user_management", name = "users_payment_method")
@Where(clause = "deleted = false")
public class UserPaymentMethod extends BaseEntity {

  @OneToOne(mappedBy = "userPaymentMethod")
  private User user;
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;
}
