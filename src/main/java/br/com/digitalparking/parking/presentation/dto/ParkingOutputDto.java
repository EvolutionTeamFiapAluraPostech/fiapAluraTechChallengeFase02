package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.user.presentation.dto.UserOutputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;

@Tag(name = "ParkingOutputDto", description = "DTO de saída para registrar o cadatro de estacionamento")
public record ParkingOutputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "Valid UUID")
    String id,
    @Schema(description = "Veículo cadastrado no estacionamento")
    VehicleOutputDto vehicleOutputDto,
    @Schema(description = "Condutor do veículo/usuário do aplicativo cadastrado no estacionamento")
    UserOutputDto userOutputDto,
    @Schema(example = "-23.56404", description = "Latitude é a medida em graus de qualquer ponto da superfície da Terra até o Equador. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String latitude,
    @Schema(example = "-46.65219", description = "Longitude é a medida em graus de qualquer ponto da superfície da Terra até o Meridiano de Greenwich. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String longitude,
    @Schema(example = "Av Paulista", description = "A rua é o caminho utilizado por pessoas e diferentes veículos")
    String street,
    @Schema(example = "Bela Vista", description = "Bairro é uma comunidade ou região dentro de uma cidade ou município, sendo a unidade mínima de urbanização existente na maioria das cidades do mundo")
    String neighborhood,
    @Schema(example = "São Paulo", description = "O espaço urbano de um município delimitado por um perímetro urbano")
    String city,
    @Schema(example = "São Paulo", description = "O Estado é um conceito da Geografia que está vinculado ao ordenamento jurídico de um determinado território")
    String state,
    @Schema(example = "Brasil", description = "País é uma região geográfica considerada o território físico de um Estado Soberano, ou de uma menor ou antiga divisão política dentro de uma região geográfica")
    String country,
    @Schema(example = "OPEN, BUSY, CLOSED", description = "Uma vaga de estacionamento pode estar disponível, ocupada ou encerrada")
    String parkingState,
    @Schema(example = "FIXED, FLEX", description = "Tipo de estacionamento, com horário fixo ou flexível")
    String parkingType,
    @Schema(example = "1 ou 2", description = "Se o tipo de estacionamento escolhido for FIXED, será a quantidade de horas que o veículo permanecerá estacionado. Se o tipo de estacionamento escolhido for FLEX, a quantidade será indeterminada")
    String parkingTime,
    @Schema(example = "2024-01-28T10:15:52.522561979", description = "Hora inicial do estacionamento.", format = "LocalDateTime")
    LocalDateTime initialParking,
    @Schema(example = "2024-01-28T12:15:52.522561979", description = "Hora final do estacionamento", format = "LocalDateTime")
    LocalDateTime finalParking,
    ParkingPaymentOutputDto parkingPaymentOutputDto) {

  public ParkingOutputDto(Parking parking) {
    this(parking.getId().toString(),
        parking.getVehicle() != null ? VehicleOutputDto.from(parking.getVehicle()) : null,
        parking.getUser() != null ? UserOutputDto.from(parking.getUser()) : null,
        parking.getLatitude().toString(), parking.getLongitude().toString(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(),
        parking.getState(), parking.getCountry(),
        parking.getParkingState() != null ? parking.getParkingState().name() : null,
        parking.getParkingType() != null ? parking.getParkingType().name() : null,
        parking.getParkingTime() > 0 ?
            ParkingTime.valueOfHour(parking.getParkingTime())
                .getDescription() : "",
        parking.getInitialParking(), parking.getFinalParking(),
        parking.getParkingPayment() != null ? ParkingPaymentOutputDto.from(
            parking.getParkingPayment()) : null);
  }

  public static ParkingOutputDto from(Parking parking) {
    return new ParkingOutputDto(parking);
  }
}
