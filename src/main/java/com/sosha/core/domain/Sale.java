package com.sosha.core.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="sales", indexes=@Index(columnList="tenant_id,branch_id"))
public class Sale {
  @Id private String id;
  @Column(name="tenant_id", nullable=false) private String tenantId;
  @Column(name="branch_id", nullable=false) private String branchId;
  @Column(name="customer_id") private String customerId;
  @Column(name="total_amount", nullable=false) private BigDecimal totalAmount;
  @Column(name="tax_amount") private BigDecimal taxAmount = BigDecimal.ZERO;
  @Column(name="status", nullable=false) private String status; // PAID,PARTIAL,UNPAID
  @Column(name="idempotency_key", unique=true, nullable=false) private String idempotencyKey;
  @Column(name="sync_policy", nullable=false) private String syncPolicy="PRIVATE";
  @Column(name="created_at") private String createdAt;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getBranchId(){return branchId;} public void setBranchId(String v){branchId=v;}
  public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal v){totalAmount=v;}
  public String getIdempotencyKey(){return idempotencyKey;} public void setIdempotencyKey(String v){idempotencyKey=v;}
  public String getSyncPolicy(){return syncPolicy;} public void setSyncPolicy(String v){syncPolicy=v;}
}
