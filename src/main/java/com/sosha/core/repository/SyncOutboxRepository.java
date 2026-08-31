package com.sosha.core.repository;
import com.sosha.core.domain.SyncOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SyncOutboxRepository extends JpaRepository<SyncOutbox,String> {
  List<SyncOutbox> findBySyncedAtIsNullOrderByCreatedAtAsc();
}
