package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record PutUserInputDto(
    @NotBlank(message = "Name is required.")
    @Length(max = 500, message = "Max name length is 500 characters.")
    String name,
    @NotBlank(message = "email is required.")
    @Length(max = 500, message = "Max email length is 500 characters.")
    @Email
    String email,
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
