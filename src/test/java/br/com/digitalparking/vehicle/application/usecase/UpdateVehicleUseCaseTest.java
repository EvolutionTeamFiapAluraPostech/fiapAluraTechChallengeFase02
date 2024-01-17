package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_DESCRIPTION;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_LICENSE_PLATE;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.vehicle.application.validation.VehicleLicensePlateInOtherVehicleValidator;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateVehicleUseCaseTest {

  @Mock
  private VehicleService vehicleService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private VehicleLicensePlateInOtherVehicleValidator vehicleLicensePlateInOtherVehicleValidator;
  @InjectMocks
  private UpdateVehicleUseCase updateVehicleUseCase;

  @Test
  void shouldUpdateVehicle() {
    var vehicle = createVehicle();
    var vehicleToUpdate = Vehicle.builder()
        .id(vehicle.getId())
        .description(DEFAULT_VEHICLE_DESCRIPTION)
        .licensePlate(DEFAULT_VEHICLE_LICENSE_PLATE)
        .color(DEFAULT_VEHICLE_LICENSE_PLATE)
        .build();

    when(vehicleService.findVehicleByIdRequired(vehicle.getId())).thenReturn(vehicleToUpdate);
    when(vehicleService.save(any())).thenReturn(vehicleToUpdate);

    var vehicleUpdated = updateVehicleUseCase.execute(vehicle.getId().toString(), vehicleToUpdate);

    assertThat(vehicleUpdated).isNotNull();
    assertThat(vehicleUpdated).usingRecursiveComparison().isEqualTo(vehicleToUpdate);
    verify(uuidValidator).validate(vehicleToUpdate.getId().toString());
    verify(vehicleLicensePlateInOtherVehicleValidator).validate(vehicle.getLicensePlate(), vehicle.getId().toString());
    verify(vehicleService).save(vehicleToUpdate);
  }

}