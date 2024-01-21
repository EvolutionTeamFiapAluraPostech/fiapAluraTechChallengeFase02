package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetParkingByIdUseCase {

  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;

  public GetParkingByIdUseCase(ParkingService parkingService, UuidValidator uuidValidator) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
  }

  public Parking execute(String uuid) {
    uuidValidator.validate(uuid);
    return parkingService.findById(UUID.fromString(uuid));
  }
}
