package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createNewVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.service.UserService;
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
  @Mock
  private UserFromSecurityContext userFromSecurityContext;
  @Mock
  private UserService userService;
  @InjectMocks
  private CreateVehicleUseCase createVehicleUseCase;

  @Test
  void shouldCreateNewVehicleWhenAllVehicleAttributesAreCorrect() {
    var vehicle = createNewVehicle();
    var user = createUser();
    when(vehicleService.save(vehicle)).then(returnsFirstArg());
    when(userFromSecurityContext.getUser()).thenReturn(user);
    when(userService.save(user)).then(returnsFirstArg());

    var vehicleSaved = createVehicleUseCase.execute(vehicle);

    assertThat(vehicleSaved).isNotNull();
    assertThat(vehicleSaved.getDescription()).isEqualTo(vehicle.getDescription());
    assertThat(vehicleSaved.getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
    assertThat(vehicleSaved.getColor()).isEqualTo(vehicle.getColor());
    verify(vehicleLicensePlateValidator).validate(vehicle.getLicensePlate());
    verify(vehicleService).save(vehicle);
    verify(userService).save(user);
  }

}