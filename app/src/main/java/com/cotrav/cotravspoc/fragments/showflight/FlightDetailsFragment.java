package com.cotrav.cotravspoc.fragments.showflight;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.ViewBusPassanger;
import com.cotrav.cotravspoc.models.show_flight_model.view_flight.FlightBookingDetails;
import com.cotrav.cotravspoc.models.show_flight_model.view_flight.FlightPassanger;
import com.cotrav.cotravspoc.viewmodels.FlightBookingsViewModel;
import com.cotrav.cotravspoc.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class FlightDetailsFragment extends Fragment {
    TextView bookingId,bookingStatus,
            assCode,assCity,bookingReason,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,
            dropTime,dropDate,dropCity,dropEndPont,bookingDate,

            ticketNumber,numberSeats,boardingPoint,detailFlightType,pnrNumber,empList,noOfEmployees;

    LinearLayout basicDetailLay,passengerDetailLay,flightDetailsLay;

    ProgressBar progressBar;
    Bundle arg;
    FlightBookingsViewModel flightBookingsViewModel;
    UserViewModel userViewModel;
    FlightBookingDetails flightBookingDetails;
    FlightPassanger flightPassanger;

    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    private String strspocId;

    public FlightDetailsFragment() {

        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_flight_details, container, false);
        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        flightDetailsLay = view .findViewById(R.id.flight_details_lay);
        flightDetailsLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        //tourType = view.findViewById(R.id.tour_type);
        bookingStatus= view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingDate=view.findViewById(R.id.booking_date);
        bookingReason = view.findViewById(R.id.booking_reason);
        pickupCity = view.findViewById(R.id.pickup_city);
        pickupBoardingPoint = view.findViewById(R.id.pickup_boardingpoint);
        pickupDate = view.findViewById(R.id.pickup_date);
        pickupTime = view.findViewById(R.id.pickup_time);
        dropCity = view.findViewById(R.id.drop_city);
        dropDate = view.findViewById(R.id.drop_date);
        dropEndPont = view.findViewById(R.id.drop_city_endpoint);
        dropTime = view.findViewById(R.id.drop_time);
        passengerDetailLay=view.findViewById(R.id.passenger_detail_lay);
        ticketNumber=view.findViewById(R.id.ticket_number);
        numberSeats=view.findViewById(R.id.number_seats);
        boardingPoint=view.findViewById(R.id.boarding_point);
        detailFlightType=view.findViewById(R.id.detail_flight_type);
        pnrNumber=view.findViewById(R.id.pnr_number);
        empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        progressBar = view.findViewById(R.id.progressBar_bookingDetails);


        flightBookingsViewModel= ViewModelProviders.of(this).get(FlightBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        strspocId = loginPref.getString("spoc_id","n");
        authorization = "Token "+token;
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);

        flightBookingsViewModel.getFlightBookingDetails(authorization,"4",
                getArguments().getString("bookingId")).observe(this, new Observer<FlightBookingDetails>() {
            @Override
            public void onChanged(FlightBookingDetails flightBookingDetail) {
                progressBar.setVisibility(View.GONE);
                if (flightBookingDetail!=null){
                    Log.d("FlightBooking", GsonStringConvertor.gsonToString(flightBookingDetail));
                    flightBookingDetails =flightBookingDetail;
                    basicDetailLay.setVisibility(View.VISIBLE);
                if (flightBookingDetails.getPassangers().size()>0) {
                    passengerDetailLay.setVisibility(View.VISIBLE);
                    List<FlightPassanger> passangers = flightBookingDetails.getPassangers();
                    String size = String.valueOf(passangers.size());
                    noOfEmployees.setText(size);
                    for (int i = 0; i < passangers.size(); i++) {
                        int j = i + 1;
                        String no = String.valueOf(j);
                        if (passangers.get(i).getEmployeeEmail() != null &&
                                passangers.get(i).getEmployeeName() != null &&
                                passangers.get(i).getEmployeeContact() != null) {
                            if (i == 0)
                                empList.append(Html.fromHtml("Passenger" + " " + no + "<br>" +
                                        "<b>" + "Employee Name : &emsp;&emsp;&emsp;" + "</b>" +
                                        passangers.get(i).getEmployeeName() + "<br>" +
                                        "<b>" + "Employee Email :&ensp;&emsp;&emsp;&emsp;" + "</b>" +
                                        passangers.get(i).getEmployeeEmail() + "<br>" +
                                        "<b>" + "Employee Contact No.:&ensp;&ensp;" + "</b>" +
                                        passangers.get(i).getEmployeeContact() + ""));
                            else
                                empList.append(Html.fromHtml("<br><br>" + "Passenger" + " " + no + "<br>" +
                                        "<b>" + "Employee Name : &emsp;&emsp;&emsp;" + "</b>" +
                                        passangers.get(i).getEmployeeName() + "<br>" +
                                        "<b>" + "Employee Email :&ensp;&emsp;&emsp;&emsp;" + "</b>" +
                                        passangers.get(i).getEmployeeEmail() + "<br>" +
                                        "<b>" + "Employee Contact No.:&ensp;&ensp;" + "</b>" +
                                        passangers.get(i).getEmployeeContact() + ""));
                        } else
                            empList.append(Html.fromHtml("<b>" + "Passemger" + "</b>" + " " + no + "<br>" +
                                    "Passenger Data Not Found"));
                    }
                }
                    setData();
                }
            }
        });


        flightBookingsViewModel.getFlightdetailsConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;

    }

    private void setData() {
        bookingId.setText(flightBookingDetails.getReferenceNo());
        bookingStatus.setText(flightBookingDetails.getClientStatus());
        bookingReason.setText(flightBookingDetails.getReasonBooking());
        assCode.setText(flightBookingDetails.getAssessmentCode());
        assCity.setText(flightBookingDetails.getAssessmentCode());
        //pickupCity.setText(flightBookingDetails.getFromLocation());
        bookingDate.setText(flightBookingDetails.getBookingDatetime());
        //dropCity.setText(flightBookingDetails.getToLocation());

        String strPickup = flightBookingDetails.getFromLocation();
        String[] pickupSplit = strPickup.split(", ");
        pickupCity.setText(pickupSplit[0]);

        String strDrop = flightBookingDetails.getToLocation();
        String[] dropSplit = strDrop.split(", ");
        dropCity.setText(dropSplit[0]);

        pickupBoardingPoint.setText(flightBookingDetails.getFromLocation());
        dropEndPont.setText(flightBookingDetails.getToLocation());


        String pickDate=flightBookingDetails.getDepartureDatetime();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = df.parse(pickDate);
            pickupDate.setText(dateFormat.format(date));
            pickupTime.setText(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bookDate=flightBookingDetails.getBookingDatetime();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy HH:mm");
        try {
            Date date1 = df1.parse(bookDate);
            bookingDate.setText(dateFormat1.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (flightBookingDetails.getStatusCotrav()<=1){
            bookingStatus.setText(flightBookingDetails.getClientStatus());
            dropDate.setText("Not Assigned");

        }else {
            if (flightBookingDetails.getStatusCotrav() == 3 || flightBookingDetails.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");
            bookingStatus.setText(flightBookingDetails.getCotravStatus());
        }

    }

}
