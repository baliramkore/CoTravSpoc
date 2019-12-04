package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotravspoc.models.traintypemodel.TrainType;
import com.cotrav.cotravspoc.repositories.TrainTypeRepository;
import java.util.List;

public class TrainTypeViewModel extends AndroidViewModel {
    TrainTypeRepository taxiTypeRepository;
    LiveData<List<TrainType>> trainTypes;



    public TrainTypeViewModel(@NonNull Application application) {
        super(application);
        taxiTypeRepository=new TrainTypeRepository(application);

    }

    public void initViewModel(String Authorization, String usertype,String corporateId){
        if (trainTypes==null) {
            trainTypes = taxiTypeRepository.getTrainTypes(Authorization, usertype,corporateId);
        }
    }

    public LiveData<List<TrainType>> getTrainTypes() {
        return trainTypes;
    }
}
