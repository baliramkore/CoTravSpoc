package com.cotrav.cotravspoc.fragments;

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
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.PlacesFieldSelector;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addflight.AddFlightBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addtaxi.AddTaxiBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addtrain.AddTrainBookingActivity;
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;
import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.models.localpackagesmodel.Packages;
import com.cotrav.cotravspoc.models.localpackagesmodel.PackagesResponse;
import com.cotrav.cotravspoc.models.taxitypemodel.TaxiType;
import com.cotrav.cotravspoc.retrofit.APIurls;
import com.cotrav.cotravspoc.retrofit.AddTaxiBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.GetPackagesAPI;
import com.cotrav.cotravspoc.viewmodels.AllCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCodeViewModel;
import com.cotrav.cotravspoc.viewmodels.TaxiTypeViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rey.material.widget.Spinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocalTaxi extends Fragment
{
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1,EMPLOYEE_INFO=100;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    static SharedPreferences loginpref,spocpref;
    SharedPreferences billing_pref;
    static String Authorization="";
    static String usertype="4";
    String finalDateAndTime="";
    static packageAdapter packageadapters;
    static ArrayAdapter<String> pacakageadapter;
    String local;
    ArrayAdapter<String> assessmentCityAdapter;
    ArrayAdapter<String> billingAdapter;
    AllCitiesViewModel allCitiesViewModel;
    TaxiTypeViewModel taxiTypeViewModel;
    ArrayList<String> placelist;
    ArrayList<String> places_idlist;
    ArrayList<String> taxi_type_list;
    ArrayList<String> taxi_type_id_list;
    ArrayList<String> employee_idlist;
    ArrayList<String> getEmployee_idphno;
    String[] employee_id_value;
    ArrayAdapter<String> allcityadapter,placeadapter,assesmentadapter,assesmentcodeAdapter,taxiadapter;
    ArrayList<String> allCitiesList,assessmentCityList, assessmentCodeList,assessmentCityId,assessmentCodeId;
    static SearchableSpinner pickupCitySpinner,taxitypeSpinner,txt_localAssCity,txt_localAssCode,txt_localBillingEntity;
    static TextView txt_localPickupDate,txt_localPickupTime,txt_pickupLocation,txt_dropLocation,txt_localPassengers,txt_localPackages,txt_localReason;
    AssessmentCitiesViewModel assessmentCitiesViewModel;
    AssessmentCodeViewModel assessmentCodeViewModel;
    static String hasAssCode,access_token,userType,userId,spocId,groupId,subgroupId,mpickupCity,mdropCity,mtaxiType,mpickupLocation,mdropLocation,mpickupDate,mpickupTime,mpessengers,mpackage,massCity,massCode,mbiilEntity,mreason;
    Button btnSave;
    static  String start_date_send,end_date_send,city;
    static int clickCount= 0;
    static int total=1;
    static int max =4;
    static  String pri1 = "AC Seater",pri2 ="AC Seater",pri3 ="AC Seater";
    double lat;
    double lng;
    private String corporateId;
    private ArrayList<String> pacakage_idlist,pacakagelist = new ArrayList<>();
    HashMap<String,String> packagehashMap;
    ArrayList<String> bill_namelist,bill_idlist;
    static Context context;
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    LinearLayout layoutAssisment;
    ProgressDialog progressDialog;

    public FragmentLocalTaxi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_fragment_local_taxi, container, false);
        context=getActivity();
        loginpref=getActivity().getSharedPreferences("login_info",MODE_PRIVATE);
        billing_pref= getActivity().getSharedPreferences("billing_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        //gathering login time spoc details for api call
        loginpref = getActivity().getSharedPreferences("login_info", MODE_PRIVATE);
        Authorization="Token "+loginpref.getString("access_token", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        local = loginpref.getString("is_local", null);
        spocpref=getActivity().getSharedPreferences("spoc_info",MODE_PRIVATE);
        hasAssCode=spocpref.getString("has_assessment_codes","n");

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        placeadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,placelist);
        pickupCitySpinner = (SearchableSpinner) view.findViewById(R.id.local_spinner_city);
        taxitypeSpinner=(SearchableSpinner)view.findViewById(R.id.local_spinnerTaxiType);
        txt_pickupLocation = (TextView) view.findViewById(R.id.local_pickupLocation);
        txt_dropLocation = (TextView) view.findViewById(R.id.local_dropLocation);
        txt_localPickupDate=(TextView)view.findViewById(R.id.local_datePickup);
        txt_localPickupTime=(TextView)view.findViewById(R.id.local_timePickup);
        txt_localPassengers=(TextView)view.findViewById(R.id.local_pessangers);
        txt_localPackages=(TextView)view.findViewById(R.id.local_package);
        txt_localAssCity=(SearchableSpinner)view.findViewById(R.id.local_assesmentCity);
        txt_localAssCode=(SearchableSpinner)view.findViewById(R.id.local_assesmentCode);
        txt_localBillingEntity=(SearchableSpinner)view.findViewById(R.id.local_billingEntity);
        txt_localReason=(TextView)view.findViewById(R.id.local_reason);
        layoutAssisment=(LinearLayout)view.findViewById(R.id.layout_assisment);
        if (hasAssCode.equals("0"))
        {
            layoutAssisment.setVisibility(View.GONE);
        }
        btnSave=(Button) view.findViewById(R.id.btn_submit);



        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

        txt_localPassengers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(taxitypeSpinner.getSelectedItem().toString().equals("SUV")) {
                    Log.e("click", "SUV");
                    // noOfPassenger.add(new String("5"));
                    //noOfPassenger.add(new String("6"));
                    max = 6;
                }else{
                    max=4;
                }
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getChildFragmentManager(),"no of radio_passenger");

            }
        });






        //txt_localPackages  = (TextView) view.findViewById(R.id.packages);
        packageadapters = new packageAdapter(getActivity(),pacakagelist);
        pacakageadapter=new ArrayAdapter<String>(getActivity(),R.layout.packages_adapter_layout,pacakagelist);
        txt_localPackages.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new packageDialog();
                //app crash while opening this dialog
                dialogFragment.setCancelable(false);
                dialogFragment.show(getChildFragmentManager(),"packages");
            }
        });



        pickupCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(pickupCitySpinner.getSelectedItem().toString().equals("Select City")){
                    TextView errorText = (TextView)pickupCitySpinner.getSelectedView();

                }else{
                    city=pickupCitySpinner.getSelectedItem().toString();
                    gettingLatLng(city);
                    taxitypeSpinner.setSelection(0);

                }
                if(pickupCitySpinner.getSelectedItemPosition() != 0) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


      /*  txt_pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickup = new Intent(getActivity(), GetMapLocationActivity.class);
                pickup.putExtra("calledby","local");
                pickup.putExtra("city", city);
                //drop.putExtra("address",finalAddress);
                pickup.putExtra("lat",lat);
                pickup.putExtra("lng",lng);
                startActivityForResult(pickup, PICKUP_CODE_AUTOCOMPLETE);

            }
        });
        txt_dropLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickup = new Intent(getActivity(), GetMapLocationActivity.class);
                pickup.putExtra("calledby","local");
                pickup.putExtra("city", city);
                //drop.putExtra("address",finalAddress);
                pickup.putExtra("lat",lat);
                pickup.putExtra("lng",lng);
                startActivityForResult(pickup, DROP_CODE_AUTOCOMPLETE);
            }
        });*/
        txt_pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .build(context);
                startActivityForResult(autocompleteIntent, PICKUP_CODE_AUTOCOMPLETE);
            }
        });
        txt_dropLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .build(context);
                startActivityForResult(autocompleteIntent, DROP_CODE_AUTOCOMPLETE);
            }
        });

        txt_localPickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getFragmentManager(), "datePicker");

            }
        });
        txt_localPickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getFragmentManager(), "starttime");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
           setData();

                if (isInternetConnected(getContext())) {
                    if (    (taxitypeSpinner.getSelectedItemPosition()==0)
                            ||(pickupCitySpinner.getSelectedItemPosition()==0)
                            ||(txt_localBillingEntity.getSelectedItemPosition()==0)
                            ||txt_localReason.getText().toString().length() < 1
                            ||txt_localPickupDate.getText().toString().equals("Pickup Date")
                            ||txt_localPickupTime.getText().toString().equals("Pickup Time")
                            ||txt_localPackages.getText().toString().equals("Select Packages")
                            ||txt_pickupLocation.getText().toString().equals("Pickup Location")
                            ||txt_dropLocation.getText().toString().equals("Drop Location")
                            ||txt_localPassengers.getText().toString().equals("Passengers")
                    ) {

                            Toast.makeText(getActivity(), "Please Fill all Fields", Toast.LENGTH_SHORT).show();

                        if (txt_localReason.getText().toString().length() < 1){
                            txt_localReason.setError("field required");
                        }
                        if (txt_localPackages.getText().toString().equals("Select Packages")){
                            txt_localPackages.setError("field required");
                        }
                        if (txt_pickupLocation.getText().toString().equals("Pickup Location")){
                            txt_pickupLocation.setError("field required");
                        }
                        if (txt_dropLocation.getText().toString().equals("Drop Location")){
                            txt_dropLocation.setError("field required");
                        }
                        if (txt_localPickupDate.getText().toString().equals("Pickup Date")){
                            txt_localPickupDate.setError("field required");
                        }
                        if (txt_localPickupTime.getText().toString().equals("Pickup Time")){
                            txt_localPickupTime.setError("field required");
                        }

                        if (txt_localPickupTime.getText().toString().equals("Pickup Time")){
                            txt_localPickupTime.setError("field required");
                        }
                        if (txt_localPassengers.getText().toString().equals("Passengers")){
                            txt_localPassengers.setError("field required");
                        }
                        if (employee_idlist.size() == 0) {
                            txt_localPassengers.setError("field required");
                            Toast.makeText(getActivity(), "please choose Employee ", Toast.LENGTH_SHORT).show();
                        }



                        if (pickupCitySpinner.getSelectedItem().toString().equals("Select City")){
                            ((android.widget.TextView) pickupCitySpinner.getSelectedView()).setError("field required");
                        }
                        if (taxitypeSpinner.getSelectedItem().toString().equals("Taxi Type")){
                            ((android.widget.TextView) taxitypeSpinner.getSelectedView()).setError("field required");
                        }
                        if (txt_localBillingEntity.getSelectedItem().toString().equals("Billing ID")){
                            ((android.widget.TextView) txt_localBillingEntity.getSelectedView()).setError("field required");
                        }
                        if (hasAssCode.equals("0")==false)
                        {
                            if (txt_localAssCode.getSelectedItem().toString().equals("Ass.Code"))
                            {
                                ((android.widget.TextView) txt_localAssCode.getSelectedView()).setError("field required");
                            }

                            if (txt_localAssCity.getSelectedItem().toString().equals("Ass.City"))
                            {
                                ((android.widget.TextView) txt_localAssCity.getSelectedView()).setError("field required");

                            }
                        }

                    } else {


                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("BUS BOOKING");
                        mDialogmsg.setText("Please Press Yes to Complete booking");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        myes.setText("Yes");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mpackage = packagehashMap.get(txt_localPackages.getText().toString());
                                new doLocalTaxiBooking().execute(
                                        Authorization, userType, corporateId, userId, corporateId, "1", spocId, groupId,
                                        subgroupId, "2", mpickupCity, mpickupLocation, mdropLocation,finalDateAndTime, mtaxiType, mpackage, massCode, massCity, mreason);
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
                        alertDialogBuilder.setTitle("LOCAL TAXI BOOKING");
                        alertDialogBuilder.setIcon(R.drawable.check_icon);
                        alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {


                                mpackage = packagehashMap.get(txt_localPackages.getText().toString());
                                new doLocalTaxiBooking().execute(
                                        Authorization, userType, corporateId, userId, corporateId, "1", spocId, groupId,
                                        subgroupId, "2", mpickupCity, mpickupLocation, mdropLocation,finalDateAndTime, mtaxiType, mpackage, massCode, massCity, mreason);
                                dialog.dismiss();

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

            }
        });

        allCitiesViewModel= ViewModelProviders.of(getActivity()).get(AllCitiesViewModel.class);
        allCitiesViewModel.initAllCitiesViewModel(Authorization,usertype);
        assessmentCitiesViewModel= ViewModelProviders.of(getActivity()).get(AssessmentCitiesViewModel.class);
        assessmentCitiesViewModel.initViewModel(Authorization, usertype,corporateId);
        assessmentCodeViewModel= ViewModelProviders.of(getActivity()).get(AssessmentCodeViewModel.class);
        assessmentCodeViewModel.initViewModel(Authorization, usertype,corporateId);
        taxiTypeViewModel= ViewModelProviders.of(getActivity()).get(TaxiTypeViewModel.class);
        taxiTypeViewModel.initViewModel(Authorization,usertype,corporateId);





        List<String> cities = new ArrayList<String>();
        taxi_type_list=new ArrayList<>();
        pacakagelist=new ArrayList<>();
        pacakage_idlist=new ArrayList<>();
        assessmentCityList=new ArrayList<>();
        assessmentCityId=new ArrayList<>();
        assessmentCodeList=new ArrayList<>();
        assessmentCodeId=new ArrayList<>();
        taxi_type_id_list=new ArrayList<>();
        packagehashMap=new HashMap<>();
        placelist=new ArrayList<>();
        places_idlist=new ArrayList<>();
        bill_namelist=new ArrayList<>();
        bill_idlist=new ArrayList<>();
        employee_idlist=new ArrayList<>();
        getEmployee_idphno=new ArrayList<>();



        allCitiesViewModel.getCitiesLiveData(Authorization, usertype);
        placelist.add(new String("Select City"));
        allcityadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,placelist);
        pickupCitySpinner.setAdapter(allcityadapter);
        //txt_pickupLocation.setAdapter(placeadapter);
        allCitiesViewModel.getCitiesLiveData(Authorization, usertype).
                observe(this, new Observer<List<City>>()
                {
                    @Override
                    public void onChanged(List<City> cities)
                    {
                        if (cities!=null && cities.size()>0)
                        {
                            placelist.clear();
                            places_idlist.clear();
                            placelist.add(new String("Select City"));
                            places_idlist.add(new String("Select City"));
                            for (int i=0;i<cities.size();i++)
                            {
                                placelist.add(cities.get(i).getCityName());
                                places_idlist.add(String.valueOf(cities.get(i).getId()));
                            }
                            placeadapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }


                    }
                });



        taxi_type_list.add(new String("Taxi Type"));
        taxiadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,taxi_type_list);
        taxitypeSpinner.setAdapter(taxiadapter);
        taxiTypeViewModel.getTaxiTypes().observe(this, new Observer<List<TaxiType>>() {
            @Override
            public void onChanged(@Nullable List<TaxiType> taxiTypes) {
                Log.d("TaxiType",GsonStringConvertor.gsonToString(taxiTypes)+" taxi");
                taxi_type_list.clear();
                taxi_type_id_list.clear();
                taxi_type_list.add("Taxi Type");
                taxi_type_id_list.add("Taxi Type");
                if (taxiTypes!=null && taxiTypes.size()>0){
                    for (int i=0;i<taxiTypes.size();i++){
                        taxi_type_list.add(taxiTypes.get(i).getName());
                        taxi_type_id_list.add(String.valueOf(taxiTypes.get(i).getId()));
                    }
                    taxiadapter.notifyDataSetChanged();
                }
                Log.d("TaxiTypes", GsonStringConvertor.gsonToString(taxiTypes));
            }
        });

        assessmentCityList.add(new String("Ass.City"));
        assessmentCityId.add(new String("Ass.City"));
        assesmentadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,assessmentCityList);
        txt_localAssCity.setAdapter(assesmentadapter);
        assessmentCitiesViewModel.getLiveAssessmentCityList(Authorization,usertype,corporateId).observe(this, new Observer<List<AssCity>>() {
            @Override
            public void onChanged(List<AssCity> assCities) {
                assessmentCityList.clear();
                assessmentCityId.clear();
                assessmentCityList.add("Ass.City");
                assessmentCityId.add("Ass.City");
                if (assCities!=null && assCities.size()>0){
                    for (int i=0;i<assCities.size();i++){
                        //assesmentadapter.add(assCities.get(i).getCityName());
                        assessmentCityList.add(assCities.get(i).getCityName());
                        assessmentCityId.add(String.valueOf(assCities.get(i).getId()));

                    }
                }
                Log.d("Ass.City", GsonStringConvertor.gsonToString(assCities));
            }
        });

        assessmentCodeList.add(new String("Ass. Code"));
        assessmentCodeId.add(new String("Ass. Code"));
        assesmentcodeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,assessmentCodeList);
        txt_localAssCode.setAdapter(assesmentcodeAdapter);
        assessmentCodeViewModel.getLiveAssessmentCodeList(Authorization,usertype,corporateId).observe(this, new Observer<List<AssCode>>() {
            @Override
            public void onChanged(List<AssCode> assCodes) {

                if (assCodes!=null && assCodes.size()>0){
                    assessmentCodeList.clear();
                    assessmentCodeId.clear();
                    assessmentCodeList.add("Ass.Code");
                    assessmentCodeId.add("Ass.Code");
                    for (int i=0;i<assCodes.size();i++){
                        //assesmentcodeAdapter.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeList.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeId.add(String.valueOf(assCodes.get(i).getId()));

                    }
                }
                Log.d("Ass.Code", GsonStringConvertor.gsonToString(assCodes));
            }
        });

        taxitypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)

            {
                if(taxitypeSpinner.getSelectedItem().toString().equals("Taxi Type")){

                    TextView errorText = (TextView)taxitypeSpinner.getSelectedView();

                }

                pacakagelist.clear();
                pacakage_idlist.clear();
                packagehashMap.clear();
                pacakagelist.add("No Packages");
                pacakage_idlist.add("No Packages");
                txt_localPackages.setText("Select Packages");
                if(pickupCitySpinner.getSelectedItemPosition() != 0 && taxitypeSpinner.getSelectedItemPosition()!=0)
                {
                    String cityid = places_idlist.get(pickupCitySpinner.getSelectedItemPosition());
                    String taxitypeid = taxi_type_id_list.get(taxitypeSpinner.getSelectedItemPosition());
                    Log.e("cityId", cityid+" "+taxitypeid+" "+taxitypeSpinner.getSelectedItemPosition());
                    if (isInternetConnected(getActivity()))
                    {
                        new getPacakages().execute(Authorization, usertype,cityid,taxitypeid);
                    }
                    else {
                        Snackbar.make(view,"Internet Connection Unavailable",Snackbar.LENGTH_LONG).show();
                    }
                }

             /*   if(taxitypeSpinner.getSelectedItem().toString().equals("SUV")){
                    Log.e("click","SUV");
                    // noOfPassenger.add(new String("5"));
                    //noOfPassenger.add(new String("6"));
                    // max=6;
                }
                else {


                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        if(isInternetConnected())
        {
            String Auth="Token "+loginpref.getString("access_token", "n");
            String usertype=loginpref.getString("usertype", "n");
            String corporatreId=loginpref.getString("corporate_id", "n");

            bill_idlist.add(new String("Billing ID"));
            bill_namelist.add(new String("Billing ID"));
            new getBillingEntities().execute(Auth,usertype,corporatreId);
            billingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, bill_namelist);
            txt_localBillingEntity.setAdapter(billingAdapter);

        }



        return view;
    }

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

        //Getting Login Time Spoc Details for Api Call
        Authorization = "Token " + access_token;
        userType = loginpref.getString("usertype", "n");
        userId = loginpref.getString("spoc_id", "n");
        corporateId = loginpref.getString("corporate_id", "n");
        spocId = loginpref.getString("spoc_id", "n");
        groupId = loginpref.getString("group_id", "n");
        subgroupId = loginpref.getString("subgroup_id", "n");

        mpickupCity=places_idlist.get(pickupCitySpinner.getSelectedItemPosition());
        mtaxiType=taxi_type_id_list.get(taxitypeSpinner.getSelectedItemPosition());
        massCity=assessmentCityId.get(txt_localAssCity.getSelectedItemPosition());
        massCode=assessmentCodeId.get(txt_localAssCode.getSelectedItemPosition());
        mbiilEntity=bill_idlist.get(txt_localBillingEntity.getSelectedItemPosition());
        mpickupLocation = txt_pickupLocation.getText().toString();
        mdropLocation=txt_dropLocation.getText().toString();
        mpickupDate = txt_localPickupDate.getText().toString();
        //String onlytime= String.valueOf(mpickupDate.split("T"));
        mpickupTime = txt_localPickupTime.getText().toString();
        finalDateAndTime = mpickupDate + " " + mpickupTime;
        mpessengers = txt_localPassengers.getText().toString();
        mreason = txt_localReason.getText().toString();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("request code",requestCode+"");
        // Log.d("employeee", Arrays.toString(employeeIdList.toArray()));

        // Check that the result was from the autocomplete widget.
        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.
                txt_pickupLocation.setError(null);
                txt_pickupLocation.setText(place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=data.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.

                txt_dropLocation.setError(null);
                txt_dropLocation.setText(place.getAddress());

            }
        }else if (requestCode ==EMPLOYEE_INFO){

            if(resultCode == RESULT_OK){
                //passenger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background , 0);
                employee_idlist=data.getStringArrayListExtra("employee_id");
                getEmployee_idphno=data.getStringArrayListExtra("employee_phno");
                employee_id_value= data.getStringArrayListExtra("employeeIdValue").toArray(new String[0]);
                Log.d("employeeIdValue", Arrays.toString(employee_idlist.toArray()));
                Log.d("phone", Arrays.toString(getEmployee_idphno.toArray()));
                Log.d("employeee", Arrays.toString(employee_id_value));

            }

        }
    }

    protected  void gettingLatLng(String city){
        if(Geocoder.isPresent() && isInternetConnected(getActivity())){
            try {
                Geocoder gc = new Geocoder(getActivity());
                List<Address> addresses= gc.getFromLocationName(city, 5); // get the found Address Objects

                List<LatLng> latlng = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        latlng.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        lat =a.getLatitude();
                        lng = a.getLongitude();
                        Log.d("lnag",lng+"");
                    }
                }
                Log.d("Local lat and lng", Arrays.toString(latlng.toArray()));
            } catch (IOException e) {
                // handle the exception
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
            passenger5 = (ImageView) view.findViewById(R.id.passenger_5);
            passenger5.setVisibility(View.GONE);
            passenger6 = (ImageView) view.findViewById(R.id.passenger_6);
            passenger6.setVisibility(View.GONE);
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
            }else if(total == 5){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
                passenger5.setVisibility(View.VISIBLE);
            }else if(total ==6){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
                passenger5.setVisibility(View.VISIBLE);
                passenger6.setVisibility(View.VISIBLE);
            }

            total_text.setText(total+"");
            ok = (Button) view.findViewById(R.id.ok);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==max){
                        Toast.makeText(getActivity(),"Number of Local Taxi Passenger should be less then "+max,Toast.LENGTH_SHORT).show();

                    }else {
                        total++;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger2.setVisibility(View.VISIBLE);
                        }if(total==3){
                            passenger3.setVisibility(View.VISIBLE);
                        }if (total==4){
                            passenger4.setVisibility(View.VISIBLE);
                        }if(total==5){
                            passenger5.setVisibility(View.VISIBLE);
                        }if(total==6){
                            passenger6.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==1){
                        Toast.makeText(getActivity(),"Number of Local Taxi Passenger should be more then "+ 0,Toast.LENGTH_SHORT).show();
                    }else {
                        total--;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger3.setVisibility(View.GONE);
                        }if(total==3){
                            passenger4.setVisibility(View.GONE);
                        }if (total==1){
                            passenger2.setVisibility(View.GONE);
                        }if(total==4){
                            passenger5.setVisibility(View.GONE);
                        }if(total == 5){
                            passenger6.setVisibility(View.GONE);
                        }
                    }

                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_localPassengers.setError(null);
                    txt_localPassengers.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(),EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




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
                    start_date_send = day + "/" + month + "/" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txt_localPickupDate.setText(d + "-" + month + "-" + year);
                    } else {
                        txt_localPickupDate.setText(day + "-" + month + "-" + year);
                    }
                    txt_localPickupDate.setError(null);

                } else {
                    Toast.makeText(getContext(),"past dates are not allowed",Toast.LENGTH_SHORT).show();
                    // Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
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
                    txt_localPickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    txt_localPickupTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    txt_localPickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    txt_localPickupTime.setText(hourOfDay + ":" + minute + ":00");
                }
                txt_localPickupTime.setError(null);
            }
        }
    }

    protected  class doLocalTaxiBooking extends AsyncTask<String,Integer,String>
    {
        AddTaxiBookingAPI addTaxiBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {


            String Auth=params[0].toString();
            String usertype=params[1].toString();
            ArrayList<String> emp=new ArrayList<>();
            emp.add("10");
            emp.add("20");

            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data",params.toString());
            addTaxiBookingAPI = ConfigRetrofit.configRetrofit(AddTaxiBookingAPI.class);

            if (hasAssCode.equals("0")){
                massCode="0";
                massCity="0";
            }

            addTaxiBookingAPI.addLocalTaxiBooking(Auth,usertype,userId,corporateId,mbiilEntity,spocId,groupId,subgroupId,
                    "2",mpickupCity,mpickupLocation,mdropLocation,finalDateAndTime,mtaxiType,
                    mpackage,massCode, massCity,mreason,mpessengers,employee_id_value
                    ).enqueue(new Callback<AddTaxiResponce>() {
                @Override
                public void onResponse(Call<AddTaxiResponce> call, Response<AddTaxiResponce> response) {


                    Log.d("resp",response.body().message);
                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(getContext(),"Booking Added :"+response.body().message,Toast.LENGTH_SHORT).show();

                        final Dialog dialog = new Dialog(context);
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
                        mNo.setVisibility(View.INVISIBLE);

                        myes.setText("Ok");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getActivity(), AddTaxiBookingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        mNo.setVisibility(View.INVISIBLE);
                        dialog.show();
                        /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Booking Status");
                        alertDialogBuilder.setMessage(""+response.body().message);
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent=new Intent(getActivity(), AddTaxiBookingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alert11 = alertDialogBuilder.create();
                        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                        alert11.show();*/
                    }

                }

                @Override
                public void onFailure(Call<AddTaxiResponce> call, Throwable t) {

                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();

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
            pd = new ProgressDialog(getContext());
            pd.setMessage("completing booking");
            d=new android.app.AlertDialog.Builder(getContext());
            d.setTitle("BUS BOOKING");
            //pd.show();
        }


        @Override
        protected void onPostExecute(String s)
        {

            pd.dismiss();

        }
    }

    public static class packageDialog extends  DialogFragment
    {
        private TextView code_name;
        Button ok;

        String selected_package = "Select Packages";
        String selectedPackageId="Selected Packages Id";
        Spinner assesmentspinner;
        RelativeLayout relativeLayout;

        public packageDialog(){

        }

        public static packageDialog newInstance(int no){
            packageDialog frag = new packageDialog();
            Bundle args = new Bundle();
            args.putInt("details",no);
            frag.setArguments(args);
            return  frag;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
            return inflater.inflate(R.layout.packages_dialog,container);
        }

        @Override

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);
            com.rey.material.widget.TextView textView = (com.rey.material.widget.TextView) view.findViewById(R.id.text_preview);
            TextView noPackage = (TextView) view.findViewById(R.id.no_packages);
            final ListView listView  = (ListView) view.findViewById(R.id.list);
            listView.setBackgroundResource(R.drawable.shape_intro_text);
            listView.setAdapter(packageadapters);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(packageadapters.getSelected_package()!=null)
                        Log.d("select packages",packageadapters.getSelected_package());
                        selected_package= packageadapters.getSelected_package();

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });


            if(!packageadapters.isEmpty()&&packageadapters.getItem(0).equals("No Packages")){
                noPackage.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else {
                noPackage.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);


                // listView.setItemsCanFocus(true);
                listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                        Log.d("clicked",listView.getItemAtPosition(myItemInt)+"");


                    }

                });

            }


            Button button = (Button) view.findViewById(R.id.ok);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCount=0;

                    getDialog().dismiss();

                }
            });










        }
    }

    String pacakagevalue;
    protected class getPacakages extends AsyncTask<String,Integer,String>

    {
        GetPackagesAPI getPackagesAPI;


        @Override
        protected String doInBackground(final String... params) {

            String Auth=params[0].toString();
            String usertype=params[1];
            String mcity=params[2];
            String mtaxitype=params[3];
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];

            Log.d("posting data",params.toString());
            getPackagesAPI = ConfigRetrofit.configRetrofit(GetPackagesAPI.class);
            getPackagesAPI.getTaxiPackages(Auth,usertype,corporateId,mcity,mtaxitype,"2").enqueue(new Callback<PackagesResponse>() {
                @Override
                public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response)
                {


                        if (response.body().getSuccess()==1)
                        {
                            String resp=GsonStringConvertor.gsonToString(response.body());

                            Log.d("Packages", GsonStringConvertor.gsonToString(response.body().getPackage()));
                            List<Packages> packagesList=response.body().getPackage();


                            for(int i=0;i<packagesList.size();i++) {
                                pacakagelist.clear();
                                pacakagelist.add(packagesList.get(i).getPackageName());
                                pacakage_idlist.add(String.valueOf(packagesList.get(i).getId()));
                                packagehashMap.put(packagesList.get(i).getPackageName(),String.valueOf(packagesList.get(i).getId()));
                            }
                            packageadapters.setPackagess(pacakagelist);
                            packageadapters.notifyDataSetChanged();
                        }else {
                            pacakagelist.removeAll(pacakagelist);
                            pacakage_idlist.removeAll(pacakage_idlist);
                            pacakagelist.add("Select Pacakage");
                            pacakage_idlist.add("Select Package Id");
                            Toast.makeText(context, "No Package found", Toast.LENGTH_SHORT).show();
                            b.setMessage("No Package found");
                            b.setTitle("Packages");
                            b.show();
                        }
                }

                @Override
                public void onFailure(Call<PackagesResponse> call, Throwable t) {

                }
            });


        return pacakagevalue;
        }

        ProgressDialog pd;
        AlertDialog.Builder b;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Getting Packages");
            pd.setCancelable(false);
            pd.show();
            b=new AlertDialog.Builder(getActivity());
            b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            pd.dismiss();

        }

    }
    private static class packageAdapter extends BaseAdapter
    {

        private LayoutInflater inflater;
        ArrayList<String> packagess;
        Context context;
        String selected_package;
        Boolean isChecked =false;
        int selected_position;

        ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();

        public LayoutInflater getInflater() {
            return inflater;
        }

        public void setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public ArrayList<String> getPackagess() {
            return packagess;
        }

        public void setPackagess(ArrayList<String> packagess) {
            this.packagess = packagess;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public Boolean getChecked() {
            return isChecked;
        }

        public void setChecked(Boolean checked) {
            isChecked = checked;
        }

        public int getSelected_position() {
            return selected_position;
        }

        public void setSelected_position(int selected_position) {
            this.selected_position = selected_position;
        }

        public ArrayList<CheckBox> getmCheckBoxes() {
            return mCheckBoxes;
        }

        public void setmCheckBoxes(ArrayList<CheckBox> mCheckBoxes) {
            this.mCheckBoxes = mCheckBoxes;
        }

        public packageAdapter(Context context, ArrayList<String> packages){
            this.context = context;
            this.packagess = packages;
        }
        @Override
        public  int getCount(){

            return packagess.size();
        }
        public  void setSelected_package(String packages){
            selected_package = packages;
        }

        public  String getSelected_package(){
            return selected_package;
        }
        @Override
        public Object getItem(int position){
            return packagess.get(position);
        }

        @Override
        public long getItemId(int position){
            return  position;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(context);

            if(convertView==null){

                convertView= inflater.inflate(R.layout.packages_adapter_layout,parent,false);
            }
            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            final TextView textView =(TextView) convertView.findViewById(R.id.textView1);
            textView.setText(packagess.get(position));
            checkBox.setTag("first");
            mCheckBoxes.add(checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked())
                    {
                        for (int i = 0; i < mCheckBoxes.size(); i++)
                        {
                            if (mCheckBoxes.get(i) == v)
                                selected_position = i;
                            else
                                mCheckBoxes.get(i).setChecked(false);
                        }

                    }
                    else
                    {
                        selected_position=-1;
                    }
                    txt_localPackages.setError(null);
                    txt_localPackages.setText(textView.getText() + "");

                }


            });



            return convertView;
        }

    }

    public class AddTaxiResponce {

        @SerializedName("success")
        @Expose
        private int success;
        @SerializedName("message")
        @Expose
        private String message;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    ArrayList<String> list1=new ArrayList<>();

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
            pd = new ProgressDialog(getContext());
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

                    bill_idlist.add(new String(obj.get("id").toString()));
                    bill_namelist.add(new String(obj.get("entity_name").toString()));

                }


            }
            pd.dismiss();
        }


    }
    AlertDialog.Builder dialog;
    public boolean isInternetConnected(){
        ConnectivityManager cm=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[]=cm.getAllNetworkInfo();

        int i;
        for(i=0;i<networkInfo.length;++i){
            if(networkInfo[i].getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;

    }
}
