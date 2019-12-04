package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.trainstationmodel.TrainStation;
import com.cotrav.cotravspoc.models.trainstationmodel.TrainStationApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.TrainTypeAPI;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainStationRepository

{

    MutableLiveData<List<TrainStation>> trainStations;
    SharedPreferences trainStationPref;
    TrainTypeAPI trainTypeAPI;

    public TrainStationRepository(Application application) {
        trainStations=new MutableLiveData<>();
        trainTypeAPI= ConfigRetrofit.configRetrofit(TrainTypeAPI.class);
        trainStationPref=(SharedPreferences)application.getSharedPreferences("trainstationinfo", Context.MODE_PRIVATE);
        if (!trainStationPref.getString("train_stations","n").equals("n")){
            trainStations.setValue(GsonStringConvertor.stringToGson(trainStationPref.getString("train_stations","n"), TrainStationApiResponse.class).getStations());
        }
    }

    public MutableLiveData<List<TrainStation>> getTrainStations(String Authorization, String usertype)
    {

        trainTypeAPI.getTrainStations(Authorization,usertype).enqueue(new Callback<TrainStationApiResponse>() {
            @Override
            public void onResponse(Call<TrainStationApiResponse> call, Response<TrainStationApiResponse> response) {
                SharedPreferences.Editor editor=trainStationPref.edit();
                if (response.body().getSuccess().equals("1"))
                {

                    trainStations.setValue(response.body().getStations());
                    editor.putString("train_stations",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }

            }

            @Override
            public void onFailure(Call<TrainStationApiResponse> call, Throwable t) {

            }
        });
        return trainStations;
    }
}
