package com.sosha.core.service;
import com.sosha.core.domain.FinanceLedger;
import com.sosha.core.repository.FinanceLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; import java.util.UUID;
@Service
public class FinanceService {
  @Autowired private FinanceLedgerRepository repo;
  @Transactional public FinanceLedger record(String type, BigDecimal amount, String desc){
    FinanceLedger f = new FinanceLedger();
    f.setId(UUID.randomUUID().toString());
    f.setType(type);
    f.setAmount(amount);
    f.setDescription(desc);
    return repo.save(f);
  }
}
