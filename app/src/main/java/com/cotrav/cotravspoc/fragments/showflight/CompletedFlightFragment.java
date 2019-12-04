package com.cotrav.cotravspoc.fragments.showflight;


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
import com.cotrav.cotravspoc.adapter.flightadapters.CompletedFlightBookingAdapter;
import com.cotrav.cotravspoc.models.show_flight_model.show_flight.FlightBooking;
import com.cotrav.cotravspoc.viewmodels.FlightBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFlightFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener
{


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CompletedFlightBookingAdapter completedFlightBookingAdapter;
    private FlightBookingsViewModel flightBookingsViewModel;
    private List<FlightBooking> completedBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String spoc_id = "";
    String token ="";
    String authorization = "";

    String usertype = "";

    public CompletedFlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        flightBookingsViewModel = ViewModelProviders.of(getActivity()).get(FlightBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        spoc_id = loginPref.getString("spoc_id", "n");
        flightBookingsViewModel.InitFlightBookingViewModel(authorization,usertype,spoc_id);
        completedBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_completed_flight_booking, parent, false);
        Log.d("Flight Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        completedFlightBookingAdapter = new CompletedFlightBookingAdapter(getActivity(), completedBookings,"past");
        recyclerView = (RecyclerView) view.findViewById(R.id.fight_booking_recycler_view);
        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);

        swipeRefreshLayout.setRefreshing(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(completedFlightBookingAdapter);



        flightBookingsViewModel.getPastLiveData(authorization,usertype,spoc_id).observe(this, new Observer<List<FlightBooking>>() {
            @Override
            public void onChanged(List<FlightBooking> flightBookingList) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (flightBookingList!=null){
                    connectionErrorLayout.setVisibility(View.GONE);
                    completedBookings.clear();
                    completedBookings.addAll(flightBookingList);
                }
                completedFlightBookingAdapter.notifyDataSetChanged();
            }
        });
        flightBookingsViewModel.getPastConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Flight Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Bookings Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (completedBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            flightBookingsViewModel.refreshTodaysBooking(authorization,usertype,spoc_id);
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
        flightBookingsViewModel.refreshTodaysBooking(authorization,usertype,spoc_id);
    }

}
