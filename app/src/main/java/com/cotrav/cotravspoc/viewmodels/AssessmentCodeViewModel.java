package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.repositories.AssessmentCodeRepository;
import java.util.List;

/**
 * Created by Baliram on 13/09/19.
 */

public class AssessmentCodeViewModel extends AndroidViewModel {

    private LiveData<List<AssCode>> liveAssessCodeList;
    AssessmentCodeRepository assessmentCodeRepository;

    public AssessmentCodeViewModel(Application application) {
        super(application);
        assessmentCodeRepository=new AssessmentCodeRepository(application);
    }

    public void initViewModel(String Authorization, String usertype,String corporateId)
    {
        //liveAssessmentCityList=assessmentCitiesRepository.getAssessmentCities(access_token,admin_id);
        liveAssessCodeList=assessmentCodeRepository.getAssessmentCode(Authorization,usertype,corporateId);

    }

    public LiveData<List<AssCode>> getLiveAssessmentCodeList(String Authorization, String usertype,String corporateId) {
        if (liveAssessCodeList==null)
        {
            //liveAssessmentCityList=assessmentCitiesRepository.getAssessmentCities(access_token,admin_id);
            liveAssessCodeList=assessmentCodeRepository.getAssessmentCode(Authorization,usertype,corporateId);

        }
        return liveAssessCodeList;
    }
}

