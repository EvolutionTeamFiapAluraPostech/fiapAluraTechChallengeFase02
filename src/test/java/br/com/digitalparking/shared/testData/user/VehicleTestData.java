package br.com.digitalparking.shared.testData.user;

import br.com.digitalparking.vehicle.model.entity.Vehicle;

public final class VehicleTestData {

  public static final String DEFAULT_VEHICLE_DESCRIPTION = "Toyota Corolla";
  public static final String DEFAULT_VEHICLE_LICENSE_PLATE = "AAA-1A24";
  public static final String DEFAULT_VEHICLE_COLOR = "White";
  public static final String DEFAULT_VEHICLE_CREATED_BY = "Thomas Anderson";

  public static final String VEHICLE_TEMPLATE_INPUT = """
      {"description": "%s", "licensePlate": "%s", "color": "%s"}
      """;

  public static final String VEHICLE_INPUT = VEHICLE_TEMPLATE_INPUT.formatted(
      DEFAULT_VEHICLE_DESCRIPTION, DEFAULT_VEHICLE_LICENSE_PLATE, DEFAULT_VEHICLE_COLOR);

  public static Vehicle createNewVehicle() {
    return Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .createdBy(DEFAULT_VEHICLE_CREATED_BY)
        .build();
  }

}
