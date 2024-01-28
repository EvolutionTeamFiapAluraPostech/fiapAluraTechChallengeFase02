package br.com.digitalparking.parking.presentation.api;

import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingPaymentInputDto;
import br.com.digitalparking.parking.presentation.dto.ParkingUpdateInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ParkingApi {

  @Operation(summary = "Cadastro de estacionamento de veículo",
      description = "Endpoint para cadastrar novo estacionamento de veículo, vinculado ao usuário logado no aplicativo",
      tags = {"ParkingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de método de pagamento preferido do usuário não cadastrado, para validação de tempo fixo escolhido, sem definição da quantidade de horas de estacionamento ou pagamento via PIX para período de estacionamento flexível",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})
  })
  ParkingOutputDto postParking(
      @Parameter(description = "DTO com atributos para se cadastrar um novo estacionamento de veículo. Requer validação de dados informados, como veículo, rua, bairro, cidade, Estado, país e tipo de estacionamento (tempo fixo ou flexível)")
      ParkingInputDto parkingInputDto);

  @Operation(summary = "Recupera um estacionamento de veículo",
      description = "Endpoint para recuperar um estacionamento de veículo pelo ID cadastrado",
      tags = {"ParkingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para estacionamento de veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  ParkingOutputDto getParkingById(
      @Parameter(description = "UUID válido do estacionamento do veículo")
      String uuid);

  @Operation(summary = "Atualiza estacionamento de veículo",
      description = "Endpoint para atualizar dados do estacionamento de um veículo",
      tags = {"ParkingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para estacionamento de veículo, ou veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  ParkingOutputDto putParking(
      @Parameter(description = "UUID válido do estacionamento do veículo")
      String uuid,
      @Parameter(description = "DTO com atributos para se atualizar o estacionamento de veículo. Requer validação de dados informados, como veículo, rua, bairro, cidade, Estado, país e tipo de estacionamento (tempo fixo ou flexível)")
      ParkingUpdateInputDto parkingUpdateInputDto);

  @Operation(summary = "Realiza o pagamento do estacionamento de veículo",
      description = "Endpoint para realizar o pagamento do estacionamento de um veículo",
      tags = {"ParkingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para estacionamento de veículo, ou veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  ParkingOutputDto putParkingPayment(
      @Parameter(description = "UUID válido do estacionamento do veículo")
      String uuid,
      @Parameter(description = "DTO com atributos para se atualizar o pagamento do estacionamento de veículo")
      ParkingPaymentInputDto parkingPaymentInputDto);

  @Operation(summary = "Realiza o encerramento do estacionamento de veículo",
      description = "Endpoint para realizar o encerramento do estacionamento de um veículo",
      tags = {"ParkingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para estacionamento de veículo, ou veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  void putParkingStateClosed(
      @Parameter(description = "UUID válido do estacionamento do veículo")
      String uuid);
}
