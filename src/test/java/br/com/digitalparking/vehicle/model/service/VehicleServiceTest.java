package br.com.digitalparking.vehicle.model.service;

import static br.com.digitalparking.shared.testData.user.VehicleTestData.createNewVehicle;
import static br.com.digitalparking.shared.testData.user.VehicleTestData.createVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.shared.exception.NoResultException;
import br.com.digitalparking.vehicle.infrastructure.repository.VehicleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

  @Mock
  private VehicleRepository vehicleRepository;
  @InjectMocks
  private VehicleService vehicleService;

  @Test
  void shouldSaveVehicleWhenAllVehicleAttributesAreCorrect() {
    var vehicle = createNewVehicle();
    when(vehicleRepository.save(vehicle)).then(returnsFirstArg());

    var vehicleSaved = vehicleService.save(vehicle);

    assertThat(vehicleSaved).isNotNull();
    assertThat(vehicleSaved.getDescription()).isEqualTo(vehicle.getDescription());
    assertThat(vehicleSaved.getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
    assertThat(vehicleSaved.getColor()).isEqualTo(vehicle.getColor());
    verify(vehicleRepository).save(vehicle);
  }

  @Test
  void shouldFindVehicleByIdWhenVehicleExists() {
    var vehicle = createVehicle();
    when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

    var vehicleFound = vehicleService.findVehicleByIdRequired(vehicle.getId());

    assertThat(vehicleFound).isNotNull();
    assertThat(vehicleFound.getId()).isEqualTo(vehicle.getId());
  }

  @Test
  void shouldThrowsExceptionWhenVehicleNotExists() {
    var vehicleUuid = UUID.randomUUID();
    when(vehicleRepository.findById(vehicleUuid)).thenReturn(Optional.empty());

    assertThrows(NoResultException.class,
        () -> vehicleService.findVehicleByIdRequired(vehicleUuid));
  }

  @Test
  void shouldFindVehicleByLicensePlateWhenVehicleExists() {
    var vehicle = createVehicle();
    when(vehicleRepository.findByLicensePlate(vehicle.getLicensePlate())).thenReturn(
        List.of(vehicle));

    var vehicles = vehicleService.findVehicleByLicensePlate(vehicle.getLicensePlate());

    assertThat(vehicles).isNotNull();
    assertThat(vehicles.get(0).getId()).isEqualTo(vehicle.getId());
    assertThat(vehicles.get(0).getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
  }

  @Test
  void shouldThrowExceptionWhenVehicleDoesNotExist() {
    var vehicleLicensePlate = "ABC-1234";
    when(vehicleRepository.findByLicensePlate(vehicleLicensePlate)).thenReturn(
        Collections.emptyList());

    var vehicles = vehicleService.findVehicleByLicensePlate(vehicleLicensePlate);

    assertThat(vehicles.size()).isEqualTo(0);
  }

  @Test
  void shouldDeleteVehicleByIdWhenVehicleExists() {
    var vehicle = createVehicle();
    when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

    assertDoesNotThrow(() -> vehicleService.deleteVehicleById(vehicle.getId()));
  }

  @Test
  void shouldThrowExceptionWhenVehicleWillBeDeletedAndItDoesNotExist() {
    var vehicleUuid = UUID.randomUUID();
    when(vehicleRepository.findById(vehicleUuid)).thenReturn(Optional.empty());

    assertThrows(NoResultException.class, () -> vehicleService.deleteVehicleById(vehicleUuid));
  }

}