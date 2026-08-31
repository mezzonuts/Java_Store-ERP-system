package com.sosha.core.repository;
import com.sosha.core.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SaleRepository extends JpaRepository<Sale,String> {
  Optional<Sale> findByIdempotencyKey(String key);
}
