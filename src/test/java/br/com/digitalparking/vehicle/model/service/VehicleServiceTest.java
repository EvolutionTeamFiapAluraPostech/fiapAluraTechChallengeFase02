package br.com.digitalparking.vehicle.model.service;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.createNewVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.vehicle.infrastructure.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

  @Mock
  private VehicleRepository vehicleRepository;
  @InjectMocks
  private VehicleService vehicleService;

  @Test
  void shouldSaveVehicleWhenAllVehicleAttributesAreCorrect() {
    var vehicle = createNewVehicle();
    when(vehicleRepository.save(vehicle)).then(returnsFirstArg());

    var vehicleSaved = vehicleService.save(vehicle);

    assertThat(vehicleSaved).isNotNull();
    assertThat(vehicleSaved.getDescription()).isEqualTo(vehicle.getDescription());
    assertThat(vehicleSaved.getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
    assertThat(vehicleSaved.getColor()).isEqualTo(vehicle.getColor());
    verify(vehicleRepository).save(vehicle);
  }

}