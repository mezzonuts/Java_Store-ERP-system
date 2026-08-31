package com.sosha.core.sync;
import com.sosha.core.domain.SyncOutbox;
import com.sosha.core.repository.SyncOutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
@Service
public class SyncScheduler {
  @Autowired private SyncOutboxRepository repo;
  @Autowired private StoreClient storeClient;
  @Scheduled(fixedDelay=5000) // 5s
  public void flushOutbox(){
    List<SyncOutbox> pending = repo.findBySyncedAtIsNullOrderByCreatedAtAsc();
    if(pending.isEmpty()) return;
    for(SyncOutbox o: pending){
      try{
        storeClient.publish(o.getTableName(), o.getRowId(), o.getPayloadJson());
        o.setSyncedAt(Instant.now().toString());
        repo.save(o);
      }catch(Exception e){
        o.setRetryCount(o.getRetryCount()+1);
        if(o.getRetryCount()<3) repo.save(o);
      }
    }
  }
}
