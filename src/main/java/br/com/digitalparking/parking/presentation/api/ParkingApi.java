package br.com.digitalparking.parking.presentation.api;

import br.com.digitalparking.parking.application.usecase.CreateOrUpdateParkingPaymentUseCase;
import br.com.digitalparking.parking.application.usecase.CreateParkingUseCase;
import br.com.digitalparking.parking.application.usecase.GetParkingByIdUseCase;
import br.com.digitalparking.parking.application.usecase.UpdateParkingStateCloseUseCase;
import br.com.digitalparking.parking.application.usecase.UpdateParkingUseCase;
import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingPaymentInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingUpdateInputDto;
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
  private final CreateOrUpdateParkingPaymentUseCase createOrUpdateParkingPaymentUseCase;
  private final UpdateParkingStateCloseUseCase updateParkingStateCloseUseCase;

  public ParkingApi(CreateParkingUseCase createParkingUseCase,
      GetParkingByIdUseCase getParkingByIdUseCase, UpdateParkingUseCase updateParkingUseCase,
      CreateOrUpdateParkingPaymentUseCase createOrUpdateParkingPaymentUseCase,
      UpdateParkingStateCloseUseCase updateParkingStateCloseUseCase) {
    this.createParkingUseCase = createParkingUseCase;
    this.getParkingByIdUseCase = getParkingByIdUseCase;
    this.updateParkingUseCase = updateParkingUseCase;
    this.createOrUpdateParkingPaymentUseCase = createOrUpdateParkingPaymentUseCase;
    this.updateParkingStateCloseUseCase = updateParkingStateCloseUseCase;
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
      @RequestBody @Valid ParkingUpdateInputDto parkingUpdateInputDto) {
    var parking = ParkingUpdateInputDto.to(parkingUpdateInputDto);
    var parkingUpdated = updateParkingUseCase.execute(uuid, parking);
    return ParkingOutputDto.from(parkingUpdated);
  }

  @PutMapping("/{uuid}/payment")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ParkingOutputDto putParkingPayment(@PathVariable String uuid,
      @RequestBody @Valid ParkingPaymentInputDto parkingPaymentInputDto) {
    var parkingPayment = ParkingPaymentInputDto.to(parkingPaymentInputDto);
    var parkingSaved = createOrUpdateParkingPaymentUseCase.execute(uuid, parkingPayment);
    return ParkingOutputDto.from(parkingSaved);
  }

  @PutMapping("/{uuid}/close")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void putParkingStateClosed(@PathVariable String uuid) {
    updateParkingStateCloseUseCase.execute(uuid);
  }
}
