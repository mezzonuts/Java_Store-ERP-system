package com.sosha.core.sync;
import com.sosha.core.domain.SyncOutbox;
import com.sosha.core.repository.SyncOutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
@Component
public class OutboxEnqueuer {
  @Autowired private SyncOutboxRepository repo;
  public void enqueue(String table, String rowId, String payload){
    SyncOutbox o = new SyncOutbox();
    o.setId(java.util.UUID.randomUUID().toString());
    o.setTableName(table);
    o.setRowId(rowId);
    o.setOp("UPSERT");
    o.setPayloadJson(payload);
    o.setIdempotencyKey(java.util.UUID.randomUUID().toString());
    o.setCreatedAt(Instant.now().toString());
    repo.save(o);
  }
}
