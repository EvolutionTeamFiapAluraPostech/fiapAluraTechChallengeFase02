package br.com.digitalparking.user.presentation.dto;

import jakarta.validation.constraints.Email;

public record UserFilter(String name, @Email String email) {

}
