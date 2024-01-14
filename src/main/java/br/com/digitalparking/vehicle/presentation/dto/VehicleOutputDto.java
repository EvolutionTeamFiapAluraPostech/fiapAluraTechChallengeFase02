package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;

public record VehicleOutputDto(String id, String description, String licensePlate, String color) {

  public VehicleOutputDto(Vehicle vehicleCreated) {
    this(vehicleCreated.getId().toString(), vehicleCreated.getDescription(),
        vehicleCreated.getLicensePlate(), vehicleCreated.getColor());
  }

  public static VehicleOutputDto from(Vehicle vehicleCreated) {
    return new VehicleOutputDto(vehicleCreated);
  }
}
