package br.com.digitalparking.vehicle.model.entity;

import br.com.digitalparking.shared.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(schema = "parking", name = "vehicles")
public class Vehicle extends BaseEntity {

  @Column(nullable = false)
  private Boolean active = true;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String licensePlate;

  @Column
  private String color;

}
