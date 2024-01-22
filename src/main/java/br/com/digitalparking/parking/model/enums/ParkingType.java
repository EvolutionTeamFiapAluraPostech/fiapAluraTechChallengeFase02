package br.com.digitalparking.parking.model.enums;

import lombok.Getter;

@Getter
public enum ParkingType {

  FIXED("Fixed hour"),
  FLEX("Flexible hour");

  private final String description;

  ParkingType(String description) {
    this.description = description;
  }
}
