package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateParkingUseCaseTest {
  @Mock
  private ParkingService parkingService;
  @Mock
  private UserFromSecurityContext userFromSecurityContext;
  @Mock
  private VehicleService vehicleService;
  @InjectMocks
  private CreateParkingUseCase createParkingUseCase;

  @Test
  void shouldCreateParking() {
    var user = createUser();
    var vehicle = createVehicle();
    var parking = createNewParking();
    when(userFromSecurityContext.getUser()).thenReturn(user);
    when(vehicleService.findVehicleByIdRequired(parking.getVehicle().getId())).thenReturn(vehicle);
    when(parkingService.save(parking)).then(returnsFirstArg());

    var parkingSaved = createParkingUseCase.execute(parking);

    assertThat(parkingSaved).isNotNull();
    assertThat(parkingSaved).usingRecursiveComparison().isEqualTo(parking);
  }
}