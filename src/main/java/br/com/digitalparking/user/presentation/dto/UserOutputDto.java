package br.com.digitalparking.user.presentation.dto;

import br.com.digitalparking.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@Tag(name = "UserOutputDto", description = "DTO de saída para representação de um usuário")
public record UserOutputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "Valid UUID")
    String id,
    @Schema(example = "Thomas Anderson", description = "Nome do condutor do veículo/usuário do aplicativo")
    String name,
    @Schema(example = "thomas.anderson@matrix.com", description = "email do condutor do veículo/usuário do aplicativo")
    String email,
    @Schema(example = "92477979000", description = "CPF do condutor do veículo/usuário do aplicativo")
    String cpf,
    @Schema(example = "PIX", description = "Método de pagamento preferido do condutor do veículo/usuário do aplicativo")
    String userPaymentMethod
) {

  public UserOutputDto(User user) {
    this(user.getId() != null ? user.getId().toString() : null,
        user.getName(),
        user.getEmail(),
        user.getCpf(),
        user.getUserPaymentMethod() != null ? user.getUserPaymentMethod().getPaymentMethod()
            .getDescription() : null);
  }

  public static Page<UserOutputDto> toPage(Page<User> usersPage) {
    return usersPage.map(UserOutputDto::new);
  }

  public static UserOutputDto from(User user) {
    return new UserOutputDto(user);
  }
}
