package br.com.digitalparking.vehicle.presentation.dto;

import br.com.digitalparking.vehicle.model.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "VehicleOutputDto", description = "DTO de saída para representação de um veículo")
public record VehicleOutputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "Valid UUID")
    String id,
    @Schema(example = "Honda HRV", description = "Marca e modelo do veículo")
    String description,
    @Schema(example = "ABC-1E98 ou ABC-1056", description = "Placa do veículo")
    String licensePlate,
    @Schema(example = "Branco", description = "Cor do veículo")
    String color) {

  public VehicleOutputDto(Vehicle vehicleCreated) {
    this(vehicleCreated.getId().toString(), vehicleCreated.getDescription(),
        vehicleCreated.getLicensePlate(), vehicleCreated.getColor());
  }

  public static VehicleOutputDto from(Vehicle vehicleCreated) {
    return new VehicleOutputDto(vehicleCreated);
  }

  public static List<VehicleOutputDto> toList(List<Vehicle> vehicles) {
    return vehicles.stream().map(VehicleOutputDto::new).toList();
  }
}
