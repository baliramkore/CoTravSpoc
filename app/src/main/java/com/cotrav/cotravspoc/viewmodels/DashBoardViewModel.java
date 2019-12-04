package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardData;
import com.cotrav.cotravspoc.repositories.DashBoardRepository;


import java.util.List;

public class DashBoardViewModel extends AndroidViewModel
{
    DashBoardRepository dashBoardRepository;
    LiveData<List<DashBoardData>> dashBoard;


    public DashBoardViewModel(@NonNull Application application) {
        super(application);
        dashBoardRepository=new DashBoardRepository(application);

    }

    public void initViewModelDashBoard(String Authorization, String usertype,String spoc_id){

        dashBoard = dashBoardRepository.getDashBoardLiveData(Authorization, usertype,spoc_id);

    }

    public LiveData<List<DashBoardData>> getDashBoardData(String Authorization, String usertype,String spoc_id) {

        if (dashBoard==null)
        {
            dashBoard = dashBoardRepository.getDashBoardLiveData(Authorization, usertype,spoc_id);

        }
        return dashBoard;
    }
}
