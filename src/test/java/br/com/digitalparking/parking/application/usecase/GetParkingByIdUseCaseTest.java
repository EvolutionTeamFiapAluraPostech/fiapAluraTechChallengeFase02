package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_UUID;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.DEFAULT_PARKING_UUID_STRING;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParking;
import static org.assertj.core.api.Assertions.assertThat;
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
class GetParkingByIdUseCaseTest {

  @Mock
  private ParkingService parkingService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private GetParkingByIdUseCase getParkingByIdUseCase;

  @Test
  void shouldFindParkingByIdWhenItExists() {
    var parking = createParking();
    when(parkingService.findById(parking.getId())).thenReturn(parking);

    var parkingFound = getParkingByIdUseCase.execute(parking.getId().toString());

    assertThat(parkingFound).isNotNull();
    assertThat(parkingFound).usingRecursiveComparison().isEqualTo(parking);
    verify(uuidValidator).validate(parking.getId().toString());
  }

  @Test
  void shouldReturnNullWhenParkingDoesNotExist() {
    when(parkingService.findById(DEFAULT_PARKING_UUID)).thenReturn(null);

    var parkingFound = getParkingByIdUseCase.execute(DEFAULT_PARKING_UUID_STRING);

    assertThat(parkingFound).isNull();
    verify(uuidValidator).validate(DEFAULT_PARKING_UUID_STRING);
  }
}