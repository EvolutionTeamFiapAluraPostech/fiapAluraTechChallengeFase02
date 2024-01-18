package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record VehicleInputDto(
    @NotBlank(message = "Description is required.")
    @Length(max = 500, message = "Max description length is 500 characters.")
    String description,
    @NotBlank(message = "License plate is required.")
    @Length(max = 10, message = "Max license plate length is 10 characters.")
    String licensePlate,
    @NotBlank(message = "Color is required.")
    @Length(max = 10, message = "Max color length is 50 characters.")
    String color,
    Boolean active) {

  public static Vehicle toVehicle(VehicleInputDto vehicleInputDto) {
    var activeVehicle = vehicleInputDto.active != null ? vehicleInputDto.active : true;
    return Vehicle.builder()
        .description(vehicleInputDto.description)
        .licensePlate(vehicleInputDto.licensePlate)
        .color(vehicleInputDto.color)
        .active(activeVehicle)
        .build();
  }
}
