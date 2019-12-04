package com.cotrav.cotravspoc.fragments.showtaxi;


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
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.ViewTaxiPassanger;
import com.cotrav.cotravspoc.viewmodels.TaxiBookingsViewModel;
import com.cotrav.cotravspoc.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaxiDetailsFragment extends Fragment
{
    TextView tourType,bookingId,bookingStatus,
            assCode,assCity,bookingReason,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,
            dropTime,dropDate,dropCity,dropEndPont,
           bookingDate,driverName,driverContact,driverEmail,taxiTicketNo,taxiType,taxiModel,taxiRegNo,empList,noOfEmployees;

    LinearLayout basicDetailLay,passengerDetailLay,taxiDetailLay,driverDetailLay;

    ProgressBar progressBar;
    Bundle arg;
    TaxiBookingsViewModel taxiBookingsViewModel;
    UserViewModel userViewModel;
    ViewTaxiBooking taxiBooking;

    ViewTaxiPassanger taxiPassanger;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    List<ViewTaxiPassanger> taxiPassangerList;
    private String strspocId,spocEmailId,employeeName="",userContact,userType;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public TaxiDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_taxi_details, container, false);


        progressBar = view.findViewById(R.id.progressBar_bookingDetails);

        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        taxiDetailLay=view.findViewById(R.id.taxi_detail_lay);
        driverDetailLay=view.findViewById(R.id.driver_detail_lay);
        passengerDetailLay = view.findViewById(R.id.passenger_detail_lay);
        empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);

        bookingId = view.findViewById(R.id.refrence_no);
        tourType = view.findViewById(R.id.tour_type);
        bookingStatus= view.findViewById(R.id.booking_status);
        bookingDate=view.findViewById(R.id.booking_date);
        assCity=view.findViewById(R.id.assesment_city);
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
        taxiTicketNo=view.findViewById(R.id.ticket_number);
        taxiType=view.findViewById(R.id.txt_taxi_type);
        taxiModel=view.findViewById(R.id.txt_taxi_model);
        taxiRegNo=view.findViewById(R.id.txt_reg_number);

        driverName=view.findViewById(R.id.txt_driver_name);
        driverContact=view.findViewById(R.id.txt_driver_contact);
        driverEmail=view.findViewById(R.id.txt_driver_email);




        taxiBookingsViewModel= ViewModelProviders.of(this).get(TaxiBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        strspocId = loginPref.getString("spoc_id","n");
        spocEmailId = loginPref.getString("email","n");
        userContact = loginPref.getString("user_contact","n");
        authorization = "Token "+token;
        userType=loginPref.getString("usertype","n");
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);
        taxiPassangerList=new ArrayList<>();
        taxiBookingsViewModel.getTaxiBookingDetails(authorization,userType,getArguments().
                getString("bookingId"),strspocId).observe(this, new Observer<ViewTaxiBooking>() {
            @Override
            public void onChanged(ViewTaxiBooking viewTaxiBooking) {
                progressBar.setVisibility(View.GONE);
                if (viewTaxiBooking!=null){
                    Log.d("TaxiBooking", GsonStringConvertor.gsonToString(viewTaxiBooking));
                    taxiBooking =viewTaxiBooking;
                    //busBooking.getPassangers().get(0).getEmployeeName();

                  /*  if(taxiBooking.getPassangers().size()<=0)
                    {
                        passengerDetailLay.setVisibility(view.GONE);
                    }else{
                        passengerDetailLay.setVisibility(view.VISIBLE);
                        for (int i=0;i<=taxiBooking.getPassangers().size();i++)
                        {

                            taxiPassanger= taxiBooking.getPassangers().get(0);

                        }
                        empName.setText(taxiPassanger.getEmployeeName());
                        empContact.setText(taxiPassanger.getEmployeeContact());
                        empEmail.setText(taxiPassanger.getEmployeeEmail());

                    }*/

                    if (taxiBooking.getPassangers().size()>0)
                    {
                        passengerDetailLay.setVisibility(View.VISIBLE);
                        List<ViewTaxiPassanger> passangers = taxiBooking.getPassangers();
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
                    if (taxiBooking.getStatusCotrav()==4) {
                        driverName.setText(taxiBooking.getDriverName());
                        driverContact.setText(taxiBooking.getDriverContact());
                        driverEmail.setText(taxiBooking.getDriverEmail());
                        driverDetailLay.setVisibility(View.VISIBLE);
                        taxiTicketNo.setText(taxiBooking.getReferenceNo());
                        taxiRegNo.setText(taxiBooking.getTaxiRegNo());
                        taxiType.setText(taxiBooking.getTaxiTypeName());
                        taxiModel.setText(taxiBooking.getBrandName()+" "+ taxiBooking.getModelName());
                        taxiDetailLay.setVisibility(View.VISIBLE);
                    }

                    setData();
                }
            }
        });


        taxiBookingsViewModel.getTaxiDetailsConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;

    }

    private void setData() {


        bookingId.setText(taxiBooking.getReferenceNo());
        //bookingStatus.setText(taxiBooking.getCotravStatus());
        bookingReason.setText(taxiBooking.getReasonBooking());
        assCode.setText(taxiBooking.getAssessmentCode());
        assCity.setText(taxiBooking.getAssessmentCityId());

        String strMain = taxiBooking.getPickupLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(taxiBooking.getPickupLocation());


        String strMain2 = taxiBooking.getDropLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(taxiBooking.getDropLocation());


        String pickDate=taxiBooking.getPickupDatetime();
         try {
            Date date = df.parse(pickDate);
            pickupDate.setText(dateFormat.format(date));
            pickupTime.setText(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bookDate=taxiBooking.getBookingDate();

        //DateFormat timeFormat1 = new SimpleDateFormat("HH:mm");
        try
        {
            Date date1 = df.parse(bookDate);
            bookingDate.setText(dateFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (taxiBooking.getTourType().equals("1")) {
            tourType.setText("RADIO");
            //pickupCity.setText(taxiBooking.getCityName());
            //dropCity.setText(taxiBooking.getCityName());
        }
        if (taxiBooking.getTourType().equals("2")){
            tourType.setText("LOCAL");
            //pickupCity.setText(taxiBooking.getCityName());
            //dropCity.setText(taxiBooking.getCityName());
        }
        if (taxiBooking.getTourType().equals("3")){
            tourType.setText("OUTSTATION");
            //String strMain = taxiBooking.getDropLocation();
            //String[] arrSplit = strMain.split(", ");
            //dropCity.setText(arrSplit[0]);
        }
        basicDetailLay.setVisibility(View.VISIBLE);
        //spocDetailLay.setVisibility(View.VISIBLE);
        if (taxiBooking.getStatusCotrav()<=1){
            bookingStatus.setText(taxiBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (taxiBooking.getStatusCotrav()==3||taxiBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");
            bookingStatus.setText(taxiBooking.getCotravStatus());
        }

    }
}
