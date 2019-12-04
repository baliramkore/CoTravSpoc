package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotravspoc.models.taxitypemodel.TaxiType;
import com.cotrav.cotravspoc.repositories.TaxiTypeRepository;

import java.util.List;

/**
 * Created by Baliram on 9/09/19.
 */

public class TaxiTypeViewModel extends AndroidViewModel {

    TaxiTypeRepository taxiTypeRepository;
    LiveData<List<TaxiType>> taxiTypes;

    public TaxiTypeViewModel(Application application) {
        super(application);
        taxiTypeRepository=new TaxiTypeRepository(application);
    }

    public void initViewModel(String Authorization, String usertype,String corporateId){
        if (taxiTypes==null) {
            taxiTypes = taxiTypeRepository.getTaxiTypes(Authorization, usertype,corporateId);
        }
    }

    public LiveData<List<TaxiType>> getTaxiTypes() {
        return taxiTypes;
    }
}
