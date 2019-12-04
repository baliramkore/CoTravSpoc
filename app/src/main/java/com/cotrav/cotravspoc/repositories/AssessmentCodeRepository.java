package com.cotrav.cotravspoc.repositories;

import android.app.Application;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssessmentCodeApiResponse;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssessmentResponse;
import com.cotrav.cotravspoc.retrofit.AssessmentCodeAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Baliram on 13/09/19.
 */

public class AssessmentCodeRepository {
    private Application application;
    private SharedPreferences assessmentcodePreferences;
    private AssessmentCodeAPI assessmentCodeAPI;
    private MutableLiveData<List<AssCode>> assessCodeLiveData;


    public AssessmentCodeRepository(Application application){
        this.application=application;
        assessmentcodePreferences=application.getSharedPreferences("assesmentinfo", Context.MODE_PRIVATE);
        assessCodeLiveData=new MutableLiveData<>();
        assessmentCodeAPI= ConfigRetrofit.configRetrofit(AssessmentCodeAPI.class);
    }

    public LiveData<List<AssCode>> getAssessmentCode(String Authorization, String usertype,String corporateId){

        assessmentCodeAPI.getAssessmentCodes(Authorization,usertype,corporateId).enqueue(new Callback<AssessmentCodeApiResponse>() {
            @Override
            public void onResponse(Call<AssessmentCodeApiResponse> call, Response<AssessmentCodeApiResponse> response) {

                SharedPreferences.Editor editor=assessmentcodePreferences.edit();
                if (response.body().getSuccess()==1){
                    assessCodeLiveData.setValue(response.body().getAssCodes());
                    editor.putString("taxi_types",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<AssessmentCodeApiResponse> call, Throwable t) {

            }
        });

        return assessCodeLiveData;
    }

}
