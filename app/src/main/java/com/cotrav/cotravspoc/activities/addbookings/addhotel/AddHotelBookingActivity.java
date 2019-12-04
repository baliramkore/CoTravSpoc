package com.cotrav.cotravspoc.activities.addbookings.addhotel;

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
import com.cotrav.cotravspoc.PlacesFieldSelector;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addflight.AddFlightBookingActivity;
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;
import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.models.hoteltypesmodel.HotelType;
import com.cotrav.cotravspoc.models.roomtypesmodel.RoomType;
import com.cotrav.cotravspoc.retrofit.APIurls;
import com.cotrav.cotravspoc.retrofit.AddHotelBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.viewmodels.AllCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCodeViewModel;
import com.cotrav.cotravspoc.viewmodels.HotelTypeViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.text.DateFormat;
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
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AddHotelBookingActivity extends AppCompatActivity {
    static SharedPreferences loginpref,billing_pref,spocpref;
    static SearchableSpinner pickupCity,hotelType,hotelType2,assCity,assCode,billing,roomType;
    static TextView preferedLocation,checkinDate,checkinTime,checkoutDate,checkoutTime,employyes;
    Button btnSubmit;
    EditText prefHotel,hotel_reason;
    static String access_token,userType,Authorization,corporateId;
    AllCitiesViewModel allCitiesViewModel;
    AssessmentCitiesViewModel assessmentCitiesViewModel;
    AssessmentCodeViewModel assessmentCodeViewModel;
    HotelTypeViewModel hotelTypeViewModel;
    ArrayAdapter<String> allcityadapter,billingAdapter,assesmentadapter,assesmentcodeAdapter,hotelTypeAdapter,hotelType2Adapter,roomTypeAdapter,roomTypeAdapter2;
    String city,finalAddress;
    static int total=1,EMPLOYEE_INFO=100;
    private static final int PREFAREA = 1;

    ArrayList<String> employee_idlist;
    ArrayList<String> employee_id_value;
    ArrayList<String> getEmployee_idphno;
    ArrayList<String> age_list;
    ArrayList<String> male_female_list;
    ArrayList<String> id_type_list;
    ArrayList<String> id_no_list;
    double lat;
    double lng;
    static Context context;
    int HOTEL_PREF_LOCATION=1;
    ArrayList<String> pickup_CityList,pickup_CityIdList,drop_CityList,drop_CityIdList,bill_namelist,bill_idlist,
            assessmentCityList,assessmentCityIdList,assessmentCodeList,assessmentCodeIdList,
            hotelTypeList,hotelTypeIDList,
            hotelTypeList2,hotelTypeIDList2,
            roomTypeList,roomTypeIDList;
    String hasAssCode,subgroupId,groupId,spocId,userId,mpickupCity,mprefLocation,mcheckinDate,mcheckinTime,mcheckoutDate,
            mcheckoutTime,mbucket1,mbucket2,massesmentCity, massesmentCode,mbiilEntity,mroomType,memployees,mreason;
    private String booking_datetime;
    private String finalcheckindate,finalcheckoutdate;
    private Toolbar mtoolbar;

    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    LinearLayout layoutAssisment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel_booking);

        mtoolbar=findViewById(R.id.toolbar);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        mtoolbar.setTitle("Add Hotel Booking");
        mtoolbar.setNavigationIcon(R.drawable.test_back);
        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        context=AddHotelBookingActivity.this;
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        userType = loginpref.getString("usertype", "n");
        billing_pref= getSharedPreferences("billing_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        Authorization="Token "+loginpref.getString("access_token", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocpref=getSharedPreferences("spoc_info",MODE_PRIVATE);
        hasAssCode=spocpref.getString("has_assisment_code","n");

        //Getting UI Element From XML
        pickupCity=(SearchableSpinner)findViewById(R.id.hotel_spinner_city);
        hotelType=(SearchableSpinner)findViewById(R.id.hotel_hoteltype1);
        hotelType2=(SearchableSpinner)findViewById(R.id.hotel_hoteltype2);
        assCity=(SearchableSpinner)findViewById(R.id.hotel_assesmentCity);
        assCode=(SearchableSpinner)findViewById(R.id.hotel_assesmentCode);
        billing=(SearchableSpinner)findViewById(R.id.hotel_billing);

        preferedLocation=(TextView)findViewById(R.id.hotel_preferedLocation);
        checkinDate=(TextView)findViewById(R.id.hotel_checkindate);
        checkinTime=(TextView)findViewById(R.id.hotel_checkinTime);
        checkoutDate=(TextView)findViewById(R.id.hotel_checkoutDate);
        checkoutTime=(TextView)findViewById(R.id.hotel_checkoutTime);
        employyes=(TextView)findViewById(R.id.hotel_employees);
        roomType=(SearchableSpinner) findViewById(R.id.hotel_roomType);
        hotel_reason=(EditText)findViewById(R.id.hotel_reason);
        layoutAssisment=(LinearLayout)findViewById(R.id.layout_assisment);
        if (hasAssCode.equals("0"))
        {
            layoutAssisment.setVisibility(View.GONE);
        }

        btnSubmit=(Button)findViewById(R.id.btn_submit);

        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setData();

                if (isInternetConnected(AddHotelBookingActivity.this))
                {
                    if (
                            (roomType.getSelectedItemPosition()==0)
                          ||(pickupCity.getSelectedItemPosition()==0)
                          || (hotelType.getSelectedItemPosition()==0)
                          || (hotelType2.getSelectedItemPosition()==0)
                          || (billing.getSelectedItemPosition()==0)
                          || (hotel_reason.getText().toString().length() < 1)
                          ||preferedLocation.getText().toString().equals("Preffered Location")
                          ||checkinDate.getText().toString().equals("Checkin Date")
                          ||checkinTime.getText().toString().equals("checkin Time")
                          ||checkoutDate.getText().toString().equals("Checkout Date")
                          ||checkoutTime.getText().toString().equals("Checkout Time")
                          ||employyes.getText().toString().equals("Passenger")



                    ) {

                        if (preferedLocation.getText().toString().equals("Preffered Location")){
                            preferedLocation.setError("Preffered Location");
                        }

                        if (checkinDate.getText().toString().equals("Checkin Date")) {
                            checkinDate.setError("Checkin Date");
                        }
                        if (checkinTime.getText().toString().equals("checkin Time")) {
                            checkinTime.setError("checkin Time");
                        }

                        if (checkoutDate.getText().toString().equals("Checkout Date")) {
                            checkoutDate.setError("Checkout Date");

                        }
                        if (checkoutTime.getText().toString().equals("Checkout Time")) {
                            checkoutTime.setError("Checkout Time");
                        }

                        if (employyes.getText().toString().equals("Select Employees")) {
                            employyes.setError("Select Employees");
                        }
                        if (employee_idlist.size() == 0) {
                            employyes.setError("field required");
                            Toast.makeText(AddHotelBookingActivity.this, "please choose Employee", Toast.LENGTH_SHORT).show();
                        }

                        if (billing.getSelectedItem().toString().equals("Billing ID")) {

                            ((android.widget.TextView) billing.getSelectedView()).setError("field required");

                        }

                        if (pickupCity.getSelectedItem().toString().equals("Select City")) {

                            ((android.widget.TextView) pickupCity.getSelectedView()).setError("field required");

                        }

                        if (roomType.getSelectedItemPosition() == 0) {

                            ((android.widget.TextView) roomType.getSelectedView()).setError("field required");

                        }
                        if (hotelType.getSelectedItem().toString().equals("Priority 1"))
                        {
                            ((android.widget.TextView) hotelType.getSelectedView()).setError("field required");
                        }
                        if (hotelType2.getSelectedItem().toString().equals("Priority 2"))
                        {
                            ((android.widget.TextView) hotelType2.getSelectedView()).setError("field required");
                        }

                        if (hasAssCode.equals("0")==false)
                        {
                            if (assCode.getSelectedItem().toString().equals("Ass.Code"))
                            {
                                ((android.widget.TextView) assCode.getSelectedView()).setError("field required");
                            }

                            if (assCity.getSelectedItem().toString().equals("Ass.City"))
                            {
                                ((android.widget.TextView) assCity.getSelectedView()).setError("field required");

                            }
                        }


                        if (hotel_reason.getText().toString().length() < 1){
                            hotel_reason.setError("field required");
                        }

                    } else {


                        final Dialog dialog = new Dialog(AddHotelBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("HOTEL BOOKING");
                        mDialogmsg.setText("Please Press Yes to Complete booking");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        myes.setText("Yes");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new doHotelBooking().execute(Authorization,userType);
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
                   /*     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
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
                        alert11.show();*/

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
        });


        employyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getSupportFragmentManager(),"no of radio_passenger");
            }
        });

        checkinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });
        checkinTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");
            }
        });
        checkoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        checkoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","end");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "endtime");
            }
        });

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
        hotelTypeList=new ArrayList<>();
        hotelTypeIDList=new ArrayList<>();
        roomTypeList=new ArrayList<>();
        roomTypeIDList=new ArrayList<>();
        hotelTypeIDList2=new ArrayList<>();
        hotelTypeList2=new ArrayList<>();
        employee_idlist = new ArrayList<String>();
        getEmployee_idphno = new ArrayList<String>();
        employee_id_value = new ArrayList<>();
        age_list = new ArrayList<String>();
        male_female_list = new ArrayList<String>();
        id_type_list = new ArrayList<String>();
        id_no_list = new ArrayList<String>();
        //hasSingleEmployee = loginpref.getString("has_single_employee", null);



        allCitiesViewModel= ViewModelProviders.of(this).get(AllCitiesViewModel.class);
        allCitiesViewModel.initAllCitiesViewModel(Authorization,userType);
        assessmentCitiesViewModel= ViewModelProviders.of(this).get(AssessmentCitiesViewModel.class);
        assessmentCitiesViewModel.initViewModel(Authorization, userType,corporateId);
        assessmentCodeViewModel= ViewModelProviders.of(this).get(AssessmentCodeViewModel.class);
        assessmentCodeViewModel.initViewModel(Authorization, userType,corporateId);

        hotelTypeViewModel= ViewModelProviders.of(this).get(HotelTypeViewModel.class);
        hotelTypeViewModel.initHotelTypeViewModel(Authorization,userType);
        hotelTypeViewModel.initRoomTypeViewModel(Authorization,userType);


        hotelTypeList.add(new String("Priority 1"));
        hotelTypeIDList.add(new String("Priority 1"));
        hotelTypeList2.add(new String("Priority 2"));
        hotelTypeIDList2.add(new String("Priority 2"));
        hotelTypeAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,hotelTypeList);
        hotelType2Adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,hotelTypeList2);
        hotelType.setAdapter(hotelTypeAdapter);
        hotelType2.setAdapter(hotelType2Adapter);
        hotelTypeViewModel.getRoomTypes(Authorization,userType).observe(this, new Observer<List<RoomType>>() {
            @Override
            public void onChanged(List<RoomType> hoteltypes) {

                if (hoteltypes!=null && hoteltypes.size()>0){
                    hotelTypeList.clear();
                    hotelTypeIDList.clear();
                    hotelTypeList.add(new String("Priority 1"));
                    hotelTypeIDList.add(new String("Priority 1"));
                    hotelTypeList2.clear();
                    hotelTypeIDList2.clear();
                    hotelTypeList2.add(new String("Priority 2"));
                    hotelTypeIDList2.add(new String("Priority 2"));


                    for (int i=0;i<hoteltypes.size();i++){
                        hotelTypeList.add(hoteltypes.get(i).getName());
                        hotelTypeIDList.add(String.valueOf(hoteltypes.get(i).getId()));
                        hotelTypeList2.add(hoteltypes.get(i).getName());
                        hotelTypeIDList2.add(String.valueOf(hoteltypes.get(i).getId()));

                        //hotelTypeList2.add()

                    }
                    hotelType.setAdapter(hotelTypeAdapter);
                }

                hotelType2.setAdapter(hotelType2Adapter);
                Log.d("Buckets", GsonStringConvertor.gsonToString(hoteltypes));
            }
        });

        roomTypeList.add(new String(" Room Type "));
        roomTypeIDList.add(new String(" Room Type "));
        roomTypeAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,roomTypeList);
        roomType.setAdapter(roomTypeAdapter);
        hotelTypeViewModel.getHotelTypes(Authorization,userType).observe(this, new Observer<List<HotelType>>() {
            @Override
            public void onChanged(List<HotelType> hoteltypes) {

                if (hoteltypes!=null && hoteltypes.size()>0){
                    roomTypeList.clear();
                    roomTypeIDList.clear();
                    roomTypeList.add(new String(" Room Type "));
                    roomTypeIDList.add(new String(" Room Type "));
                    for (int i=0;i<hoteltypes.size();i++){
                        roomTypeList.add(hoteltypes.get(i).getName());
                        roomTypeIDList.add(hoteltypes.get(i).getId());

                    }
                }

                //Log.d("Room Type", GsonStringConvertor.gsonToString(roomType));
            }
        });






        assessmentCityList.add(new String("Ass.City"));
        assessmentCityIdList.add(new String("Ass.City"));
        assesmentadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,assessmentCityList);
        assCity.setAdapter(assesmentadapter);
        assessmentCitiesViewModel.getLiveAssessmentCityList(Authorization,userType,corporateId).observe(this, new Observer<List<AssCity>>() {
            @Override
            public void onChanged(List<AssCity> assCities) {

                if (assCities!=null && assCities.size()>0){
                    assessmentCityList.clear();
                    assessmentCityIdList.clear();
                    assessmentCityList.add(new String("Ass.City"));
                    assessmentCityIdList.add(new String("Ass.City"));
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
        assCode.setAdapter(assesmentcodeAdapter);
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



        allCitiesViewModel.getCitiesLiveData(Authorization, userType);
        pickup_CityList.add(new String("Select City"));
        pickup_CityIdList.add(new String("Select City"));
        allcityadapter=new ArrayAdapter<String>(this,R.layout.spinner_item,pickup_CityList);
        pickupCity.setAdapter(allcityadapter);
        //txt_pickupLocation.setAdapter(placeadapter);
        allCitiesViewModel.getCitiesLiveData(Authorization, userType).
                observe(this, new Observer<List<City>>()
                {
                    @Override
                    public void onChanged(List<City> cities)
                    {
                        if (cities!=null && cities.size()>0)
                        {
                            pickup_CityList.clear();
                            pickup_CityIdList.clear();
                            pickup_CityList.add(new String("Select City"));
                            pickup_CityIdList.add(new String("Select City"));
                            for (int i=0;i<cities.size();i++)
                            {
                                pickup_CityList.add(cities.get(i).getCityName());
                                pickup_CityIdList.add(String.valueOf(cities.get(i).getId()));
                            }
                            allcityadapter.notifyDataSetChanged();
                        }


                    }
                });

        if(isInternetConnected(getBaseContext()))
        {

            bill_idlist.add(new String("Billing ID"));
            bill_namelist.add(new String("Billing ID"));
            new getBillingEntities().execute(Authorization,userType,corporateId);
            billingAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bill_namelist);
            billing.setAdapter(billingAdapter);
        }

        preferedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .build(context);
                startActivityForResult(autocompleteIntent, PREFAREA);
            }
        });    }

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

    private void setData() {

        userId=loginpref.getString("spoc_id", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocId=loginpref.getString("spoc_id", "n");
        groupId=loginpref.getString("group_id","n");
        subgroupId=loginpref.getString("subgroup_id","n");

        //storing values in string from UI
        mpickupCity=pickup_CityIdList.get(pickupCity.getSelectedItemPosition());
        mprefLocation=preferedLocation.getText().toString();
        mcheckinDate=checkinDate.getText().toString();
        mcheckinTime=checkinTime.getText().toString();
        finalcheckindate=mcheckinDate+" "+mcheckinTime;
        mcheckoutDate=checkoutDate.getText().toString();
        mcheckoutTime=checkoutTime.getText().toString();
        finalcheckoutdate=mcheckoutDate+" "+mcheckoutTime;
        mbucket1=roomTypeIDList.get(hotelType.getSelectedItemPosition());
        mbucket2=roomTypeIDList.get(hotelType2.getSelectedItemPosition());
        massesmentCity=pickup_CityIdList.get(assCity.getSelectedItemPosition());
        massesmentCode=assessmentCodeIdList.get(assCode.getSelectedItemPosition());
        mbiilEntity=bill_idlist.get(billing.getSelectedItemPosition());
        mroomType=roomTypeIDList.get(roomType.getSelectedItemPosition());
        memployees=employyes.getText().toString();
        mreason = hotel_reason.getText().toString();


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
        if (requestCode == PREFAREA) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.
                preferedLocation.setError(null);
                preferedLocation.setText(place.getAddress());

            }
        }else if (requestCode ==EMPLOYEE_INFO){
            Log.d("employeee", Arrays.toString(employee_idlist.toArray()));

            if(resultCode == RESULT_OK)
            {
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
                    //start_date_send = year + "-" + month + "-" + day;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        checkinDate.setText(d + "-" + month + "-" + year);
                    } else {
                        checkinDate.setText(day + "-" + month + "-" + year);
                    }
                    checkinDate.setError(null);

                } else {
                    Toast.makeText(getContext(),"past dates are not allowed",Toast.LENGTH_SHORT).show();
                    // Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            }else if (id.equals("end")) {

                if (c.compareTo(Calendar.getInstance()) >= 0) {
                    month = month + 1;
                    //end_date_send = year + "-" + month + "-" + day;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        checkoutDate.setText(day + "-" + month + "-" + year);
                    } else {
                        checkoutDate.setText(day + "-" + month + "-" + year);
                    }
                    checkoutDate.setError(null);

                } else {
                    Toast.makeText(getContext(),"past dates are not allowed",Toast.LENGTH_SHORT).show();
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
                    checkinTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    checkinTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    checkinTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    checkinTime.setText(hourOfDay + ":" + minute + ":00");
                }
                checkinTime.setError(null);
            }        else  if (id.equals("end")) {

                if (minute < 10 && hourOfDay < 10) {
                    checkoutTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    checkoutTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    checkoutTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    checkoutTime.setText(hourOfDay + ":" + minute + ":00");
                }
                checkoutTime.setError(null);
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
                        Toasty.error(getActivity(),"Number of Bus Passengers Must be  Four",Toast.LENGTH_SHORT).show();
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
                        Toasty.error(getActivity(),"Number of Bus Passenger should be more then Zero",Toast.LENGTH_SHORT).show();
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
                    employyes.setError(null);
                    employyes.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(),EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




        }
    }
    protected  class doHotelBooking extends AsyncTask<String,Integer,String> {
        AddHotelBookingAPI addHotelBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {
            String Auth=params[0].toString();
            String usertype=params[1].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];

            Log.d("posting data",params.toString());



            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            addHotelBookingAPI = ConfigRetrofit.configRetrofit(AddHotelBookingAPI.class);

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

            addHotelBookingAPI.addHotelBooking(Authorization,userType,mpickupCity,mpickupCity,mprefLocation,
                    finalcheckindate, finalcheckoutdate,mbucket1,mbucket2,mroomType,"Taj Hotel",booking_datetime,
                    massesmentCode,massesmentCity,memployees,groupId,subgroupId,spocId,corporateId,mbiilEntity
                    ,mreason,userId,userType,employee_id_value
            ).enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response)
                {

                    assesmentvalue=response.body().toString();

                    Toast.makeText(getApplicationContext(),"Booking Added :"+response.body().message,Toast.LENGTH_SHORT).show();
                    final Dialog dialog = new Dialog(AddHotelBookingActivity.this);
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

                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {

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
            pd = new ProgressDialog(AddHotelBookingActivity.this);
            pd.setMessage("completing booking");
            d=new android.app.AlertDialog.Builder(AddHotelBookingActivity.this);
            d.setTitle("BUS BOOKING");
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

}
