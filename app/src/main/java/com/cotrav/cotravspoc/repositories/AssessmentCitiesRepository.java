package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.allcitiesmodel.AllCityResponse;
import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCityApiResponse;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCityResponse;
import com.cotrav.cotravspoc.retrofit.AllCitiesAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Baliram on 07/7/19.
 */

public class AssessmentCitiesRepository {
    Application application;
    SharedPreferences assessCitiespreference;
    AllCitiesAPI assesmentcitiesAPI;
    MutableLiveData<List<AssCity>> assessCityLiveData;


    public AssessmentCitiesRepository(Application application){
        this.application=application;
        assessCitiespreference=application.getSharedPreferences("assess_cities", Context.MODE_PRIVATE);
        assesmentcitiesAPI= ConfigRetrofit.configRetrofit(AllCitiesAPI.class);
        assessCityLiveData=new MutableLiveData<>();
    }


     public LiveData<List<AssCity>> getAssessCities(String Authorization, String usertype,String corporateId)
     {

         assesmentcitiesAPI.getAllAssCities(Authorization,usertype,corporateId).enqueue(new Callback<AssCityApiResponse>() {
             @Override
             public void onResponse(Call<AssCityApiResponse> call, Response<AssCityApiResponse> response) {

                SharedPreferences.Editor editor=assessCitiespreference.edit();
                if (response.body().getSuccess()==1)
                {
                    assessCityLiveData.setValue(response.body().getAssCity());
                    editor.putString("taxi_types",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
             }

             @Override
             public void onFailure(Call<AssCityApiResponse> call, Throwable t) {



             }
         });

         return assessCityLiveData;
    }

}