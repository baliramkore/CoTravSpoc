package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.hoteltypesmodel.HotelType;
import com.cotrav.cotravspoc.models.hoteltypesmodel.HotelTypeApiResponse;
import com.cotrav.cotravspoc.models.roomtypesmodel.RoomType;
import com.cotrav.cotravspoc.models.roomtypesmodel.RoomTypeApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.GetHotelDataAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHotelRepository {
    private Application application;
    private SharedPreferences hotelTypePreferences;
    private GetHotelDataAPI getHotelBookingAPI;
    private MutableLiveData<List<HotelType>> hotelTypeLiveData;
    private MutableLiveData<List<RoomType>> roomTypeLiveData;

    public AddHotelRepository(Application application){
        this.application=application;
        hotelTypePreferences=application.getSharedPreferences("hotelTypeInfo", Context.MODE_PRIVATE);
        hotelTypeLiveData=new MutableLiveData<List<HotelType>>();
        roomTypeLiveData=new MutableLiveData<>();
        getHotelBookingAPI= ConfigRetrofit.configRetrofit(GetHotelDataAPI.class);
    }

    public MutableLiveData<List<HotelType>> getHotelType(String Authorization, String usertype){

        getHotelBookingAPI.getHotelType(Authorization,usertype).enqueue(new Callback<HotelTypeApiResponse>() {
            @Override
            public void onResponse(Call<HotelTypeApiResponse> call, Response<HotelTypeApiResponse> response) {

                SharedPreferences.Editor editor=hotelTypePreferences.edit();
                if (response.body().getSuccess().equals("1")){
                    hotelTypeLiveData.setValue(response.body().getTypes());
                    editor.putString("hotel_type", GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<HotelTypeApiResponse> call, Throwable t) {

            }
        });

        return hotelTypeLiveData;
    }

    public MutableLiveData<List<RoomType>> getRoomType(String Authorization, String usertype){

        getHotelBookingAPI.getRoomType(Authorization,usertype).enqueue(new Callback<RoomTypeApiResponse>() {
            @Override
            public void onResponse(Call<RoomTypeApiResponse> call, Response<RoomTypeApiResponse> response) {

                if (response.body().getSuccess().equals("1")){
                    roomTypeLiveData.setValue(response.body().getTypes());
                }
            }

            @Override
            public void onFailure(Call<RoomTypeApiResponse> call, Throwable t) {

            }
        });

        return roomTypeLiveData;
    }

}
