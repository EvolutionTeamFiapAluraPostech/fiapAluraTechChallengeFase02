package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateParkingUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private UpdateParkingUseCase updateParkingUseCase;

  @Test
  void shouldUpdateParking() {
    var parking = createParking();
    var parkingToUpdate = createParking();
    parkingToUpdate.setParkingTime(1);
    when(parkingService.findById(parking.getId())).thenReturn(parkingToUpdate);
    when(parkingService.save(any())).thenReturn(parkingToUpdate);

    var parkingUpdated = updateParkingUseCase.execute(parking.getId().toString(), parking);

    assertThat(parkingUpdated).isNotNull();
    assertThat(parkingUpdated).usingRecursiveComparison().isEqualTo(parkingToUpdate);
    verify(uuidValidator).validate(parking.getId().toString());
  }
}
