package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Tag(name = "VehicleInputDto", description = "DTO de entrada para representação de um veículo")
public record VehicleInputDto(
    @Schema(example = "Honda HRV", description = "Marca e modelo do veículo")
    @NotBlank(message = "Description is required.")
    @Length(max = 500, message = "Max description length is 500 characters.")
    String description,
    @Schema(example = "ABC-1E98 ou ABC-1056", description = "Placa do veículo")
    @NotBlank(message = "License plate is required.")
    @Length(max = 10, message = "Max license plate length is 10 characters.")
    String licensePlate,
    @Schema(example = "Branco", description = "Cor do veículo")
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
