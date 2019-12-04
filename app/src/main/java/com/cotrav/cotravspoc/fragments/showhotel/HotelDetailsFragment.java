package com.cotrav.cotravspoc.fragments.showhotel;


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
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.ViewHotelPassanger;
import com.cotrav.cotravspoc.viewmodels.HotelBookingsViewModel;
import com.cotrav.cotravspoc.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HotelDetailsFragment extends Fragment{
    TextView tourType,bookingId,bookingStatus,
                assCode,assCity,bookingReason,
                pickupTime,pickupDate,pickupCity,pickupBoardingPoint,
                dropTime,dropDate,dropCity,dropEndPont,spocName,bookingDate,empList,noOfEmployees,

                ticketNo,roomType,nightNumbers,hotelAddress;

    LinearLayout basicDetailLay,passengerDetailLay,hotelDetailLay;

    ProgressBar progressBar;
    Bundle arg;
    HotelBookingsViewModel hotelBookingsViewModel;
    UserViewModel userViewModel;
    ViewHotelBooking hotelBooking;
    ViewHotelPassanger hotelPassanger;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    List<ViewHotelPassanger> hotelPassangerList;
    private String strspocId,spocEmailId,userContact;


    public HotelDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_hotel_details, container, false);
        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        hotelDetailLay=view.findViewById(R.id.hotel_detail_lay);
        passengerDetailLay = view.findViewById(R.id.passenger_detail_lay);

        empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        bookingId = view.findViewById(R.id.refrence_no);
        tourType = view.findViewById(R.id.hotel_type);
        bookingDate=view.findViewById(R.id.booking_date);
        bookingStatus= view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingReason = view.findViewById(R.id.booking_reason);
        pickupCity = view.findViewById(R.id.pickup_city);
        pickupBoardingPoint = view.findViewById(R.id.pickup_boardingpoint);
        pickupDate = view.findViewById(R.id.pickup_date);
        pickupTime = view.findViewById(R.id.pickup_time);
        dropCity = view.findViewById(R.id.drop_city);
        dropDate = view.findViewById(R.id.drop_date);
        dropEndPont = view.findViewById(R.id.drop_city_endpoint);
        dropTime = view.findViewById(R.id.drop_time);
        spocName = view.findViewById(R.id.spoc_name);

        ticketNo=view.findViewById(R.id.hotel_name);
        roomType=view.findViewById(R.id.room_type);
        nightNumbers=view.findViewById(R.id.no_of_nights);
        hotelAddress=view.findViewById(R.id.hotel_address);

        progressBar = view.findViewById(R.id.progressBar_bookingDetails);

        hotelBookingsViewModel= ViewModelProviders.of(this).get(HotelBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        strspocId = loginPref.getString("spoc_id","n");
        spocEmailId = loginPref.getString("email","n");
        userContact = loginPref.getString("user_contact","n");
        authorization = "Token "+token;
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);


        hotelPassangerList=new ArrayList<>();
        hotelBookingsViewModel.getHotelBookingDetails(authorization,"4",getArguments().
                getString("bookingId")).observe(this, new Observer<ViewHotelBooking>() {
            @Override
            public void onChanged(ViewHotelBooking viewhotelBooking) {
                progressBar.setVisibility(View.GONE);
                if (viewhotelBooking!=null){
                    Log.d("BusBooking", GsonStringConvertor.gsonToString(viewhotelBooking));
                    hotelBooking =viewhotelBooking;
                    if (hotelBooking.getPassangers().size()>0)
                    {
                        passengerDetailLay.setVisibility(View.VISIBLE);
                        List<ViewHotelPassanger> passangers = hotelBooking.getPassangers();
                        String size = String.valueOf(passangers.size());
                        noOfEmployees.setText(size);
                        for (int i = 0;i<passangers.size();i++){
                            int j = i+1;
                            String no = String.valueOf(j);
                            if (passangers.get(i).getEmployeeEmail()!=null&&
                                    passangers.get(i).getEmployeeName()!=null &&
                                    passangers.get(i).getEmployeeContact()!=null){
                                if (i==0)
                                    empList.append(Html.fromHtml("Passenger"+" "+no+"<br>" +
                                            "<b>"+"Employee Name : &emsp;&emsp;&emsp;"+"</b>"+
                                            passangers.get(i).getEmployeeName()+"<br>" +
                                            "<b>"+"Employee Email :&ensp;&emsp;&emsp;&emsp;"+"</b>"+
                                            passangers.get(i).getEmployeeEmail()+"<br>"+
                                            "<b>"+"Employee Contact No.:&ensp;&ensp;"+"</b>"+
                                            passangers.get(i).getEmployeeContact()+""));
                                else
                                    empList.append(Html.fromHtml("<br><br>"+"Passenger"+" "+no+"<br>" +
                                            "<b>"+"Employee Name : &emsp;&emsp;&emsp;"+"</b>"+
                                            passangers.get(i).getEmployeeName()+"<br>" +
                                            "<b>"+"Employee Email :&ensp;&emsp;&emsp;&emsp;"+"</b>"+
                                            passangers.get(i).getEmployeeEmail()+"<br>"+
                                            "<b>"+"Employee Contact No.:&ensp;&ensp;"+"</b>"+
                                            passangers.get(i).getEmployeeContact()+""));
                            }
                            else empList.append(Html.fromHtml("<b>"+"Passemger"+"</b>"+" "+no+"<br>" +
                                    "Passenger Data Not Found"));
                        }

                    }

               /*     if(hotelBooking.getPassangers().size()<=0)
                    {
                        passengerDetailLay.setVisibility(view.GONE);
                    }else{

                        passengerDetailLay.setVisibility(view.VISIBLE);

                        for (int i=0;i<=hotelBooking.getPassangers().size();i++)
                        {

                            hotelPassanger= hotelBooking.getPassangers().get(0);

                        }
                        ticketNo.setText(hotelBooking.getReferenceNo());
                        roomType.setText(hotelBooking.getRoomTypeName());
                        nightNumbers.setText(hotelBooking.getNoOfSeats());
                        hotelAddress.setText(hotelBooking.getPreferredArea());
                        hotelDetailLay.setVisibility(View.VISIBLE);

                    }*/

                setData();
                }
            }
        });


        hotelBookingsViewModel.getCancelledConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;

    }

    private void setData() {
        tourType.setText(hotelBooking.getHotelTypeName());
        bookingId.setText(hotelBooking.getReferenceNo());
        bookingStatus.setText(hotelBooking.getClientStatus());
        bookingReason.setText(hotelBooking.getReasonBooking());
        assCode.setText(hotelBooking.getAssessmentCode().toString());
        assCity.setText(hotelBooking.getAssessmentCityId());

        //pickupCity.setText(hotelBooking.getPreferredArea());

        String strMain = hotelBooking.getPreferredArea();
        pickupBoardingPoint.setText(strMain);
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);



        String pickDate=hotelBooking.getCheckinDatetime();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = df.parse(pickDate);
            pickupDate.setText(dateFormat.format(date));
            pickupTime.setText(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bookDate=hotelBooking.getBookingDatetime();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy HH:mm");
        try
        {
            Date date1 = df1.parse(bookDate);
            bookingDate.setText(dateFormat1.format(date1));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        basicDetailLay.setVisibility(View.VISIBLE);

        if (hotelBooking.getStatusCotrav()<=1){
            bookingStatus.setText(hotelBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        }else
        {
            if (hotelBooking.getStatusCotrav()==3||hotelBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");
            bookingStatus.setText(hotelBooking.getCotravStatus());
        }
    }
}
