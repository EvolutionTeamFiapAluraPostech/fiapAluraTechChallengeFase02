package br.com.digitalparking.parking.model.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum ParkingType {

  FIXED("Fixed hour"),
  FLEX("Flexible hour");

  private final String description;
  private static final Map<String, ParkingType> BY_DESCRIPTION = new HashMap<>();

  ParkingType(String description) {
    this.description = description;
  }

  static {
    for (ParkingType parkingType : values()) {
      BY_DESCRIPTION.put(parkingType.description, parkingType);
    }
  }

  public static ParkingType valueOfDescription(String description) {
    return BY_DESCRIPTION.get(description);
  }
}
