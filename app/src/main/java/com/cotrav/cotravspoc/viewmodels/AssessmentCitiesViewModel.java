package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.repositories.AssessmentCitiesRepository;

import java.util.List;

/**
 * Created by Baliram on 07/7/19.
 */

public class AssessmentCitiesViewModel extends AndroidViewModel {
    private LiveData<List<AssCity>> liveAssessCityList;
    AssessmentCitiesRepository assessmentCitiesRepository;

    public AssessmentCitiesViewModel(Application application) {
        super(application);
        assessmentCitiesRepository=new AssessmentCitiesRepository(application);
    }

    public void initViewModel(String Authorization, String usertype,String corporateId)
    {
        //liveAssessmentCityList=assessmentCitiesRepository.getAssessmentCities(access_token,admin_id);
        liveAssessCityList=assessmentCitiesRepository.getAssessCities(Authorization,usertype,corporateId);

    }

    public LiveData<List<AssCity>> getLiveAssessmentCityList(String Authorization, String usertype,String corporateId) {
        if (liveAssessCityList==null)
        {
            //liveAssessmentCityList=assessmentCitiesRepository.getAssessmentCities(access_token,admin_id);
            liveAssessCityList=assessmentCitiesRepository.getAssessCities(Authorization,usertype,corporateId);

        }
        return liveAssessCityList;
    }
}
