package com.sosha.core.sync;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.Map;

public interface StoreApi {
    @POST("/api/v1/catalog/publish")
    Call<Map<String, Object>> publishCatalog(@Body Map<String, Object> payload);

    @POST("/api/v1/stock/publish")
    Call<Map<String, Object>> publishStock(@Body Map<String, Object> payload);

    @GET("/api/v1/orders")
    Call<Map<String, Object>> getOrders(@Query("since") String since);
}
