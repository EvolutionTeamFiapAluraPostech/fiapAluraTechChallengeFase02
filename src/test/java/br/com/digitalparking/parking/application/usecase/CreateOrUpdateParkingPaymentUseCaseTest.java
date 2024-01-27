package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.application.event.ParkingPaymentEvent;
import br.com.digitalparking.parking.application.event.ParkingPaymentEventPublisher;
import br.com.digitalparking.parking.application.validator.PaymentHasAlreadyPaidValidator;
import br.com.digitalparking.parking.application.validator.PaymentValueValidator;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateOrUpdateParkingPaymentUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private PaymentHasAlreadyPaidValidator paymentHasAlreadyPaidValidator;
  @Mock
  private PaymentValueValidator paymentValueValidator;
  @Mock
  private ParkingPaymentEventPublisher parkingPaymentEventPublisher;
  @InjectMocks
  private CreateOrUpdateParkingPaymentUseCase createOrUpdateParkingPaymentUseCase;

  @Test
  void shouldCreateParkingPayment() {
    var parking = createParkingWithPayment();
    var parkingPayment = parking.getParkingPayment();
    when(parkingService.findById(parking.getId())).thenReturn(parking);
    when(parkingService.save(parking)).then(returnsFirstArg());

    var parkingSaved = createOrUpdateParkingPaymentUseCase.execute(parking.getId().toString(),
        parkingPayment);

    assertThat(parkingSaved).isNotNull();
    assertThat(parkingSaved).usingRecursiveComparison().isEqualTo(parking);
    verify(uuidValidator).validate(parking.getId().toString());
    verify(paymentHasAlreadyPaidValidator).validate(parkingPayment);
    verify(paymentValueValidator).validate(parkingPayment);
    verify(parkingPaymentEventPublisher).publishEvent(new ParkingPaymentEvent(parkingSaved));
  }
}
