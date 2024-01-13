package br.com.digitalparking.shared.model.entity;

import br.com.digitalparking.user.infrastructure.security.UserAuditInfo;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class AudittingEntityListener {

  private final UserAuditInfo userAuditInfo;

  public AudittingEntityListener(UserAuditInfo userAuditInfo) {
    this.userAuditInfo = userAuditInfo;
  }

  @PrePersist
  public void onPrePersist(Object entity) {
    if (userAuditInfo.getUser() != null) {
      ((BaseEntity) entity).setCreatedBy(userAuditInfo.getUser().getEmail());
    }
  }

  @PreUpdate
  public void onPreUpdate(Object entity) {
    if (userAuditInfo.getUser() != null) {
      ((BaseEntity) entity).setUpdatedBy(userAuditInfo.getUser().getEmail());
    }
  }
}
