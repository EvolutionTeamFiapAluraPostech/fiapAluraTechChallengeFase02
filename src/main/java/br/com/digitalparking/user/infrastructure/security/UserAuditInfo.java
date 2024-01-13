package br.com.digitalparking.user.infrastructure.security;

import br.com.digitalparking.user.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuditInfo {

  public User getUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() != null) {
      if (authentication.getPrincipal() instanceof User) {
        return (User) authentication.getPrincipal();
      }
    }
    return null;
  }
}
