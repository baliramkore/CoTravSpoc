package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotravspoc.models.trainstationmodel.TrainStation;
import com.cotrav.cotravspoc.repositories.TrainStationRepository;
import java.util.List;

public class TrainStationViewModel extends AndroidViewModel {
    TrainStationRepository trainStationRepository;
    LiveData<List<TrainStation>> trainStations;



    public TrainStationViewModel(@NonNull Application application) {
        super(application);
        trainStationRepository=new TrainStationRepository(application);

    }

    public void initViewModel(String Authorization, String usertype){
        if (trainStations==null) {
            trainStations = trainStationRepository.getTrainStations(Authorization, usertype);
        }
    }

    public LiveData<List<TrainStation>> getTrainStations() {
        return trainStations;
    }
}
