package br.com.digitalparking.parking.model.service;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createNewParking;
import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.shared.exception.NoResultException;
import java.util.Optional;
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
    var parking = createNewParking();
    when(parkingRepository.save(parking)).thenReturn(parking);

    var parkingSaved = parkingService.save(parking);

    assertThat(parkingSaved).isNotNull();
    assertThat(parkingSaved).usingRecursiveComparison().isEqualTo(parking);
  }

  @Test
  void shouldFindParkingWhenItExists() {
    var parking = createParking();
    when(parkingRepository.findById(parking.getId())).thenReturn(Optional.of(parking));

    var parkingFound = parkingService.findById(parking.getId());

    assertThat(parkingFound).isNotNull();
    assertThat(parkingFound).usingRecursiveComparison().isEqualTo(parking);
  }

  @Test
  void shouldThrowExceptionWhenParkingDoesNotExist() {
    var parking = createParking();
    when(parkingRepository.findById(parking.getId())).thenReturn(Optional.empty());

    assertThrows(NoResultException.class, () -> parkingService.findById(parking.getId()));
  }
}