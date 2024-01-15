package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_UUID;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.DEFAULT_VEHICLE_UUID_STRING;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetVehicleByIdAndUserUseCaseTest {

  @Mock
  private VehicleService vehicleService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private UserFromSecurityContext userFromSecurityContext;
  @InjectMocks
  private GetVehicleByIdAndUserUseCase getVehicleByIdAndUserUseCase;

  @Test
  void shouldFindVehicleByIdAndUserWhenVehicleExists() {
    var vehicle = createVehicle();
    var user = createUser();
    user.add(vehicle);
    when(vehicleService.findVehicleByIdRequired(vehicle.getId())).thenReturn(vehicle);
    when(userFromSecurityContext.getUser()).thenReturn(user);

    var vehicleFound = getVehicleByIdAndUserUseCase.execute(vehicle.getId().toString());

    assertThat(vehicleFound).isNotNull();
    assertThat(vehicleFound.getId()).isEqualTo(vehicle.getId());
    assertThat(vehicleFound.getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
    verify(uuidValidator).validate(vehicle.getId().toString());
  }

  @Test
  void shouldReturnNullWhenVehicleDoesNotExist() {
    var user = createUser();
    when(vehicleService.findVehicleByIdRequired(DEFAULT_VEHICLE_UUID)).thenReturn(null);
    when(userFromSecurityContext.getUser()).thenReturn(user);

    var vehicleFound = getVehicleByIdAndUserUseCase.execute(DEFAULT_VEHICLE_UUID_STRING);

    assertThat(vehicleFound).isNull();
    verify(uuidValidator).validate(DEFAULT_VEHICLE_UUID_STRING);
  }

}