package br.com.digitalparking.vehicle.application.validation;

import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createNewVehicle;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.DuplicatedException;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleLicensePlateValidatorTest {

  @Mock
  private UserFromSecurityContext userFromSecurityContext;
  @InjectMocks
  private VehicleLicensePlateValidator vehicleLicensePlateValidator;

  @Test
  void shouldValidateVehicleWhenVehicleLicensePlateDoesNotExist() {
    var vehicle = createNewVehicle();
    var user = createUser();
    user.setVehicles(Collections.emptyList());
    when(userFromSecurityContext.getUser()).thenReturn(user);

    assertDoesNotThrow(() -> vehicleLicensePlateValidator.validate(vehicle));
  }

  @Test
  void shouldThrowExceptionWhenVehicleLicensePlateAlreadyExists() {
    var vehicle = createNewVehicle();
    var user = createUser();
    user.setVehicles(List.of(vehicle));
    when(userFromSecurityContext.getUser()).thenReturn(user);

    assertThrows(DuplicatedException.class, () -> vehicleLicensePlateValidator.validate(vehicle));
  }

}