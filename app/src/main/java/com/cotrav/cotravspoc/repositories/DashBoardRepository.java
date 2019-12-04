package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardApiResponse;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardData;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.GetDashBoardDataAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardRepository {

    MutableLiveData<List<DashBoardData>> trainStations;
    SharedPreferences dashBoardPref;
    GetDashBoardDataAPI dashBoardAPI;

    public DashBoardRepository(Application application) {
        trainStations=new MutableLiveData<>();
        dashBoardAPI= ConfigRetrofit.configRetrofit(GetDashBoardDataAPI.class);
        dashBoardPref=(SharedPreferences)application.getSharedPreferences("dashboardinfo", Context.MODE_PRIVATE);
        if (!dashBoardPref.getString("dash_board","n").equals("n")){
            trainStations.setValue(GsonStringConvertor.stringToGson(dashBoardPref.getString("dash_board","n"), DashBoardApiResponse.class).getDashboard());
        }
    }

    public MutableLiveData<List<DashBoardData>> getDashBoardLiveData(String Authorization, String usertype,String spoc_id)
    {

        dashBoardAPI.getDashBoardData(Authorization,usertype,spoc_id).enqueue(new Callback<DashBoardApiResponse>() {
            @Override
            public void onResponse(Call<DashBoardApiResponse> call, Response<DashBoardApiResponse> response) {
                SharedPreferences.Editor editor=dashBoardPref.edit();
                if (response.body().getSuccess().equals("1")){

                    trainStations.setValue(response.body().getDashboard());
                    editor.putString("dash_board",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }

            }

            @Override
            public void onFailure(Call<DashBoardApiResponse> call, Throwable t) {

            }
        });
        return trainStations;
    }
}
