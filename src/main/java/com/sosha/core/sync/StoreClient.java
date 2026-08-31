package com.sosha.core.sync;
import org.springframework.stereotype.Component;
@Component
public class StoreClient {
  public void publish(String table, String rowId, String payload){
    // TODO: implement Retrofit call to https://store.sosha.com/api/v1/publish
    // stub for now
    System.out.println("Publish to cloud: "+table+" "+rowId);
  }
  public void pullOrders(){
    // TODO: GET /orders?since=lastSyncTs
  }
}
