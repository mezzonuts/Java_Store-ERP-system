package com.sosha.core.repository;
import com.sosha.core.domain.StockLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface StockLedgerRepository extends JpaRepository<StockLedger,String> {
  List<StockLedger> findByProductIdOrderByCreatedAtDesc(String productId);
}
