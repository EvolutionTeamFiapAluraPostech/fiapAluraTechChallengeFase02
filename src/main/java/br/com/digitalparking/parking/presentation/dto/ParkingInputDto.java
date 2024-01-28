package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.util.StringUtils;

public record ParkingInputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "UUID válido de um veículo")
    @NotBlank
    String vehicleId,
    @Schema(example = "-23.56404", description = "Latitude é a medida em graus de qualquer ponto da superfície da Terra até o Equador. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String latitude,
    @Schema(example = "-46.65219", description = "Longitude é a medida em graus de qualquer ponto da superfície da Terra até o Meridiano de Greenwich. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String longitude,
    @Schema(example = "Av Paulista", description = "A rua é o caminho utilizado por pessoas e diferentes veículos")
    @NotBlank
    String street,
    @Schema(example = "Bela Vista", description = "Bairro é uma comunidade ou região dentro de uma cidade ou município, sendo a unidade mínima de urbanização existente na maioria das cidades do mundo")
    @NotBlank
    String neighborhood,
    @Schema(example = "São Paulo", description = "O espaço urbano de um município delimitado por um perímetro urbano")
    @NotBlank
    String city,
    @Schema(example = "São Paulo", description = "O Estado é um conceito da Geografia que está vinculado ao ordenamento jurídico de um determinado território")
    @NotBlank
    String state,
    @Schema(example = "Brasil", description = "País é uma região geográfica considerada o território físico de um Estado Soberano, ou de uma menor ou antiga divisão política dentro de uma região geográfica")
    @NotBlank
    String country,
    @Schema(example = "FIXED, FLEX", description = "Tipo de estacionamento, com horário fixo ou flexível")
    @NotBlank
    String parkingType,
    @Schema(example = "1 ou 2", description = "Se o tipo de estacionamento escolhido for FIXED, será a quantidade de horas que o veículo permanecerá estacionado. Se o tipo de estacionamento escolhido for FLEX, a quantidade será indeterminada")
    String parkingTime) {

  public static Parking to(ParkingInputDto parkingInputDto) {
    var vehicle = Vehicle.builder().id(UUID.fromString(parkingInputDto.vehicleId)).build();
    var latitude =
        parkingInputDto.latitude != null ? new BigDecimal(parkingInputDto.latitude) : null;
    var longitude =
        parkingInputDto.longitude != null ? new BigDecimal(parkingInputDto.longitude) : null;
    var hour = 0;
    if (StringUtils.hasLength(parkingInputDto.parkingTime)) {
      var parkingTime = ParkingTime.valueOfDescription(parkingInputDto.parkingTime);
      hour = parkingTime.getHour();
    }

    return Parking.builder()
        .vehicle(vehicle)
        .latitude(latitude)
        .longitude(longitude)
        .street(parkingInputDto.street)
        .neighborhood(parkingInputDto.neighborhood)
        .city(parkingInputDto.city)
        .state(parkingInputDto.state)
        .country(parkingInputDto.country)
        .parkingType(ParkingType.valueOf(parkingInputDto.parkingType))
        .parkingTime(hour)
        .build();
  }
}
