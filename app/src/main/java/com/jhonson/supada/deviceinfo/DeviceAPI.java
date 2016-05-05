/**
 * DeviceAPI.java
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface DeviceAPI {

    String BASE_URL = "http://private-1cc0f-devicecheckout.apiary-mock.com";

    @GET("/devices")
    Call<List<Device>> getDevices();

    class Factory {

        private static DeviceAPI service;

        public static DeviceAPI getInstance() {
            if (service  == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(DeviceAPI.class);
                return service;
            }
            else {
                return service;
            }
        }
    }



}
