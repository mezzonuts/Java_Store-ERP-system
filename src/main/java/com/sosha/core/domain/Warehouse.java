package com.sosha.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="warehouses")
public class Warehouse {
  @Id private String id;
  @Column(name="branch_id", nullable=false) private String branchId;
  @Column(nullable=false) private String name;
  @Column(columnDefinition="BOOLEAN DEFAULT TRUE") private boolean active=true;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getBranchId(){return branchId;} public void setBranchId(String v){branchId=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
