package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotravspoc.models.companytypemodel.Corporate;
import com.cotrav.cotravspoc.repositories.CompanyRepository;


import java.util.List;

public class CompanyTypeViewModel extends AndroidViewModel {
    CompanyRepository companyTypeRepository;
    LiveData<List<Corporate>> comapanyTypes;



    public CompanyTypeViewModel(@NonNull Application application) {
        super(application);
        companyTypeRepository=new CompanyRepository(application);

    }

    public void initViewModel(String Authorization, String usertype,String corporateId){
        if (comapanyTypes==null) {
            comapanyTypes = companyTypeRepository.getCompaniesType(Authorization, usertype,corporateId);
        }
    }

    public LiveData<List<Corporate>> getTrainTypes() {
        return comapanyTypes;
    }
}
