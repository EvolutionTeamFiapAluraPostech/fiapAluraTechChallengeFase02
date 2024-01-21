package br.com.digitalparking.parking.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.shared.testData.parking.ParkingTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {
  @Mock
  private ParkingRepository parkingRepository;
  @InjectMocks
  private ParkingService parkingService;

  @Test
  void shouldSaveParkingWhenAllAttributesAreCorrect() {
    var parking = ParkingTestData.createNewParking();
    when(parkingRepository.save(parking)).thenReturn(parking);

    var parkingSaved = parkingService.save(parking);

    assertThat(parkingSaved).isNotNull();
    assertThat(parkingSaved).usingRecursiveComparison().isEqualTo(parking);
  }
}