package com.cotrav.cotravspoc.fragments.showtrain;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.adapter.trainadapters.CancelledTrainBookingAdapter;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBooking;
import com.cotrav.cotravspoc.viewmodels.TrainBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledTrainFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CancelledTrainBookingAdapter cancelledTrainBookingAdapter;
    private TrainBookingsViewModel trainBookingsViewModel;
    private List<TrainBooking> cancelledBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String spoc_id = "";
    String token ="";
    String authorization = "";

    String usertype = "";

    public CancelledTrainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        trainBookingsViewModel = ViewModelProviders.of(getActivity()).get(TrainBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        spoc_id = loginPref.getString("spoc_id", "n");
        trainBookingsViewModel.InitTrainBookingViewModel(authorization,usertype,spoc_id);
        cancelledBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_cancelled_train, parent, false);
        Log.d("Train Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        cancelledTrainBookingAdapter = new CancelledTrainBookingAdapter(getActivity(), cancelledBookings,"cancelled");
        recyclerView = (RecyclerView) view.findViewById(R.id.train_booking_recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cancelledTrainBookingAdapter);


        trainBookingsViewModel.getCancelledLiveData(authorization,usertype,spoc_id).observe(this, new Observer<List<TrainBooking>>() {
            @Override
            public void onChanged(List<TrainBooking> trainBookingList) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (trainBookingList!=null){
                    connectionErrorLayout.setVisibility(View.GONE);
                    cancelledBookings.clear();
                    cancelledBookings.addAll(trainBookingList);
                }
                cancelledTrainBookingAdapter.notifyDataSetChanged();
            }
        });
        trainBookingsViewModel.getCancelledConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Train Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Bookings Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (cancelledBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            trainBookingsViewModel.refreshCancelledBooking(authorization,usertype,spoc_id);
                        }
                    }).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        trainBookingsViewModel.refreshCancelledBooking(authorization,usertype,spoc_id);
    }
}
