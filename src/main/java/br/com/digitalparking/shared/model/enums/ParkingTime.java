package br.com.digitalparking.shared.model.enums;

import lombok.Getter;

@Getter
public enum ParkingTime {

  ONE_HOUR("1-Hour", 1),
  TWO_HOURS("2-Hours", 2);

  private final String description;
  private final Integer hour;

  ParkingTime(String parkingTime, Integer hour) {
    this.description = parkingTime;
    this.hour = hour;
  }

}
