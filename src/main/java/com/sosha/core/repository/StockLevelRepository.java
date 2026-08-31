package com.sosha.core.repository;
import com.sosha.core.domain.StockLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StockLevelRepository extends JpaRepository<StockLevel,String> {
  Optional<StockLevel> findByProductIdAndWarehouseId(String productId, String warehouseId);
}
