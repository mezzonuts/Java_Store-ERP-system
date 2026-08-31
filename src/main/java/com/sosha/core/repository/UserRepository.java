package com.sosha.core.repository;
import com.sosha.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,String> {
  Optional<User> findByUsername(String username);
  Optional<User> findByUsernameAndTenantId(String username, String tenantId);
}
