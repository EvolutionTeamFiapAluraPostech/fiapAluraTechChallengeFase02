package br.com.digitalparking.user.infrastructure.repository;

import br.com.digitalparking.user.infrastructure.repository.query.UserQueryRepository;
import br.com.digitalparking.user.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID>, UserQueryRepository {

  Optional<User> findByEmail(String email);

  Optional<User> findByCpf(String cpf);

  Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
