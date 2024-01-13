package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record PostUserInputDto(
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
    String cpf,
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
