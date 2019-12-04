package com.cotrav.cotravspoc.fragments.showbus;


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
import com.cotrav.cotravspoc.adapter.busadapters.TodaysBusBookingAdapter;
import com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking;
import com.cotrav.cotravspoc.viewmodels.BusBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysBusFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TodaysBusBookingAdapter todaysBusBookingAdapter;
    private BusBookingsViewModel busBookingsViewModel;
    private List<BusBooking> todaysBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String spoc_id = "";
    String token ="";
    String authorization = "";

    String usertype = "";

    public TodaysBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        final View view = layoutInflater.inflate(R.layout.fragment_todays_bus_booking, parent, false);
        busBookingsViewModel = ViewModelProviders.of(getActivity()).get(BusBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        spoc_id = loginPref.getString("spoc_id", "n");
        busBookingsViewModel.InitBusBookingViewModel(authorization,usertype,spoc_id);
        todaysBookings =new ArrayList<>();

        Log.d("Bus Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        todaysBusBookingAdapter = new TodaysBusBookingAdapter(getActivity(), todaysBookings,"todays");
        recyclerView = (RecyclerView) view.findViewById(R.id.bus_booking_recycler_view);
        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);

        swipeRefreshLayout.setRefreshing(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(todaysBusBookingAdapter);



        busBookingsViewModel.getTodaysLiveData(authorization,usertype,spoc_id).observe(this, new Observer<List<BusBooking>>() {
            @Override
            public void onChanged(List<BusBooking> busBookingList) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (busBookingList!=null){
                    connectionErrorLayout.setVisibility(View.GONE);
                    todaysBookings.clear();
                    todaysBookings.addAll(busBookingList);
                }
                todaysBusBookingAdapter.notifyDataSetChanged();
            }
        });
        busBookingsViewModel.getTodaysConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Bus Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Bookings Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (todaysBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            busBookingsViewModel.refreshTodaysBooking(authorization,usertype,spoc_id);
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
        busBookingsViewModel.refreshTodaysBooking(authorization,usertype,spoc_id);
    }
}
