package br.com.digitalparking.user.presentation.api;

import br.com.digitalparking.user.presentation.dto.PostUserInputDto;
import br.com.digitalparking.user.presentation.dto.PutUserInputDto;
import br.com.digitalparking.user.presentation.dto.UserFilter;
import br.com.digitalparking.user.presentation.dto.UserOutputDto;
import br.com.digitalparking.user.presentation.dto.UserPaymentMethodDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "UsersApi", description = "API de cadastro de condutor do veículo/usuário do aplicativo")
public interface UsersApi {

  @Operation(summary = "Lista de usuários",
      description = "Endpoint para recuperar uma lista paginada de usuários ordenada por nome",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))})})
  Page<UserOutputDto> getAllUsersPaginated(
      @Parameter(description = "Interface com atributos para paginação") Pageable pageable);

  @Operation(summary = "Cadastro de usuários",
      description = "Endpoint para cadastrar novos usuários. O email e CPF devem ser únicos na base de dados",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de senha, email e cpf")})
  UserOutputDto postUser(
      @Parameter(description = "DTO com atributos para se cadastrar um novo usuário. Requer validação de dados informados, como nome, email, cpf e senha")
      PostUserInputDto postUserInputDto);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo email cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found")})
  UserOutputDto getUserByEmail(@Parameter(description = "email válido") String email);

  @Operation(summary = "Lista de usuários",
      description = "Endpoint para recuperar uma lista paginada de usuários, filtrada por nome, ordenada por nome",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))})})
  Page<UserOutputDto> getUsersByName(
      @Parameter(description = "Nome do condutor/usuário") String name,
      @Parameter(description = "Interface com atributos para paginação") Pageable pageable);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo ID cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado")})
  UserOutputDto getUserById(
      @Parameter(description = "UUID do condutor/usuário válido") String userId);

  @Operation(summary = "Lista de usuários",
      description = "Endpoint para recuperar uma lista paginada de usuários, filtrada por nome OU email, ordenada por nome",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado")})
  Page<UserOutputDto> getUsersByNameOrEmail(
      @Parameter(description = "DTO com os atributos nome ou email para serem utilizados como filtro de pesquisa. Pode ser informado o nome ou o email.") UserFilter userFilter,
      @Parameter(description = "Interface com atributos para paginação") Pageable pageable);

  @Operation(summary = "Atualiza usuários",
      description = "Endpoint para atualizar dados do usuário",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido"),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado"),
      @ApiResponse(responseCode = "409", description = "conflic para email/cpf já cadastrado em outro usuário")})
  UserOutputDto putUser(@Parameter(description = "UUID do condutor/usuário válido") String userUuid,
      @Parameter(description = "DTO com atributos para se cadastrar um novo usuário. Requer validação de dados informados, como nome, email, cpf e senha") PutUserInputDto putUserInputDto);

  @Operation(summary = "Exclui usuários",
      description = "Endpoint para excluir usuários. A exclusão é feita por soft delete",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation"),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido"),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado")})
  void deleteUser(@Parameter(description = "UUID do condutor/usuário válido") String userUuid);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo CPF cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado")})
  UserOutputDto getUserByCpf(@Parameter(description = "CPF do condutor/usuário válido") String cpf);

  @Operation(summary = "Recupera um usuário",
      description = "Endpoint para recuperar um usuário pelo CPF cadastrado",
      tags = {"UsersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation"),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido"),
      @ApiResponse(responseCode = "404", description = "not found para usuário não encontrado")})
  UserOutputDto patchUserPaymentMethod(@PathVariable String userUuid,
      @RequestBody UserPaymentMethodDto userPaymentMethodDto);
}
