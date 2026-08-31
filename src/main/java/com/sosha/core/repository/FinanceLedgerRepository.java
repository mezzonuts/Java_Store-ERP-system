package com.sosha.core.repository;
import com.sosha.core.domain.FinanceLedger;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FinanceLedgerRepository extends JpaRepository<FinanceLedger,String> {}
