package br.com.digitalparking.vehicle.presentation.api;

import br.com.digitalparking.user.presentation.dto.UserOutputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "VehicleApi", description = "API de cadastro de veículos")
public interface VehicleApi {

  @Operation(summary = "Cadastro de veículos",
      description = "Endpoint para cadastrar novos veículos. Cada veículo cadastrado será vinculado ao usuário logado no aplicativo",
      tags = {"VehicleApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de descrição, placa e cor do veículo",
          content = {@Content(schema = @Schema(hidden = true))})})
  VehicleOutputDto postVehicle(
      @Parameter(description = "DTO com atributos para se cadastrar um novo veículo. Requer validação de dados informados, como descrição, placa e cor do véiculo")
      VehicleInputDto vehicleInputDto);

  @Operation(summary = "Recupera um veículo",
      description = "Endpoint para recuperar um veículo pelo ID cadastrado",
      tags = {"VehicleApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  VehicleOutputDto getVehicleById(
      @Parameter(description = "UUID válido do veículo")
      String vehicleId);

  @Operation(summary = "Atualiza veículo",
      description = "Endpoint para atualizar dados do veículo",
      tags = {"VehicleApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "409", description = "conflic para placa do veículo já cadastrado para o usuário logado no aplicativo",
          content = {@Content(schema = @Schema(hidden = true))})})
  VehicleOutputDto putVehicle(
      @Parameter(description = "UUID válido do veículo")
      String vehicleUuid,
      @Parameter(description = "DTO com atributos para se atualizar um veículo já cadastrado. Requer validação de dados informados, como descrição, placa e cor do véiculo")
      VehicleInputDto vehicleInputDto);

  @Operation(summary = "Exclui veículo",
      description = "Endpoint para excluir veículo. A exclusão é feita por soft delete",
      tags = {"VehicleApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "successful operation"),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para veículo não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  void deleteVehicle(@Parameter(description = "UUID válido do veículo") String vehicleUuid);

  @Operation(summary = "Lista de veículos do usuário autenticado",
      description = "Endpoint para recuperar uma lista de veículos do usuário autenticado no aplicativo",
      tags = {"VehicleApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleOutputDto.class))})})
  List<VehicleOutputDto> getAllVehicles();
}
