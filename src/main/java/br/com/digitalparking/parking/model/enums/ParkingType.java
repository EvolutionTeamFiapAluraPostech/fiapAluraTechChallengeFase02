package br.com.digitalparking.parking.model.enums;

import lombok.Getter;

@Getter
public enum ParkingType {

  FIXED("Hour fixed"),
  FREE("Free time");

  private final String description;

  ParkingType(String description) {
    this.description = description;
  }
}
