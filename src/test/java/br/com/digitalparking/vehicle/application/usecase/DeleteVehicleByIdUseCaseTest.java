package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteVehicleByIdUseCaseTest {

  @Mock
  private VehicleService vehicleService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private DeleteVehicleByIdUseCase deleteVehicleByIdUseCase;

  @Test
  void shouldDeleteVehicleByIdWhenVehicleExists() {
    var vehicle = createVehicle();
    when(vehicleService.findVehicleByIdRequired(vehicle.getId())).thenReturn(vehicle);

    assertDoesNotThrow(() -> deleteVehicleByIdUseCase.execute(vehicle.getId().toString()));
    verify(uuidValidator).validate(vehicle.getId().toString());
  }

}