package br.com.digitalparking.parking.presentation.api;

import br.com.digitalparking.parking.application.usecase.CreateParkingUseCase;
import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingApi {

  private final CreateParkingUseCase createParkingUseCase;

  public ParkingApi(CreateParkingUseCase createParkingUseCase) {
    this.createParkingUseCase = createParkingUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ParkingOutputDto postParking(@RequestBody @Valid ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    var parkingSaved = createParkingUseCase.execute(parking);
    return ParkingOutputDto.from(parkingSaved);
  }
}
