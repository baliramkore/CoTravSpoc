package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.traintypemodel.TrainType;
import com.cotrav.cotravspoc.models.traintypemodel.TrainTypeApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.TaxiTypeAPI;
import com.cotrav.cotravspoc.retrofit.TrainTypeAPI;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainTypeRepository
{

    MutableLiveData<List<TrainType>> trainTypes;
    SharedPreferences trainTypePref;
    TrainTypeAPI trainTypeAPI;

    public TrainTypeRepository(Application application) {
        trainTypes=new MutableLiveData<>();
        trainTypeAPI= ConfigRetrofit.configRetrofit(TrainTypeAPI.class);
        trainTypePref=(SharedPreferences)application.getSharedPreferences("trainitypeinfo", Context.MODE_PRIVATE);
        if (!trainTypePref.getString("train_types","n").equals("n")){
            trainTypes.setValue(GsonStringConvertor.stringToGson(trainTypePref.getString("train_types","n"), TrainTypeApiResponse.class).getTypes());
        }
    }

    public MutableLiveData<List<TrainType>> getTrainTypes(String Authorization, String usertype,String corporateId)
    {

        trainTypeAPI.getTrainTypes(Authorization,usertype,corporateId).enqueue(new Callback<TrainTypeApiResponse>() {
            @Override
            public void onResponse(Call<TrainTypeApiResponse> call, Response<TrainTypeApiResponse> response) {

                SharedPreferences.Editor editor=trainTypePref.edit();
                if (response.body().getSuccess()==1){

                    trainTypes.setValue(response.body().getTypes());
                    editor.putString("train_types",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<TrainTypeApiResponse> call, Throwable t) {

            }
        });
        return trainTypes;
    }
}
