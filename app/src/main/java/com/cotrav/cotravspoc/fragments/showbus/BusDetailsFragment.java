package com.cotrav.cotravspoc.fragments.showbus;



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
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.BusBooking;
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.ViewBusPassanger;
import com.cotrav.cotravspoc.viewmodels.BusBookingsViewModel;
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
public class BusDetailsFragment extends Fragment
{
    TextView bookingId,bookingStatus,
            assCode,assCity,bookingReason,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,
            dropTime,dropDate,dropCity,dropEndPont,bookingDate,
            ticketNo,seatNo,boardingPoint,pnrNo,busType,empList,noOfEmployees;

    LinearLayout basicDetailLay,passengerDetailLay,busDetailsLayout;
    ProgressBar progressBar;
    Bundle arg;
    BusBookingsViewModel busBookingsViewModel;
    UserViewModel userViewModel;
    BusBooking busBooking;

    ViewBusPassanger busPassanger;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    List<ViewBusPassanger> busPassangerList;
    private String strspocId,spocEmailId,employeeName="";

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public BusDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_bus_details, container, false);

        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        busDetailsLayout=view.findViewById(R.id.bus_details_layout);
        passengerDetailLay = view.findViewById(R.id.passenger_detail_lay);
        empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        bookingId = view.findViewById(R.id.refrence_no);
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


        ticketNo=view.findViewById(R.id.txt_ticket_number);
        seatNo=view.findViewById(R.id.txt_number_seats);
        boardingPoint=view.findViewById(R.id.txt_boarding_point);
        busType=view.findViewById(R.id.txt_bus_type);
        pnrNo=view.findViewById(R.id.txt_pnr_number);


        progressBar = view.findViewById(R.id.progressBar_bookingDetails);




        busBookingsViewModel= ViewModelProviders.of(this).get(BusBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        strspocId = loginPref.getString("spoc_id","n");
        spocEmailId = loginPref.getString("email","n");
        authorization = "Token "+token;
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);


        busPassangerList=new ArrayList<>();
        busBookingsViewModel.getBusBookingDetails(authorization,"4",
                getArguments().getString("bookingId")).observe(this, new Observer<BusBooking>() {
            @Override
            public void onChanged(BusBooking busBookingDetail) {
                progressBar.setVisibility(View.GONE);
                if (busBookingDetail!=null){
                    Log.d("BusBooking", GsonStringConvertor.gsonToString(busBookingDetail));
                    busBooking =busBookingDetail;
                    //busBooking.getPassangers().get(0).getEmployeeName();

/*                    if(busBooking.getPassangers().size()<=0)
                    {
                        passengerDetailLay.setVisibility(view.GONE);

                    }else{
                        passengerDetailLay.setVisibility(view.VISIBLE);
                        for (int i=0;i<=busBooking.getPassangers().size();i++)
                        {

                            busPassanger= busBooking.getPassangers().get(0);

                        }
                        empName.setText(busPassanger.getEmployeeName());
                        empContact.setText(busPassanger.getEmployeeContact());
                        empEmail.setText(busPassanger.getEmployeeEmail());
                    }*/


                    if (busBooking.getPassangers().size()>0)
                    {
                        passengerDetailLay.setVisibility(View.VISIBLE);
                        List<ViewBusPassanger> passangers = busBooking.getPassangers();
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


                    if (busBooking.getStatusCotrav()>=4){
                        ticketNo.setText(busBooking.getReferenceNo().toString());
                        seatNo.setText(busBooking.getSeatNo().toString());
                        pnrNo.setText(busBooking.getPnrNo().toString());
                        boardingPoint.setText(busBooking.getBoardingPoint().toString());
                        busType.setText(busBooking.getBusTypePriority1().toString());
                        busDetailsLayout.setVisibility(View.VISIBLE);
                    }
                    if (busBooking.getStatusCotrav()<=1){
                        bookingStatus.setText(busBooking.getClientStatus());
                        dropDate.setText("Not Assigned");

                    }else{
                        if (busBooking.getStatusCotrav()==3||busBooking.getStatusCotrav() == 2)
                            dropDate.setText("Not Assigned");
                        bookingStatus.setText(busBooking.getCotravStatus());
                    }


                    setData();
                }
            }
        });


        busBookingsViewModel.getBusdetailsConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;

    }

    private void setData() {
        bookingId.setText(busBooking.getReferenceNo());
        bookingReason.setText(busBooking.getReasonBooking());
        assCode.setText(busBooking.getAssessmentCode());
        assCity.setText(busBooking.getAssessmentCityId());
        bookingDate.setText(busBooking.getBookingDatetime());

        String strMain = busBooking.getPickupLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(busBooking.getPickupLocation());


        String strMain2 = busBooking.getDropLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(busBooking.getDropLocation());


        String pickDate=busBooking.getPickupFromDatetime();

        try {
            Date date = df.parse(pickDate);
            pickupDate.setText(dateFormat.format(date));
            pickupTime.setText(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bookDate=busBooking.getBookingDatetime();

        try {
            Date date1 = df.parse(bookDate);
            bookingDate.setText(dateFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        basicDetailLay.setVisibility(View.VISIBLE);

    }



}
