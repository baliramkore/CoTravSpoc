package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.repositories.AllCitiesRepository;

import java.util.List;

/**
 * Created by Baliram on 11/09/19.
 */

public class AllCitiesViewModel extends AndroidViewModel {

    private LiveData<List<City>> citiesLiveData;
    private AllCitiesRepository allCitiesRepository;

    public AllCitiesViewModel(Application application) {
        super(application);
        allCitiesRepository=new AllCitiesRepository(this.getApplication());
    }

    public void initAllCitiesViewModel(String Authorization, String usertype){
        if (citiesLiveData==null) {
            citiesLiveData = allCitiesRepository.getAllCities(Authorization, usertype);
        }
    }

    public LiveData<List<City>> getCitiesLiveData(String Authorization, String usertype) {
        if (citiesLiveData==null) {
            citiesLiveData=allCitiesRepository.getAllCities(Authorization,usertype);
        }
        return citiesLiveData;
    }

}
