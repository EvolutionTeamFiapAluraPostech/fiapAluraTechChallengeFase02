package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Tag(name = "PostUserInputDto", description = "DTO de entrada represetação de um usuário")
public record PostUserInputDto(
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
    String cpf,
    @Schema(example = "@Admin123", description = "Senha do condutor do veículo/usuário do aplicativo")
    @NotBlank(message = "Password is required.")
    @Length(min = 8, max = 20, message = "Min password length is 8 characters and max password length is 20 characters.")
    String password
) {

  public static User toUser(PostUserInputDto postUserInputDto) {
    return User.builder()
        .name(postUserInputDto.name)
        .email(postUserInputDto.email)
        .cpf(postUserInputDto.cpf)
        .password(postUserInputDto.password)
        .build();
  }
}
