package br.com.digitalparking.parking.presentation.api;

import br.com.digitalparking.parking.application.usecase.CreateParkingUseCase;
import br.com.digitalparking.parking.application.usecase.GetParkingByIdUseCase;
import br.com.digitalparking.parking.application.usecase.UpdateParkingUseCase;
import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingApi {

  private final CreateParkingUseCase createParkingUseCase;
  private final GetParkingByIdUseCase getParkingByIdUseCase;
  private final UpdateParkingUseCase updateParkingUseCase;

  public ParkingApi(CreateParkingUseCase createParkingUseCase,
      GetParkingByIdUseCase getParkingByIdUseCase, UpdateParkingUseCase updateParkingUseCase) {
    this.createParkingUseCase = createParkingUseCase;
    this.getParkingByIdUseCase = getParkingByIdUseCase;
    this.updateParkingUseCase = updateParkingUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ParkingOutputDto postParking(@RequestBody @Valid ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    var parkingSaved = createParkingUseCase.execute(parking);
    return ParkingOutputDto.from(parkingSaved);
  }

  @GetMapping("/{uuid}")
  @ResponseStatus(HttpStatus.OK)
  public ParkingOutputDto getParkingById(@PathVariable String uuid) {
    var parking = getParkingByIdUseCase.execute(uuid);
    return ParkingOutputDto.from(parking);
  }

  @PutMapping("/{uuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ParkingOutputDto putParking(@PathVariable String uuid,
      @RequestBody @Valid ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    var parkingUpdated = updateParkingUseCase.execute(uuid, parking);
    return ParkingOutputDto.from(parkingUpdated);
  }
}
