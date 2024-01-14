package br.com.digitalparking.vehicle.presentation.api;

import br.com.digitalparking.vehicle.application.usecase.CreateVehicleUseCase;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

  private final CreateVehicleUseCase createVehicleUseCase;

  public VehicleApi(CreateVehicleUseCase createVehicleUseCase) {
    this.createVehicleUseCase = createVehicleUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VehicleOutputDto postVehicle(@RequestBody @Valid VehicleInputDto vehicleInputDto) {
    var vehicle = vehicleInputDto.toVehicle();
    var vehicleCreated = createVehicleUseCase.execute(vehicle);
    return VehicleOutputDto.from(vehicleCreated);
  }
}
