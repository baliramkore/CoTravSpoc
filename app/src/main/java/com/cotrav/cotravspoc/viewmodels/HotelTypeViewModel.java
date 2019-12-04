package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.hoteltypesmodel.HotelType;
import com.cotrav.cotravspoc.models.roomtypesmodel.RoomType;
import com.cotrav.cotravspoc.repositories.AddHotelRepository;

import java.util.List;

/**
 * Created by Baliram on 13/09/19.
 */

public class HotelTypeViewModel extends AndroidViewModel {
    AddHotelRepository hotelTypeRepository;
    MutableLiveData<List<HotelType>> hotelTypes;
    LiveData<List<RoomType>> roomTypes;



    public HotelTypeViewModel(@NonNull Application application) {
        super(application);
        hotelTypeRepository=new AddHotelRepository(application);

    }

    public void initHotelTypeViewModel(String Authorization, String usertype){
        if (hotelTypes==null) {
            hotelTypes = hotelTypeRepository.getHotelType(Authorization, usertype);
        }

    }
    public void initRoomTypeViewModel(String Authorization, String usertype){
        if (roomTypes==null) {
            roomTypes = hotelTypeRepository.getRoomType(Authorization, usertype);
        }

    }
    public MutableLiveData<List<HotelType>> getHotelTypes(String authorization, String userType) {
        return hotelTypes;
    }


    public LiveData<List<RoomType>> getRoomTypes(String authorization, String userType) {
        return roomTypes;
    }

}

