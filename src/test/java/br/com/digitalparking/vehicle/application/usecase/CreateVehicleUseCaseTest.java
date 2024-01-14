package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.createNewVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.vehicle.application.validation.VehicleLicensePlateValidator;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateVehicleUseCaseTest {

  @Mock
  private VehicleService vehicleService;
  @Mock
  private VehicleLicensePlateValidator vehicleLicensePlateValidator;
  @InjectMocks
  private CreateVehicleUseCase createVehicleUseCase;

  @Test
  void shouldCreateNewVehicleWhenAllVehicleAttributesAreCorrect() {
    var vehicle = createNewVehicle();
    when(vehicleService.save(vehicle)).then(returnsFirstArg());

    var vehicleSaved = createVehicleUseCase.execute(vehicle);

    assertThat(vehicleSaved).isNotNull();
    assertThat(vehicleSaved.getDescription()).isEqualTo(vehicle.getDescription());
    assertThat(vehicleSaved.getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
    assertThat(vehicleSaved.getColor()).isEqualTo(vehicle.getColor());
    verify(vehicleLicensePlateValidator).validate(vehicle);
    verify(vehicleService).save(vehicle);
  }

}