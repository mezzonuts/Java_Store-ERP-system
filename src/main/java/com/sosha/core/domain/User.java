package com.sosha.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id","username"}))
public class User {
  @Id private String id;
  @Column(name="tenant_id", nullable=false) private String tenantId;
  @Column(name="branch_id", nullable=false) private String branchId;
  @Column(nullable=false) private String username;
  @Column(name="password_hash", nullable=false) private String passwordHash;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
  @Column(nullable=false) private boolean active=true;
  @Column(name="sync_policy", nullable=false) private String syncPolicy="PRIVATE";
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getBranchId(){return branchId;} public void setBranchId(String v){branchId=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
  public Role getRole(){return role;} public void setRole(Role v){role=v;}
  public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
  public String getSyncPolicy(){return syncPolicy;} public void setSyncPolicy(String v){syncPolicy=v;}
}
