package com.sosha.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="audit_log")
public class AuditLog {
  @Id private String id;
  @Column(name="table_name", nullable=false) private String tableName;
  @Column(name="row_id") private String rowId;
  @Column(nullable=false) private String op; // INSERT,UPDATE,DELETE
  @Column(name="old_json") private String oldJson;
  @Column(name="new_json") private String newJson;
  @Column(name="user_id") private String userId;
  @Column(name="created_at") private String createdAt;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTableName(){return tableName;} public void setTableName(String v){tableName=v;}
  public String getRowId(){return rowId;} public void setRowId(String v){rowId=v;}
  public String getOp(){return op;} public void setOp(String v){op=v;}
  public String getOldJson(){return oldJson;} public void setOldJson(String v){oldJson=v;}
  public String getNewJson(){return newJson;} public void setNewJson(String v){newJson=v;}
}
