package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static br.com.digitalparking.shared.testData.user.UserTestData.createUser;
import static br.com.digitalparking.shared.testData.vehicle.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.application.validator.ParkingTimeValidator;
import br.com.digitalparking.parking.application.validator.PaymentMethodPixValidator;
import br.com.digitalparking.parking.application.validator.UserDefaultPaymentMethodRequiredValidator;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.enums.PaymentMethod;
import br.com.digitalparking.user.infrastructure.security.UserFromSecurityContext;
import br.com.digitalparking.user.model.entity.UserPaymentMethod;
import br.com.digitalparking.vehicle.model.service.VehicleService;
import java.util.UUID;
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
  @Mock
  private ParkingTimeValidator parkingTimeValidator;
  @Mock
  private UserDefaultPaymentMethodRequiredValidator userDefaultPaymentMethodRequiredValidator;
  @Mock
  private PaymentMethodPixValidator paymentMethodPixValidator;
  @InjectMocks
  private CreateParkingUseCase createParkingUseCase;

  @Test
  void shouldCreateParking() {
    var user = createUser();
    var userPaymentMethod = UserPaymentMethod.builder()
        .id(UUID.randomUUID())
        .user(user)
        .paymentMethod(PaymentMethod.CREDIT_CARD).build();
    user.setUserPaymentMethod(userPaymentMethod);
    var vehicle = createVehicle();
    var parking = createNewParking();
    when(userFromSecurityContext.getUser()).thenReturn(user);
    when(vehicleService.findVehicleByIdRequired(parking.getVehicle().getId())).thenReturn(vehicle);
    when(parkingService.save(parking)).then(returnsFirstArg());

    var parkingSaved = createParkingUseCase.execute(parking);

    assertThat(parkingSaved).isNotNull();
    assertThat(parkingSaved).usingRecursiveComparison().isEqualTo(parking);
    verify(parkingTimeValidator).validate(parking);
    verify(userDefaultPaymentMethodRequiredValidator).validate(user);
    verify(paymentMethodPixValidator).validate(parking);
  }
}