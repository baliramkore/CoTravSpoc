package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.models.companytypemodel.CompanyApiResponse;
import com.cotrav.cotravspoc.models.companytypemodel.Corporate;
import com.cotrav.cotravspoc.retrofit.CompanyTypeAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository
{

    MutableLiveData<List<Corporate>> companyTypes;
    SharedPreferences companyTypePref;
    CompanyTypeAPI companyTypeAPI;

    public CompanyRepository(Application application) {
        companyTypes=new MutableLiveData<>();
        companyTypeAPI= ConfigRetrofit.configRetrofit(CompanyTypeAPI.class);
        companyTypePref=(SharedPreferences)application.getSharedPreferences("companytypeinfo", Context.MODE_PRIVATE);
        if (!companyTypePref.getString("company_types","n").equals("n")){
            companyTypes.setValue(GsonStringConvertor.stringToGson(companyTypePref.getString("company_types","n"), CompanyApiResponse.class).getCorporates());
        }
    }

    public MutableLiveData<List<Corporate>> getCompaniesType(String Authorization, String usertype,String corporateId)
    {

        companyTypeAPI.getCompanyTypes(Authorization,usertype,corporateId).enqueue(new Callback<CompanyApiResponse>() {
            @Override
            public void onResponse(Call<CompanyApiResponse> call, Response<CompanyApiResponse> response) {

                SharedPreferences.Editor editor=companyTypePref.edit();
                if (response.body().getSuccess()==1){

                    companyTypes.setValue(response.body().getCorporates());
                    editor.putString("company_types",GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<CompanyApiResponse> call, Throwable t) {

            }
        });
        return companyTypes;
    }
}
