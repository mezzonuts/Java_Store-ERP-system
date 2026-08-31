package com.sosha.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="sync_outbox")
public class SyncOutbox {
  @Id private String id;
  @Column(name="table_name", nullable=false) private String tableName;
  @Column(name="row_id", nullable=false) private String rowId;
  @Column(nullable=false) private String op;
  @Column(name="payload_json", columnDefinition="TEXT") private String payloadJson;
  @Column(name="idempotency_key", nullable=false, unique=true) private String idempotencyKey;
  @Column(name="created_at", nullable=false) private String createdAt;
  @Column(name="synced_at") private String syncedAt;
  @Column(name="retry_count") private int retryCount = 0;

  // getters & setters
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTableName(){return tableName;} public void setTableName(String v){tableName=v;}
  public String getRowId(){return rowId;} public void setRowId(String v){rowId=v;}
  public String getOp(){return op;} public void setOp(String v){op=v;}
  public String getPayloadJson(){return payloadJson;} public void setPayloadJson(String v){payloadJson=v;}
  public String getIdempotencyKey(){return idempotencyKey;} public void setIdempotencyKey(String v){idempotencyKey=v;}
  public String getCreatedAt(){return createdAt;} public void setCreatedAt(String v){createdAt=v;}
  public String getSyncedAt(){return syncedAt;} public void setSyncedAt(String v){syncedAt=v;}
  public int getRetryCount(){return retryCount;} public void setRetryCount(int v){retryCount=v;}
}
