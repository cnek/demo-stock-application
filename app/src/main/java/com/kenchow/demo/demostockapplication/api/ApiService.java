package com.kenchow.demo.demostockapplication.api;

import com.kenchow.demo.demostockapplication.pojo.StockPojo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac on 6/11/2017.
 */

public interface ApiService {

    @GET("/api/v3/datasets/XNAS/{code}.json")
    Observable<StockPojo> getStockPrice(
            @Path("code") String code,
            @Query("api_key") String api_key,
            @Query("start_date") String start_date);

}
