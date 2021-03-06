package com.example.watcher.api;

import static com.example.watcher.adapters.BindingAdapters.SERVER_IP;

import com.example.watcher.data.device.DeviceList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DeviceNetService {
    static DeviceNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://" + SERVER_IP + ":8080")
                .build().create(DeviceNetService.class);
    }

    @GET("/getalldevices")
    Call<DeviceList> getAllDevices();

    @POST("/updatedevice")
    @FormUrlEncoded
    Call<Boolean> updateDevice(@Field("json_device") String json_device);

    @POST("/deletedevice")
    @FormUrlEncoded
    Call<Boolean> deleteDevice(@Field("json_device") String json_device);

    @POST("/opendoor")
    @FormUrlEncoded
    Call<Boolean> openDoor(@Field("did") int did);
}
