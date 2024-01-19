package br.com.digitalparking.vehicle.presentation.api;

import br.com.digitalparking.vehicle.application.usecase.CreateVehicleUseCase;
import br.com.digitalparking.vehicle.application.usecase.DeleteVehicleByIdUseCase;
import br.com.digitalparking.vehicle.application.usecase.GetVehicleByIdAndUserUseCase;
import br.com.digitalparking.vehicle.application.usecase.UpdateVehicleUseCase;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

  private final CreateVehicleUseCase createVehicleUseCase;
  private final GetVehicleByIdAndUserUseCase getVehicleByIdAndUserUseCase;
  private final UpdateVehicleUseCase updateVehicleUseCase;
  private final DeleteVehicleByIdUseCase deleteVehicleByIdUseCase;

  public VehicleApi(CreateVehicleUseCase createVehicleUseCase,
      GetVehicleByIdAndUserUseCase getVehicleByIdAndUserUseCase,
      UpdateVehicleUseCase updateVehicleUseCase, DeleteVehicleByIdUseCase deleteVehicleByIdUseCase) {
    this.createVehicleUseCase = createVehicleUseCase;
    this.getVehicleByIdAndUserUseCase = getVehicleByIdAndUserUseCase;
    this.updateVehicleUseCase = updateVehicleUseCase;
    this.deleteVehicleByIdUseCase = deleteVehicleByIdUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VehicleOutputDto postVehicle(@RequestBody @Valid VehicleInputDto vehicleInputDto) {
    var vehicle = VehicleInputDto.toVehicle(vehicleInputDto);
    var vehicleCreated = createVehicleUseCase.execute(vehicle);
    return VehicleOutputDto.from(vehicleCreated);
  }

  @GetMapping("/{vehicleId}")
  @ResponseStatus(HttpStatus.OK)
  public VehicleOutputDto getVehicleById(@PathVariable String vehicleId) {
    var vehicle = getVehicleByIdAndUserUseCase.execute(vehicleId);
    return VehicleOutputDto.from(vehicle);
  }

  @PutMapping("/{vehicleUuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public VehicleOutputDto putVehicle(@PathVariable String vehicleUuid,
      @RequestBody @Valid VehicleInputDto vehicleInputDto) {
    var vehicle = VehicleInputDto.toVehicle(vehicleInputDto);
    var vehicleUpdated = updateVehicleUseCase.execute(vehicleUuid, vehicle);
    return VehicleOutputDto.from(vehicleUpdated);
  }

  @DeleteMapping("/{vehicleUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteVehicle(@PathVariable String vehicleUuid) {
    deleteVehicleByIdUseCase.execute(vehicleUuid);
  }
}
