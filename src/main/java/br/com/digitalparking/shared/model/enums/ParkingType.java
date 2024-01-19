package br.com.digitalparking.shared.model.enums;

import lombok.Getter;

@Getter
public enum ParkingType {

  FIXED("Hour fixed"),
  FREE("Free time");

  private final String parkingType;

  ParkingType(String parkingType) {
    this.parkingType = parkingType;
  }
}
