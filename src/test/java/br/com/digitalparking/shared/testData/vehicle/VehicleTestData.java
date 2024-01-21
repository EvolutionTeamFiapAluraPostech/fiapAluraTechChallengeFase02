package br.com.digitalparking.shared.testData.vehicle;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import java.util.UUID;

public final class VehicleTestData {

  public static final UUID DEFAULT_VEHICLE_UUID = UUID.randomUUID();
  public static final String DEFAULT_VEHICLE_UUID_STRING = DEFAULT_VEHICLE_UUID.toString();
  public static final String DEFAULT_VEHICLE_DESCRIPTION = "Toyota Corolla";
  public static final String DEFAULT_VEHICLE_LICENSE_PLATE = "AAA-1A23";
  public static final String DEFAULT_VEHICLE_COLOR = "White";
  public static final String DEFAULT_VEHICLE_CREATED_BY = "Thomas Anderson";
  public static final UUID ALTERNATIVE_VEHICLE_UUID = UUID.randomUUID();
  public static final String ALTERNATIVE_VEHICLE_UUID_STRING = ALTERNATIVE_VEHICLE_UUID.toString();
  public static final String ALTERNATIVE_VEHICLE_DESCRIPTION = "Honda Civic";
  public static final String ALTERNATIVE_VEHICLE_LICENSE_PLATE = "BBB-2B34";
  public static final String ALTERNATIVE_VEHICLE_COLOR = "Black";
  public static final String ALTERNATIVE_VEHICLE_ACTIVE = "true";

  public static final String VEHICLE_TEMPLATE_INPUT = """
      {"description": "%s", "licensePlate": "%s", "color": "%s"}
      """;

  public static final String VEHICLE_INPUT = VEHICLE_TEMPLATE_INPUT.formatted(
      DEFAULT_VEHICLE_DESCRIPTION, DEFAULT_VEHICLE_LICENSE_PLATE, DEFAULT_VEHICLE_COLOR);

  public static final String VEHICLE_TEMPLATE_UPDATE = """
      {"description": "%s", "licensePlate": "%s", "color": "%s", "active": "%s"}
      """;
  public static final String VEHICLE_INPUT_TO_UPDATE = VEHICLE_TEMPLATE_UPDATE.formatted(
      ALTERNATIVE_VEHICLE_DESCRIPTION, ALTERNATIVE_VEHICLE_LICENSE_PLATE, ALTERNATIVE_VEHICLE_COLOR,
      ALTERNATIVE_VEHICLE_ACTIVE);


  public static Vehicle createNewVehicle() {
    return Vehicle.builder()
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .createdBy(DEFAULT_VEHICLE_CREATED_BY)
        .active(true)
        .build();
  }

  public static Vehicle createVehicle() {
    return Vehicle.builder()
        .id(UUID.randomUUID())
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_COLOR)
        .createdBy(DEFAULT_VEHICLE_CREATED_BY)
        .active(true)
        .build();
  }

}
