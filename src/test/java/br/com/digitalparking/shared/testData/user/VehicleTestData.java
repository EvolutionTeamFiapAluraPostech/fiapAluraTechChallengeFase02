package br.com.digitalparking.shared.testData.user;

import br.com.digitalparking.vehicle.model.entity.Vehicle;

public final class VehicleTestData {

  public static final String DEFAULT_VEHICLE_DESCRIPTION = "Toyota Corolla";
  public static final String DEFAULT_VEHICLE_LICENSE_PLATE = "AAA-1A24";
  public static final String DEFAULT_VEHICLE_COLOR = "White";

  public static Vehicle createNewVehicle() {
    return Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .build();
  }

}
