package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.parking.model.enums.ParkingState.CLOSED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.application.event.ParkingCloseEventPublisher;
import br.com.digitalparking.parking.application.validator.ParkingPaymentStatePaidValidator;
import br.com.digitalparking.parking.application.validator.ParkingStateNotClosedValidator;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.shared.testData.parking.ParkingTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateParkingStateCloseUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private ParkingStateNotClosedValidator parkingStateNotClosedValidator;
  @Mock
  private ParkingPaymentStatePaidValidator parkingPaymentStatePaidValidator;
  @Mock
  private ParkingCloseEventPublisher parkingCloseEventPublisher;
  @InjectMocks
  private UpdateParkingStateCloseUseCase updateParkingStateCloseUseCase;

  @Test
  void shouldUpdateParkingStateClosedWhenParkingIsAvailable() {
    var parking = ParkingTestData.createParking();
    parking.setParkingState(CLOSED);
    when(parkingService.findById(parking.getId())).thenReturn(parking);

    assertDoesNotThrow(() -> updateParkingStateCloseUseCase.execute(parking.getId().toString()));
    verify(uuidValidator).validate(parking.getId().toString());
    verify(parkingStateNotClosedValidator).validate(parking);
    verify(parkingPaymentStatePaidValidator).validate(parking);
    verify(parkingCloseEventPublisher).publishEvent(any());
  }
}