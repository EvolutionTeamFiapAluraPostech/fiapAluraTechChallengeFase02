package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Tag(name = "PutUserInputDto", description = "DTO de entrada represetação de um usuário, para atualização de dados")
public record PutUserInputDto(
    @Schema(example = "Thomas Anderson", description = "Nome do condutor do veículo/usuário do aplicativo")
    @NotBlank(message = "Name is required.")
    @Length(max = 500, message = "Max name length is 500 characters.")
    String name,
    @Schema(example = "thomas.anderson@matrix.com", description = "email do condutor do veículo/usuário do aplicativo")
    @NotBlank(message = "email is required.")
    @Length(max = 500, message = "Max email length is 500 characters.")
    @Email
    String email,
    @Schema(example = "92477979000", description = "CPF do condutor do veículo/usuário do aplicativo")
    @NotBlank(message = "cpf is required.")
    @Length(max = 14, message = "Max cpf length is 14 characters.")
    @CPF
    String cpf
) {

  public static User toUser(PutUserInputDto putUserInputDto) {
    return User.builder()
        .name(putUserInputDto.name)
        .email(putUserInputDto.email)
        .cpf(putUserInputDto.cpf)
        .build();
  }
}
