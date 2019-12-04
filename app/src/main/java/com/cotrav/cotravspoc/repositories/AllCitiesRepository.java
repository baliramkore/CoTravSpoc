package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.allcitiesmodel.AllCityResponse;
import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.retrofit.AllCitiesAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Baliram on 17/7/17.
 */

public class AllCitiesRepository {
    SharedPreferences allCitiesSharedPreferences;
    MutableLiveData<List<City>> citiesLiveData;
    AllCitiesAPI allCitiesAPI;

    Application application;

    public AllCitiesRepository(Application application){
        this.application=application;
        allCitiesSharedPreferences=application.getSharedPreferences("cities_info", Context.MODE_PRIVATE);
        citiesLiveData=new MutableLiveData<>();
        allCitiesAPI= ConfigRetrofit.configRetrofit(AllCitiesAPI.class);


        if (allCitiesSharedPreferences.getString("cities","n")!="n"){
            System.out.println("Cities: "+allCitiesSharedPreferences.getString("cities","n"));
            //AllCityResponse citiesResponse= GsonStringConvertor.stringToGson(allCitiesSharedPreferences.getString("cities","n"),AllCityResponse.class);
            //citiesLiveData.setValue(citiesResponse.getCities());
            Log.d("dCities",allCitiesSharedPreferences.getString("cities","n"));
        }
        else {
            System.out.println("Cities: "+allCitiesSharedPreferences.getString("cities","n"));
            Log.d("dCities",allCitiesSharedPreferences.getString("cities","n"));
        }


    }

    public LiveData<List<City>> getAllCities(String Authorization, String usertype){
        if (allCitiesSharedPreferences.getString("cities","n")!="n"){
            System.out.println("Cities: "+allCitiesSharedPreferences.getString("cities","n"));


            //error coming due to string converter from json to string
            AllCityResponse citiesResponse= GsonStringConvertor.stringToGson(allCitiesSharedPreferences.getString("cities","n"),
                    AllCityResponse.class);
            citiesLiveData.setValue(citiesResponse.getCities());
        }
        else {
            System.out.println("Cities: "+allCitiesSharedPreferences.getString("cities","n"));
            Log.d("dCities",allCitiesSharedPreferences.getString("cities","n"));
        }
        refreshCitiesData(Authorization,usertype);
        return citiesLiveData;
    }

    void refreshCitiesData(String Authorization, String usertype){
        allCitiesAPI.getAllCities(Authorization,usertype).enqueue(new Callback<AllCityResponse>() {
            @Override
            public void onResponse(Call<AllCityResponse> call, Response<AllCityResponse> response) {
                SharedPreferences.Editor editor=allCitiesSharedPreferences.edit();

                    if (response.body().getSuccess()==1){
                        editor.putString("cities",GsonStringConvertor.gsonToString(response.body()));
                        citiesLiveData.setValue(response.body().getCities());
                        editor.commit();
                    }

            }

            @Override
            public void onFailure(Call<AllCityResponse> call, Throwable t) {

            }
        });
    }
}
