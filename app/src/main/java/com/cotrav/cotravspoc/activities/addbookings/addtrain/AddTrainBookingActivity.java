package com.cotrav.cotravspoc.activities.addbookings.addtrain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addflight.AddFlightBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addhotel.AddHotelBookingActivity;
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.models.trainstationmodel.TrainStation;
import com.cotrav.cotravspoc.models.traintypemodel.TrainType;
import com.cotrav.cotravspoc.retrofit.APIurls;
import com.cotrav.cotravspoc.retrofit.AddTrainBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.viewmodels.AllCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCodeViewModel;
import com.cotrav.cotravspoc.viewmodels.TrainStationViewModel;
import com.cotrav.cotravspoc.viewmodels.TrainTypeViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddTrainBookingActivity extends AppCompatActivity {

    Context context;
    SharedPreferences loginpref,billing_pref,spocpref;
    SearchableSpinner txt_pickupCity,txt_dropCity,txt_billing,txt_assesmentCity,txt_assesmentCode,txt_trainType,txtTrainStations;
    static TextView txt_dateFrom,txt_timeFrom,txt_dateTo,txt_timeTo;
    static TextView txt_pessengers;
    static TextView txt_prefTrain;
    EditText txt_reason;
    Button btnSubmit;
    AllCitiesViewModel allCitiesViewModel;
    AssessmentCitiesViewModel assessmentCitiesViewModel;
    AssessmentCodeViewModel assessmentCodeViewModel;
    TrainTypeViewModel trainTypeViewModel;
    TrainStationViewModel trainStationViewModel;
    ArrayAdapter<String> allcityadapter,billingAdapter,assesmentadapter,assesmentcodeAdapter,trainadapter,trainstationadapter;
    ArrayList<String> picktrain_station_list,picktrain_station_id_list,droptrain_station_list,droptrain_station_id_list,train_type_list,train_type_id_list,pickup_CityList,pickup_CityIdList,drop_CityList,drop_CityIdList,bill_namelist,bill_idlist,assessmentCityList,assessmentCityIdList,assessmentCodeList,assessmentCodeIdList;
    ArrayList<String> employee_idlist,employee_id_value,getEmployee_idphno,age_list,male_female_list,id_type_list,id_no_list;
    static int total=1,EMPLOYEE_INFO=100;
    String city,finalAddress;
    double lat;
    double lng;
    final int train_PICKUP_LOCATION=1,train_DROP_LOCATION=0,train_BOARDING_LOCATION=3;
    public static final String TAG = "trainBookingActivity";
    String hasAssCode,access_token,Authorization,userType,userId,corporateId,groupId,subgroupId,spocId,mpickupCity,mdropCity,mpickupLocation,
            mpickupDate,mpickupTime,finalpickupDateTime,finaldropDateTime,booking_datetime,mdropDate,mdropTime,mtrainType,mpessengers,massesmentCity,massesmentCode,mbiilEntity,mprefTrain,mreason;
    static  String pri1 = "AC Seater",pri2 ="AC Seater",pri3 ="AC Seater";
    static String finalpriority="";
    static  String start_date_send,end_date_send;
    private String hasSingleEmployee;
    Toolbar mtoolbar;
    LinearLayout layoutAssisment;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train_booking);
        //setupToolbar();
        mtoolbar=findViewById(R.id.toolbar);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        mtoolbar.setTitle("Add Train Booking");
        mtoolbar.setNavigationIcon(R.drawable.test_back);

        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }});
        context=AddTrainBookingActivity.this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Stations");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        userType = loginpref.getString("usertype", "n");
        billing_pref= getSharedPreferences("billing_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        Authorization="Token "+loginpref.getString("access_token", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocpref=getSharedPreferences("spoc_info",MODE_PRIVATE);
        hasAssCode=spocpref.getString("has_assisment_code","n");
        Log.d("====token===",access_token);
        //progressBar = findViewById(R.id.progressBar);
        txt_pickupCity=(SearchableSpinner) findViewById(R.id.train_spinner_city);
        txt_dropCity=(SearchableSpinner) findViewById(R.id.train_dropCity);
        //txt_boardingPoint=(TextView)findViewById(R.id.train_pickupLocation);
        txt_dateFrom=(TextView)findViewById(R.id.train_datePickup);
        txt_timeFrom=(TextView)findViewById(R.id.train_timePickup);
        txt_dateTo=(TextView)findViewById(R.id.train_dateDrop);
        txt_timeTo=(TextView)findViewById(R.id.train_timeDrop);

        txt_pessengers=(TextView)findViewById(R.id.train_pessangers);
        txt_trainType=(SearchableSpinner) findViewById(R.id.train_type);
        txt_assesmentCity=(SearchableSpinner) findViewById(R.id.train_assesmentCity);
        txt_assesmentCode =(SearchableSpinner) findViewById(R.id.train_assesmentCode);
        txt_prefTrain=(TextView)findViewById(R.id.train_Prefered);
        txt_billing=(SearchableSpinner)findViewById(R.id.train_billingEntity);
        txt_reason=(EditText)findViewById(R.id.train_reason);

        layoutAssisment=(LinearLayout)findViewById(R.id.layout_assisment);
        if (hasAssCode.equals("0"))
        {
            layoutAssisment.setVisibility(View.GONE);
        }
        btnSubmit=(Button)findViewById(R.id.btn_submit);

        pickup_CityList=new ArrayList<>();
        drop_CityList=new ArrayList<>();
        pickup_CityIdList=new ArrayList<>();
        drop_CityIdList=new ArrayList<>();
        bill_namelist=new ArrayList<>();
        bill_idlist=new ArrayList<>();
        assessmentCityList=new ArrayList<>();
        assessmentCityIdList=new ArrayList<>();
        assessmentCodeList=new ArrayList<>();
        assessmentCodeIdList=new ArrayList<>();
        employee_idlist = new ArrayList<String>();
        getEmployee_idphno = new ArrayList<String>();
        employee_id_value = new ArrayList<>();
        age_list = new ArrayList<String>();
        male_female_list = new ArrayList<String>();
        id_type_list = new ArrayList<String>();
        id_no_list = new ArrayList<String>();
        id_no_list = new ArrayList<String>();
        train_type_id_list = new ArrayList<String>();
        train_type_list = new ArrayList<String>();
        picktrain_station_list=new ArrayList<String>();
        picktrain_station_id_list=new ArrayList<String>();
        droptrain_station_list=new ArrayList<String>();
        droptrain_station_id_list=new ArrayList<String>();



        hasSingleEmployee = loginpref.getString("has_single_employee", null);
        assessmentCitiesViewModel= ViewModelProviders.of(this).get(AssessmentCitiesViewModel.class);
        assessmentCitiesViewModel.initViewModel(Authorization, userType,corporateId);
        assessmentCodeViewModel= ViewModelProviders.of(this).get(AssessmentCodeViewModel.class);
        assessmentCodeViewModel.initViewModel(Authorization, userType,corporateId);
        trainTypeViewModel= ViewModelProviders.of(this).get(TrainTypeViewModel.class);
        trainTypeViewModel.initViewModel(Authorization,userType,corporateId);


        trainStationViewModel= ViewModelProviders.of(this).get(TrainStationViewModel.class);
        trainStationViewModel.initViewModel(Authorization,userType);



        assessmentCityList.add(new String("Ass.City"));
        assessmentCityIdList.add(new String("Ass.City"));
        assesmentadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,assessmentCityList);
        txt_assesmentCity.setAdapter(assesmentadapter);
        assessmentCitiesViewModel.getLiveAssessmentCityList(Authorization,userType,corporateId).observe(this, new Observer<List<AssCity>>() {
            @Override
            public void onChanged(List<AssCity> assCities) {

                if (assCities!=null && assCities.size()>0){
                    for (int i=0;i<assCities.size();i++){
                        assessmentCityList.add(assCities.get(i).getCityName());
                        assessmentCityIdList.add(String.valueOf(assCities.get(i).getId()));

                    }
                }
                Log.d("Ass.City", GsonStringConvertor.gsonToString(assCities));
            }
        });

        assessmentCodeList.add(new String("Ass.Code"));
        assessmentCodeIdList.add(new String("Ass.Code"));
        assesmentcodeAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,assessmentCodeList);
        txt_assesmentCode.setAdapter(assesmentcodeAdapter);
        assessmentCodeViewModel.getLiveAssessmentCodeList(Authorization,userType,corporateId).observe(this, new Observer<List<AssCode>>() {
            @Override
            public void onChanged(List<AssCode> assCodes) {

                if (assCodes!=null && assCodes.size()>0){
                    assessmentCodeList.clear();
                    assessmentCodeIdList.clear();
                    assessmentCodeList.add(new String("Ass.Code"));
                    assessmentCodeIdList.add(new String("Ass.Code"));
                    for (int i=0;i<assCodes.size();i++){
                        //assesmentcodeAdapter.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeList.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeIdList.add(String.valueOf(assCodes.get(i).getId()));

                    }
                }
                Log.d("Ass. Code", GsonStringConvertor.gsonToString(assCodes));
            }
        });




        train_type_list.add(new String("Train Type"));
        train_type_id_list.add(new String("Train Type"));
        trainadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,train_type_list);
        txt_trainType.setAdapter(trainadapter);
        trainTypeViewModel.getTrainTypes().observe(this, new Observer<List<TrainType>>() {
            @Override
            public void onChanged(@Nullable List<TrainType> trainTypes) {
                Log.d("TaxiType",GsonStringConvertor.gsonToString(trainTypes)+" taxi");
                train_type_list.clear();
                train_type_id_list.clear();
                train_type_list.add("Train Type");
                train_type_id_list.add("Train Type");
                if (trainTypes!=null && trainTypes.size()>0)
                {
                    for (int i=0;i<trainTypes.size();i++)
                    {
                        train_type_list.add(trainTypes.get(i).getName());
                        train_type_id_list.add(String.valueOf(trainTypes.get(i).getId()));
                    }
                    trainadapter.notifyDataSetChanged();
                }
                Log.d("TaxiTypes", GsonStringConvertor.gsonToString(trainTypes));
            }
        });






        picktrain_station_list.add(new String("Pickup Station"));
        picktrain_station_id_list.add(new String("Pickup Station"));
        trainstationadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,picktrain_station_list);
        txt_pickupCity.setAdapter(trainstationadapter);
        trainStationViewModel.getTrainStations().observe(this, new Observer<List<TrainStation>>() {
            @Override
            public void onChanged(@Nullable List<TrainStation> trainStations) {
                Log.d("TaxiType",GsonStringConvertor.gsonToString(trainStations)+" taxi");
                picktrain_station_list.clear();
                picktrain_station_id_list.clear();
                picktrain_station_list.add("Pickup Station");
                picktrain_station_id_list.add("Pickup Station");
                if (trainStations!=null && trainStations.size()>0)
                {
                    for (int i=0;i<trainStations.size();i++)
                    {
                        picktrain_station_list.add(trainStations.get(i).getStationName()+" ( "+trainStations.get(i).getStationCode()+" )");
                        picktrain_station_id_list.add(String.valueOf(trainStations.get(i).getId()));
                    }
                    progressDialog.dismiss();
                    trainstationadapter.notifyDataSetChanged();
                }
                Log.d("Train Station", GsonStringConvertor.gsonToString(trainStations));
            }
        });


        droptrain_station_list.add(new String("Drop Station"));
        droptrain_station_id_list.add(new String("Drop Station"));
        trainstationadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,droptrain_station_list);
        txt_dropCity.setAdapter(trainstationadapter);
        trainStationViewModel.getTrainStations().observe(this, new Observer<List<TrainStation>>() {
            @Override
            public void onChanged(@Nullable List<TrainStation> trainStations) {
                Log.d("TaxiType",GsonStringConvertor.gsonToString(trainStations)+" taxi");
                droptrain_station_list.clear();
                droptrain_station_id_list.clear();
                droptrain_station_list.add("Drop Station");
                droptrain_station_id_list.add("Drop Station");
                if (trainStations!=null && trainStations.size()>0)
                {
                    for (int i=0;i<trainStations.size();i++)
                    {
                        droptrain_station_list.add(trainStations.get(i).getStationName()+" ( "+trainStations.get(i).getStationCode()+" )");
                        droptrain_station_id_list.add(String.valueOf(trainStations.get(i).getId()));
                    }
                    trainstationadapter.notifyDataSetChanged();
                }
                Log.d("Train Station", GsonStringConvertor.gsonToString(trainStations));
            }
        });





        txt_dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DialogFragment datePickerFragment = new AddTrainBookingActivity.DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        txt_dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        txt_timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePickerFragment = new AddTrainBookingActivity.TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");

            }
        });

        txt_timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","end");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "endtime");
            }
        });

        txt_pessengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getSupportFragmentManager(),"no of radio_passenger");
            }
        });





        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    setData();

                    if (isInternetConnected(AddTrainBookingActivity.this))
                    {
                        if (    (txt_billing.getSelectedItemPosition()==0)
                                ||(txt_trainType.getSelectedItemPosition()==0)
                                ||(txt_dropCity.getSelectedItemPosition()==0)
                                ||(txt_pickupCity.getSelectedItemPosition()==0)
                                ||txt_dateFrom.getText().toString().equals("Pickup Date")
                                ||txt_timeFrom.getText().toString().equals("Pickup Time")
                                ||txt_pessengers.getText().toString().equals("Passenger")
                                ||(txt_reason.getText().toString().length() < 1)

                        ) {

                            if (txt_dateFrom.getText().toString().equals("Pickup Date")) {
                                txt_dateFrom.setError("field required");

                            }
                            if (txt_timeFrom.getText().toString().equals("Pickup Time")) {
                                txt_timeFrom.setError("field required");

                            }

                            if (txt_pessengers.getText().toString().equals("Passenger")) {
                                txt_pessengers.setError("field required");
                                //txt_pickupLocation.setError("field required");
                            }

                            if (employee_idlist.size() == 0) {
                                txt_pessengers.setError("field required");
                                Toast.makeText(AddTrainBookingActivity.this, "please choose Employee ", Toast.LENGTH_SHORT).show();
                            }
                            if (txt_reason.getText().toString().length() < 1){
                                txt_reason.setError("field required");
                            }

                            if (hasAssCode.equals("0")==false)
                            {
                                if (txt_assesmentCode.getSelectedItem().toString().equals("Ass.Code"))
                                {
                                    ((android.widget.TextView) txt_assesmentCode.getSelectedView()).setError("field required");
                                }

                                if (txt_assesmentCity.getSelectedItem().toString().equals("Ass.City"))
                                {
                                    ((android.widget.TextView) txt_assesmentCity.getSelectedView()).setError("field required");

                                }
                            }

                            if (txt_billing.getSelectedItem().toString().equals("Billing ID")) {
                                ((android.widget.TextView) txt_billing.getSelectedView()).setError("field required");
                            }

                            if (txt_trainType.getSelectedItem().toString().equals("Train Type")) {
                                ((android.widget.TextView) txt_trainType.getSelectedView()).setError("field required");
                            }
                            if (txt_dropCity.getSelectedItem().equals("Drop Station")){
                                ((android.widget.TextView) txt_dropCity.getSelectedView()).setError("field required");

                            }
                            if (txt_pickupCity.getSelectedItem().equals("Pickup Station")){
                                ((android.widget.TextView) txt_pickupCity.getSelectedView()).setError("field required");


                            }




                        } else {


                            final Dialog dialog = new Dialog(AddTrainBookingActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                            mDialogTitile.setText("TRAIN BOOKING");
                            mDialogmsg.setText("Please Press Yes to Complete booking");

                            Button myes = dialog.findViewById(R.id.yes_txt);
                            Button mNo = dialog.findViewById(R.id.no_txt);

                            myes.setText("Yes");
                            myes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new dotrainBooking().execute(Authorization,userType);
                                    dialog.dismiss();
                                }
                            });
                            mNo.setText("Cancel");
                            mNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        /*    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                            alertDialogBuilder.setTitle("TRAIN BOOKING");
                            alertDialogBuilder.setIcon(R.drawable.check_icon);
                            alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                            alertDialogBuilder.setCancelable(true);
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @SuppressLint("MissingPermission")
                                public void onClick(DialogInterface dialog, int id) {

                                    new dotrainBooking().execute(Authorization,userType);

                                }
                            });
                            alertDialogBuilder.setNegativeButton(
                                    "No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = alertDialogBuilder.create();
                            alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                            alert11.show();
*/
                        }

                    }
                }/*{

                setData();
                if (isInternetConnected(AddHotelBookingActivity.this))
                {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("HOTEL BOOKING");
                        alertDialogBuilder.setIcon(R.drawable.check_icon);
                        alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {

                                new doHotelBooking().execute(Authorization,userType);

                            }
                        });
                        alertDialogBuilder.setNegativeButton(
                                "No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = alertDialogBuilder.create();
                        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                        alert11.show();


                }
            }*/

            /*{


                setData();

                if (isInternetConnected(AddTrainBookingActivity.this))
                {

                        if (    (txt_billing.getSelectedItemPosition()==0)
                                ||(txt_assesmentCode.getSelectedItemPosition()==0)
                                ||(txt_assesmentCity.getSelectedItemPosition()==0)) {

                            if (txt_pickupCity.getSelectedItem().equals("Pickup Station")){
                                ((android.widget.TextView) txt_pickupCity.getSelectedView()).setError("field required");


                            }
                            if (txt_dropCity.getSelectedItem().equals("Drop Station")){
                                ((android.widget.TextView) txt_dropCity.getSelectedView()).setError("field required");


                            }
                            if (txt_boardingPoint.getText().toString().equals("Pickup Location")){
                                txt_boardingPoint.setError("field required");

                            }

                            if (txt_dateFrom.getText().toString().equals("Pickup Date")) {
                                txt_dateFrom.setError("field required");

                            }
                            if (txt_timeFrom.getText().toString().equals("Pickup Time")) {
                                txt_timeFrom.setError("field required");

                            }
                            if (txt_trainType.getSelectedItem().toString().equals("Train Type")) {
                                ((android.widget.TextView) txt_trainType.getSelectedView()).setError("field required");
                            }
                            if (txt_pessengers.getText().toString().equals("Passenger")) {
                                txt_pessengers.setError("field required");
                                //txt_pickupLocation.setError("field required");
                            }
                            if (txt_billing.getSelectedItem().toString().equals("Billing ID")) {
                                ((android.widget.TextView) txt_billing.getSelectedView()).setError("field required");
                            }
                            if (txt_assesmentCode.getSelectedItem().toString().equals("Ass.Code")) {
                                ((android.widget.TextView) txt_assesmentCode.getSelectedView()).setError("field required");

                            }

                            if (txt_assesmentCity.getSelectedItem().toString().equals("Ass.City")) {
                                ((android.widget.TextView) txt_assesmentCity.getSelectedView()).setError("field required");

                            }
                            if (txt_reason.getText().toString().length() < 1){
                                txt_reason.setError("field required");
                            }
                        }

                }else{

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                    alertDialogBuilder.setTitle("TRAIN BOOKING");
                    alertDialogBuilder.setIcon(R.drawable.check_icon);
                    alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @SuppressLint("MissingPermission")
                        public void onClick(DialogInterface dialog, int id) {

                            new dotrainBooking().execute(Authorization,userType);

                        }
                    });
                    alertDialogBuilder.setNegativeButton(
                            "No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = alertDialogBuilder.create();
                    alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                    alert11.show();


                }

                    *//*{
                    AlertDialog.Builder ad=new AlertDialog.Builder(AddTrainBookingActivity.this);
                    ad.setTitle("COMPLETE train BOOKING");
                    ad.setMessage("Proceed To Complete Booking");
                    ad.setCancelable(false);
                    ad.setPositiveButton("proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            new dotrainBooking().execute(Authorization,userType);
                        }
                    });
                    ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    ad.show();
                }*//*
            }*/

        });







        if(isInternetConnected(context))
        {

            bill_idlist.add(new String("Billing ID"));
            bill_namelist.add(new String("Billing ID"));
            new getBillingEntities().execute(Authorization,userType,corporateId);
            billingAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, bill_namelist);
            txt_billing.setAdapter(billingAdapter);
        }
    }

    private void setData() {
        //Getting Login Time Spoc Details for Api Call
        Authorization="Token "+access_token;
        userType=loginpref.getString("usertype","n");
        userId=loginpref.getString("spoc_id", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocId=loginpref.getString("spoc_id", "n");
        groupId=loginpref.getString("group_id","n");
        subgroupId=loginpref.getString("subgroup_id","n");
        mpickupCity=picktrain_station_id_list.get(txt_pickupCity.getSelectedItemPosition());
        mdropCity=droptrain_station_id_list.get(txt_dropCity.getSelectedItemPosition());
        mpickupDate = txt_dateFrom.getText().toString();
        mpickupTime=txt_timeFrom.getText().toString();
        finalpickupDateTime = mpickupDate + " " + mpickupTime;
        mdropDate = txt_dateTo.getText().toString();
        mdropTime = txt_timeTo.getText().toString();
        finaldropDateTime=mdropDate+" "+mdropTime;
        mtrainType=train_type_id_list.get(txt_trainType.getSelectedItemPosition());
        massesmentCity=assessmentCityIdList.get(txt_assesmentCity.getSelectedItemPosition());
        massesmentCode=assessmentCodeIdList.get(txt_assesmentCode.getSelectedItemPosition());
        mpessengers = txt_pessengers.getText().toString();
        mbiilEntity=bill_idlist.get(txt_billing.getSelectedItemPosition());
        mprefTrain=txt_prefTrain.getText().toString();
        mreason = txt_reason.getText().toString();

    }

    static AlertDialog.Builder dialog;
    public  static boolean isInternetConnected(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[]=cm.getAllNetworkInfo();

        int i;
        for(i=0;i<networkInfo.length;++i){
            if(networkInfo[i].getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        Log.d("request code",requestCode+"");
        // Log.d("employeee", Arrays.toString(employeeIdList.toArray()));

        // Check that the result was from the autocomplete widget.
        if (requestCode == train_PICKUP_LOCATION) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);

                // Format the place's details and display them in the TextView.
                //txt_boardingPoint.setError(null);
                //txt_boardingPoint.setText(location);


            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        } else if (requestCode == train_DROP_LOCATION) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");

                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);

                // Format the place's details and display them in the TextView.
                //drop.setError(null);
                //drop.setText(location);


            }  else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }else if (requestCode == train_BOARDING_LOCATION) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");

                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);

                // Format the place's details and display them in the TextView.
                //txt_boardingPoint.setError(null);
                //txt_boardingPoint.setText(location);


            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }

        else if (requestCode ==EMPLOYEE_INFO){
            Log.d("employeee", Arrays.toString(employee_idlist.toArray()));

            if(resultCode == RESULT_OK){
                // passenger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background , 0);
                // passenger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background , 0);
                employee_idlist=data.getStringArrayListExtra("employee_id");
                getEmployee_idphno=data.getStringArrayListExtra("employee_phno");
                employee_id_value=data.getStringArrayListExtra("employeeIdValue");
                age_list = data.getStringArrayListExtra("age");
                male_female_list = data.getStringArrayListExtra("male_female");
                id_type_list = data.getStringArrayListExtra("id_type");
                id_no_list = data.getStringArrayListExtra("id_no");
                Log.d("employee_name", Arrays.toString(employee_idlist.toArray()));
                Log.d("employee_phno", Arrays.toString(getEmployee_idphno.toArray()));
                Log.d("employeeIdValue", Arrays.toString(employee_id_value.toArray()));
            }

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        String id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            id=new String();
            id=getArguments().getString("start");

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month , day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            if (id.equals("start")) {

                if (c.compareTo(Calendar.getInstance()) >= 0) {
                    month = month + 1;
                    start_date_send = year + "-" + month + "-" + day;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txt_dateFrom.setText(d + "-" + month + "-" + year);
                    } else {
                        txt_dateFrom.setText(day + "-" + month + "-" + year);
                    }
                    txt_dateFrom.setError(null);

                } else {
                    Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            }else if (id.equals("end")) {

                if (c.compareTo(Calendar.getInstance()) >= 0) {
                    month = month + 1;
                    end_date_send = year + "-" + month + "-" + day;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txt_dateTo.setText(d + "-" + month + "-" + year);
                    } else {
                        txt_dateTo.setText(day + "-" + month + "-" + year);
                    }
                    txt_dateTo.setError(null);

                } else {
                    Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        String id;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            id=new String();
            id=getArguments().getString("start");
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (id.equals("start")) {

                if (minute < 10 && hourOfDay < 10) {
                    txt_timeFrom.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    txt_timeFrom.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    txt_timeFrom.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    txt_timeFrom.setText(hourOfDay + ":" + minute + ":00");
                }
                txt_timeFrom.setError(null);
            }        else  if (id.equals("end")) {

                if (minute < 10 && hourOfDay < 10) {
                    txt_timeTo.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    txt_timeTo.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    txt_timeTo.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    txt_timeTo.setText(hourOfDay + ":" + minute + ":00");
                }
                txt_timeTo.setError(null);
            }
        }
    }
    public static class NoOfPassengers extends  DialogFragment{
        private ImageView plus,minus;
        private ImageView passenger1,passenger2,passenger3,passenger4,passenger5,passenger6;
        private TextView total_text;
        Button ok;
        RelativeLayout relativeLayout;
        public NoOfPassengers(){

        }

        public static NoOfPassengers newInstance(int no){
            NoOfPassengers frag = new NoOfPassengers();
            Bundle args = new Bundle();
            args.putInt("details",no);
            frag.setArguments(args);
            return  frag;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
            return inflater.inflate(R.layout.passenger_dialog_fragment,container);
        }

        @Override

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);
            plus = (ImageView) view.findViewById(R.id.plus_one);
            minus = (ImageView) view.findViewById(R.id.negative_one);
            relativeLayout =(RelativeLayout) view.findViewById(R.id.text_relative);
            passenger1 = (ImageView) view.findViewById(R.id.passenger_1);
            passenger2 = (ImageView) view.findViewById(R.id.passenger_2);
            passenger2.setVisibility(View.GONE);
            passenger3 = (ImageView) view.findViewById(R.id.passenger_3);
            passenger3.setVisibility(View.GONE);
            passenger4 = (ImageView) view.findViewById(R.id.passenger_4);
            passenger4.setVisibility(View.GONE);
            total_text = (TextView) view.findViewById(R.id.total_text);
            if(total==2){
                passenger2.setVisibility(View.VISIBLE);
            }else if(total ==3){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
            }else if(total ==4){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
            }            passenger5 = (ImageView) view.findViewById(R.id.passenger_5);
            passenger5.setVisibility(View.GONE);
            passenger6 = (ImageView) view.findViewById(R.id.passenger_6);
            passenger6.setVisibility(View.GONE);
            total_text.setText(total+"");
            ok = (Button) view.findViewById(R.id.ok);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==4){
                        Toasty.error(getActivity(),"Number of Train Passengers Must be  Four",Toast.LENGTH_SHORT).show();
                    }else {
                        total++;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger2.setVisibility(View.VISIBLE);
                        }if(total==3){
                            passenger3.setVisibility(View.VISIBLE);
                        }if (total==4){
                            passenger4.setVisibility(View.VISIBLE);
                        }

                    }
                    //Todo

                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==1){
                        Toasty.error(getActivity(),"Number of Train Passenger should be more then Zero",Toast.LENGTH_SHORT).show();
                    }else {
                        total--;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger3.setVisibility(View.GONE);
                        }if(total==3){
                            passenger4.setVisibility(View.GONE);
                        }if (total==1){
                            passenger2.setVisibility(View.GONE);
                        }
                    }

                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_pessengers.setError(null);
                    txt_pessengers.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(),EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




        }
    }

    protected  class dotrainBooking extends AsyncTask<String,Integer,String> {
        AddTrainBookingAPI addtrainBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {
            String Auth=params[0].toString();
            String usertype=params[1].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];

            Log.d("posting data",params.toString());


            addtrainBookingAPI = ConfigRetrofit.configRetrofit(AddTrainBookingAPI.class);

            if (hasAssCode.equals("0")){
                massesmentCode="0";
                massesmentCity="0";
            }

            //System Date Added From Mobile
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = df.format(c);
            booking_datetime = formattedDate;
            addtrainBookingAPI.addTrainBooking(Authorization,userType,userId,corporateId,spocId,groupId,subgroupId,
                    mpickupCity, mdropCity,booking_datetime,finalpickupDateTime,finaldropDateTime,mbiilEntity,mprefTrain,massesmentCode,massesmentCity,
                    mreason,mpessengers,employee_id_value,mtrainType).enqueue(new Callback<Responce>()
            {
                @Override
                public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {

                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(getApplicationContext(),"Booking Added :"+response.body().message,Toast.LENGTH_SHORT).show();

                        final Dialog dialog = new Dialog(AddTrainBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("Booking Status");
                        mDialogmsg.setText(""+response.body().message);

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        myes.setText("Ok");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getApplicationContext(), AddBusBookingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        mNo.setVisibility(View.INVISIBLE);
                        dialog.show();

                        /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Booking Status");
                        alertDialogBuilder.setMessage(""+response.body().message);
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent=new Intent(getApplicationContext(), AddTrainBookingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alert11 = alertDialogBuilder.create();
                        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                        alert11.show();*/

                    }else {
                        Toast.makeText(getApplicationContext(),"Booking Not Added :",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<AddTrainBookingActivity.Responce> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Booking Not Added :",Toast.LENGTH_SHORT).show();
                }
            });
            return assesmentvalue;

        }

        ProgressDialog pd;
        android.app.AlertDialog.Builder d;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("sucess","Done");
            pd = new ProgressDialog(AddTrainBookingActivity.this);
            pd.setMessage("completing booking");
            d=new android.app.AlertDialog.Builder(AddTrainBookingActivity.this);
            d.setTitle("train BOOKING");
            pd.show();
        }


        @Override
        protected void onPostExecute(String s)
        {

            pd.dismiss();

        }
    }


    public class Responce
    {

        @SerializedName("success")
        @Expose
        private Integer success;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getSuccess()
        {
            return success;
        }

        public void setSuccess(Integer success)
        {
            this.success = success;
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }

    String valu;
    protected class getBillingEntities extends AsyncTask<String,Integer,String> {

        String apiURL = APIurls.billingEntity;
        Integer id = 2;
        okhttp3.Response response;
        OkHttpClient cclient = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("corporate_id", params[2])
                    .build();
            Request.Builder request = new Request.Builder();

            request.url(apiURL)
                    .addHeader("Authorization", params[0])
                    .addHeader("usertype", params[1])
                    .post(formBody)
                    .build();

            try {
                response=cclient.newCall(request.build()).execute();
                valu = response.body().string();
                return valu;

            } catch (IOException e) {
                e.getMessage();
                Log.d("error", e.getMessage());
            }
            return valu;

        }

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Loading Billing Entities");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //response.body().close();
            if (s == null) {
            } else {
                Log.e("employee", s);

                SharedPreferences.Editor editor = billing_pref.edit();
                editor.putString("billing_info", s)
                        .commit();

                JSONObject json = (JSONObject) JSONValue.parse(s);
                JSONArray array = (JSONArray) json.get("Entitys");

                Iterator i = array.iterator();
                while (i.hasNext()) {
                    JSONObject obj = (JSONObject) i.next();
                    bill_namelist.add(new String(obj.get("entity_name").toString()));
                    bill_idlist.add(new String(obj.get("id").toString()));

                }


            }
            pd.dismiss();
        }


    }
}
