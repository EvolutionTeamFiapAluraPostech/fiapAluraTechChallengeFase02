package br.com.digitalparking.user.application.usecase;

import br.com.digitalparking.shared.model.entity.validator.UuidValidator;
import br.com.digitalparking.user.application.validator.UserCpfAlreadyRegisteredInOtherUserValidator;
import br.com.digitalparking.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.digitalparking.user.model.entity.User;
import br.com.digitalparking.user.model.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserUseCase {

  private final UserService userService;
  private final UuidValidator uuidValidator;
  private final UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;
  private final UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;

  public UpdateUserUseCase(
      UserService userService,
      UuidValidator uuidValidator,
      UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator,
      UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
    this.userEmailAlreadyRegisteredInOtherUserValidator = userEmailAlreadyRegisteredInOtherUserValidator;
    this.userCpfAlreadyRegisteredInOtherUserValidator = userCpfAlreadyRegisteredInOtherUserValidator;
  }

  @Transactional
  public User execute(String userUuid, User userWithUpdatedAttributes) {
    uuidValidator.validate(userUuid);
    userEmailAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userWithUpdatedAttributes.getEmail());
    userCpfAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userWithUpdatedAttributes.getCpf());

    var userSaved = userService.findUserByIdRequired(UUID.fromString(userUuid));
    var userToUpdate = updateAttibutesToUser(userSaved, userWithUpdatedAttributes);
    return userService.save(userToUpdate);
  }

  private User updateAttibutesToUser(User userSaved, User userToSave) {
    userSaved.setName(userToSave.getName());
    userSaved.setEmail(userToSave.getEmail());
    userSaved.setCpf(userToSave.getCpf());
    return userSaved;
  }

}
