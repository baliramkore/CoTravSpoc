package com.cotrav.cotravspoc.fragments.showtrain;


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
import com.cotrav.cotravspoc.models.show_train_model.view_train.ViewTrainBooking;
import com.cotrav.cotravspoc.models.show_train_model.view_train.ViewTrainPassanger;
import com.cotrav.cotravspoc.viewmodels.TrainBookingsViewModel;
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
public class TrainDetailsFragment extends Fragment {
    TextView train_type,bookingId,bookingStatus,
            assCode,assCity,bookingReason,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,
            dropTime,dropDate,dropCity,dropEndPont,bookingDate,
            ticketNo,trainName,seatNo,pnrNo,trainType,empList,noOfEmployees;

    LinearLayout basicDetailLay,passengerDetailLay,trainDetailLay,driverDetailLay;

    ProgressBar progressBar;
    Bundle arg;
    TrainBookingsViewModel trainBookingsViewModel;
    UserViewModel userViewModel;
    ViewTrainBooking trainBooking;

    ViewTrainPassanger trainPassanger;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    List<ViewTrainPassanger> trainPassangerList;
    private String strspocId,spocEmailId,employeeName="",userContact,userType;


    public TrainDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_train_details, container, false);
        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        trainDetailLay=view.findViewById(R.id.train_detail_lay);
        passengerDetailLay = view.findViewById(R.id.passenger_detail_lay);
        empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        progressBar = view.findViewById(R.id.progressBar_bookingDetails);
        bookingId = view.findViewById(R.id.refrence_no);
        bookingStatus= view.findViewById(R.id.booking_status);
        assCode = view.findViewById(R.id.assesment_code);
        assCity = view.findViewById(R.id.assesment_city);
        bookingReason = view.findViewById(R.id.booking_reason);
        pickupCity = view.findViewById(R.id.pickup_city);
        pickupBoardingPoint = view.findViewById(R.id.pickup_boardingpoint);
        pickupDate = view.findViewById(R.id.pickup_date);
        pickupTime = view.findViewById(R.id.pickup_time);
        dropCity = view.findViewById(R.id.drop_city);
        dropDate = view.findViewById(R.id.drop_date);
        dropEndPont = view.findViewById(R.id.drop_city_endpoint);
        dropTime = view.findViewById(R.id.drop_time);
        bookingDate=view.findViewById(R.id.booking_date);
        ticketNo=view.findViewById(R.id.txt_ticket_number);
        trainName=view.findViewById(R.id.txt_train_name);
        seatNo=view.findViewById(R.id.txt_seat_number);
        pnrNo=view.findViewById(R.id.txt_pnr_number);
        trainType=view.findViewById(R.id.txt_train_type);




        trainBookingsViewModel= ViewModelProviders.of(this).get(TrainBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        strspocId = loginPref.getString("spoc_id","n");
        spocEmailId = loginPref.getString("email","n");
        userContact = loginPref.getString("user_contact","n");
        authorization = "Token "+token;
        userType= loginPref.getString("usertype","n");
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);

        trainPassangerList=new ArrayList<>();
        trainBookingsViewModel.getTrainBookingDetails(authorization,userType,getArguments().
                getString("bookingId")).observe(this, new Observer<ViewTrainBooking>() {
            @Override
            public void onChanged(ViewTrainBooking viewTrainBooking) {
                progressBar.setVisibility(View.GONE);
                if (viewTrainBooking!=null){
                    Log.d("TrainBooking", GsonStringConvertor.gsonToString(viewTrainBooking));
                    trainBooking =viewTrainBooking;
                    /*//busBooking.getPassangers().get(0).getEmployeeName();

                    if(trainBooking.getPassangers().size()<=0)
                    {
                        passengerDetailLay.setVisibility(view.GONE);
                    }else{

                        passengerDetailLay.setVisibility(view.VISIBLE);
                        for (int i=0;i<=trainBooking.getPassangers().size();i++)
                        {

                            trainPassanger= trainBooking.getPassangers().get(0);

                        }
                        empName.setText(trainPassanger.getEmployeeName());
                        empContact.setText(trainPassanger.getEmployeeContact());
                        empEmail.setText(trainPassanger.getEmployeeEmail());

                    }*/
                    if (trainBooking.getPassangers().size()>0)
                    {
                        passengerDetailLay.setVisibility(View.VISIBLE);
                        List<ViewTrainPassanger> passangers = trainBooking.getPassangers();
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

                    if (trainBooking.getStatusCotrav()>=4){

                        ticketNo.setText(trainBooking.getTicketNo());
                        trainName.setText(trainBooking.getTrainName());
                        seatNo.setText(trainBooking.getSeatNo());
                        pnrNo.setText(trainBooking.getPnrNo());
                        trainType.setText(trainBooking.getTrainTypePriority1());
                        trainDetailLay.setVisibility(View.VISIBLE);

                    }

                    //String name=  busPassanger.getEmployeeName();


                    setData();
                }
            }
        });


        trainBookingsViewModel.getTrainDetailsConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;

    }
    private void setData()
    {
        bookingId.setText(trainBooking.getReferenceNo());
        bookingStatus.setText(trainBooking.getCotravStatus());
        bookingReason.setText(trainBooking.getReasonBooking());
        bookingDate.setText(trainBooking.getBookingDatetime());
        pickupCity.setText(trainBooking.getPickupLocation());
        dropCity.setText(trainBooking.getDropLocation());
        assCode.setText(trainBooking.getAssessmentCode());
        assCity.setText(trainBooking.getAssessmentCityId());

        String pickDate=trainBooking.getPickupFromDatetime();
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
        String bookDate=trainBooking.getBookingDatetime();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy HH:mm");
        //DateFormat timeFormat1 = new SimpleDateFormat("HH:mm");
        try {
            Date date1 = df.parse(bookDate);
            bookingDate.setText(dateFormat1.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        basicDetailLay.setVisibility(View.VISIBLE);
        //spocDetailLay.setVisibility(View.VISIBLE);
        if (trainBooking.getStatusCotrav()<=1){
            bookingStatus.setText(trainBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (trainBooking.getStatusCotrav()==3||trainBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");
            bookingStatus.setText(trainBooking.getCotravStatus());
        }
    }
}
