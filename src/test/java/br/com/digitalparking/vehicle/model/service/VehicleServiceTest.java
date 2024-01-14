package br.com.digitalparking.vehicle.model.service;

import static org.junit.jupiter.api.Assertions.*;

import br.com.digitalparking.vehicle.repository.VehicleRepository;
import org.junit.Test;
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

  }

}