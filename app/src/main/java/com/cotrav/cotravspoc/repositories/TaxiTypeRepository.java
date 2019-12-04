package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.taxitypemodel.TaxiType;
import com.cotrav.cotravspoc.models.taxitypemodel.TaxiTypeApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.TaxiTypeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by Baliram on 13/09/2019.
 */

public class TaxiTypeRepository {

    MutableLiveData<List<TaxiType>> taxiTypes;
    SharedPreferences taxiTypePref;
    TaxiTypeAPI taxiTypeAPI;

    public TaxiTypeRepository(Application application) {
        taxiTypes=new MutableLiveData<>();
        taxiTypeAPI= ConfigRetrofit.configRetrofit(TaxiTypeAPI.class);
        taxiTypePref=(SharedPreferences)application.getSharedPreferences("taxitypeinfo", Context.MODE_PRIVATE);
        if (!taxiTypePref.getString("taxi_types","n").equals("n")){
            taxiTypes.setValue(GsonStringConvertor.stringToGson(taxiTypePref.getString("taxi_types","n"), TaxiTypeApiResponse.class).getTaxiTypes());
        }
    }

    public MutableLiveData<List<TaxiType>> getTaxiTypes(String Authorization, String usertype,String corporateId)
    {

        taxiTypeAPI.getTaxiTypes(Authorization,usertype,corporateId).enqueue(new Callback<TaxiTypeApiResponse>() {
            @Override
            public void onResponse(Call<TaxiTypeApiResponse> call, Response<TaxiTypeApiResponse> response) {
                SharedPreferences.Editor editor=taxiTypePref.edit();
                    if (response.body().getSuccess()==1){

                        taxiTypes.setValue(response.body().getTaxiTypes());
                        editor.putString("taxi_types",GsonStringConvertor.gsonToString(response));
                        editor.commit();
                    }
            }

            @Override
            public void onFailure(Call<TaxiTypeApiResponse> call, Throwable t) {

            }
        });
        return taxiTypes;
    }
}
