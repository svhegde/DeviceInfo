/**
 * DeviceAPI.java is a interface call which does CRUD Operations.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeviceAPI {

    String BASE_URL = "https://private-1cc0f-devicecheckout.apiary-mock.com";

    @GET("/devices")
    Call<List<Device>> getDevices();

    @POST("/devices")
    Call<Device> postDevice(
            @Body Device device
    );

    // Currently POST api for update is not returning anything in response body
    // Hence return type for Call has to be Void
    @POST("/devices/{id}")
    Call<Void> updateDevice(
            @Path("id") int deviceId,
            @Body Device device
    );

    @DELETE("/devices/{id}")
    Call<Void> deleteDevice(
            @Path("id") int deviceId
    );

    class Factory {

        private static DeviceAPI service;

        public static DeviceAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(DeviceAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }


}
