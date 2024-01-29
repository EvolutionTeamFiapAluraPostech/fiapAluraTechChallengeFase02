package br.com.digitalparking.vehicle.application.usecase;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllVehiclesByUserUseCaseTest {

  @Mock
  private UserFromSecurityContext userFromSecurityContext;
  @InjectMocks
  private GetAllVehiclesByUserUseCase getAllVehiclesByUserUseCase;

  @Test
  void shouldGetAllVehiclesByUser() {
    var user = createUser();
    var vehicle = createVehicle();
    user.add(vehicle);
    when(userFromSecurityContext.getUser()).thenReturn(user);

    var vehicles = getAllVehiclesByUserUseCase.execute();

    assertThat(vehicles).isNotEmpty();
    assertThat(vehicles.get(0)).usingRecursiveComparison().isEqualTo(vehicle);
  }

}