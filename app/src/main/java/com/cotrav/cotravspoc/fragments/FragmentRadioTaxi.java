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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.PlacesFieldSelector;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.addbookings.addtaxi.AddTaxiBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addtrain.AddTrainBookingActivity;
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;
import com.cotrav.cotravspoc.models.allcitiesmodel.City;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.retrofit.APIurls;
import com.cotrav.cotravspoc.retrofit.AddTaxiBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.viewmodels.AllCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCodeViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.cotrav.cotravspoc.fragments.FragmentLocalTaxi.pickupCitySpinner;
import static com.cotrav.cotravspoc.fragments.FragmentLocalTaxi.taxitypeSpinner;
import static com.cotrav.cotravspoc.fragments.FragmentLocalTaxi.txt_localPackages;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRadioTaxi extends Fragment
{

    private static final int EMPLOYEE_INFO=100;
    static SharedPreferences loginpref,spocpref;
    static String Authorization="";
    static String usertype="";
    String radio;
    AllCitiesViewModel allCitiesViewModel;
    ArrayList<String> placelist,places_idlist,taxi_type_list,taxi_type_id_list;
    ArrayAdapter<String> allcityadapter,placeadapter,assesmentadapter,assesmentcodeAdapter,billingAdapter;;
    ArrayList<String> assessmentCityList,assessmentCityIdList,assessmentCodeList,assessmentCodeIdList;
    static SearchableSpinner txt_radiopickupCity,txt_radioAssCity,txt_radioAssCode,txt_radioBilling;
    static TextView txt_radioPickupDate,txt_radioPickupTime,txt_radiopickupLocation,txt_radiodropLocation,txt_radioPassengers,txt_radioReason;
    AssessmentCitiesViewModel assessmentCitiesViewModel;
    AssessmentCodeViewModel assessmentCodeViewModel;
    ArrayList<String> employee_idlist;
    ArrayList<String> getEmployee_idphno;
    ArrayList<String> bill_namelist,bill_idlist;
    String[] employee_id_value;
    SharedPreferences billing_pref;
    String hasAssCode,access_token,userType,userId,finalDateAndTime,mbiilEntity,spocId,groupId,subgroupId,mpickupCity,mtaxiType,mpickupLocation,mdropLocation,mpickupDate,mpickupTime,mpessengers,mpriority,massCity,massCode,mreason;
    Button btnSave;
    static  String start_date_send,city;
    static int total=1;
    double lat;
    double lng;
    static Context context;
    private String corporateId;

    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    LinearLayout layoutAssisment;
    ProgressDialog progressDialog;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    public FragmentRadioTaxi()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_radio_taxi, container, false);

        context=getActivity();
        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

        loginpref=getActivity().getSharedPreferences("login_info",MODE_PRIVATE);
        userType = loginpref.getString("usertype", "n");
        billing_pref= getActivity().getSharedPreferences("billing_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");

        //gathering login time spoc details for api call
        Authorization="Token "+loginpref.getString("access_token", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        radio = loginpref.getString("is_radio", null);
        spocpref=getActivity().getSharedPreferences("spoc_info",MODE_PRIVATE);
        hasAssCode=spocpref.getString("has_assessment_codes","n");

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        txt_radiopickupCity = (SearchableSpinner) view.findViewById(R.id.radio_spinner_city);
        txt_radiopickupLocation = (TextView) view.findViewById(R.id.radio_pickupLocation);
        txt_radiodropLocation = (TextView) view.findViewById(R.id.radio_dropLocation);
        txt_radioPickupDate=(TextView)view.findViewById(R.id.radio_datePickup);
        txt_radioPickupTime=(TextView)view.findViewById(R.id.radio_timePickup);
        txt_radioAssCity=(SearchableSpinner)view.findViewById(R.id.radio_assesmentCity);
        txt_radioAssCode=(SearchableSpinner)view.findViewById(R.id.radio_assesmentCode);
        txt_radioPassengers=(TextView)view.findViewById(R.id.radio_pessangers);
        txt_radioBilling=(SearchableSpinner)view.findViewById(R.id.radio_billingEntity);
        txt_radioReason=(TextView)view.findViewById(R.id.radio_reason);
        layoutAssisment=(LinearLayout)view.findViewById(R.id.layout_assisment);
        if (hasAssCode.equals("0"))
        {
            layoutAssisment.setVisibility(View.GONE);
        }
        btnSave=(Button) view.findViewById(R.id.btn_submit);






     /*   txt_radiopickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickup = new Intent(getActivity(), GetMapLocationActivity.class);
                pickup.putExtra("calledby","radio");
                pickup.putExtra("city", city);
                //drop.putExtra("address",finalAddress);
                pickup.putExtra("lat",lat);
                pickup.putExtra("lng",lng);
                startActivityForResult(pickup, PICKUP_CODE_AUTOCOMPLETE);
            }
        });

        txt_radiodropLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickup = new Intent(getActivity(), GetMapLocationActivity.class);
                pickup.putExtra("calledby","radio");
                pickup.putExtra("city", city);
                //drop.putExtra("address",finalAddress);
                pickup.putExtra("lat",lat);
                pickup.putExtra("lng",lng);
                startActivityForResult(pickup, DROP_CODE_AUTOCOMPLETE);
            }
        });*/
        txt_radiopickupLocation.setOnClickListener(new View.OnClickListener() {
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
        txt_radiodropLocation.setOnClickListener(new View.OnClickListener() {
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
        txt_radioPickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getFragmentManager(), "datePicker");

            }
        });
        txt_radioPickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getFragmentManager(), "starttime");
            }
        });
        txt_radioPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getFragmentManager(),"no of radio_passenger");
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();

                if (isInternetConnected(getContext())){
                    if (
                                    (txt_radiopickupCity.getSelectedItemPosition()==0)
                                    ||(txt_radioBilling.getSelectedItemPosition()==0)
                                    || (txt_radioReason.getText().toString().length()<1)
                                    || (txt_radioPassengers.getText().toString().length()<1)
                                    ||txt_radiopickupLocation.getText().toString().equals("Pickup Location")
                                    ||txt_radiodropLocation.getText().toString().equals("Drop Location")
                                    ||txt_radioPickupDate.getText().toString().equals("Pickup Date")
                                    ||txt_radioPickupTime.getText().toString().equals("Pickup Time")


                    ) {

                        Toast.makeText(getActivity(), "Please Fill all Fields", Toast.LENGTH_SHORT).show();

                        if (employee_idlist.size() == 0) {
                            txt_radioPassengers.setError("field required");
                            Toast.makeText(getActivity(), "please choose Employee ", Toast.LENGTH_SHORT).show();
                        }

                        if (txt_radioPassengers.getText().toString().equals("Passengers")){
                            txt_radioPassengers.setError("field required");
                        }

                        if (txt_radiopickupLocation.getText().toString().equals("Pickup Location")){
                            txt_radiopickupLocation.setError("field required");

                        }
                        if (txt_radiodropLocation.getText().toString().equals("Drop Location")){
                            txt_radiodropLocation.setError("field required");
                        }
                        if (txt_radioPickupDate.getText().toString().equals("Pickup Date")){
                            txt_radioPickupDate.setError("field required");
                        }
                        if (txt_radioPickupTime.getText().toString().equals("Pickup Time")){
                            txt_radioPickupTime.setError("field required");
                        }




                        if (txt_radioReason.getText().toString().length() < 1){
                            txt_radioReason.setError("field required");
                        }
                        if (pickupCitySpinner.getSelectedItem().toString().equals("Select City")){
                            ((android.widget.TextView) pickupCitySpinner.getSelectedView()).setError("field required");
                        }
                        if (taxitypeSpinner.getSelectedItem().toString().equals("Taxi Type")){
                            ((android.widget.TextView) taxitypeSpinner.getSelectedView()).setError("field required");

                        }
                        if (txt_radioBilling.getSelectedItem().toString().equals("Billing ID")){
                            ((android.widget.TextView) txt_radioBilling.getSelectedView()).setError("field required");

                        }
                        if (hasAssCode.equals("0")==false)
                        {
                            if (txt_radioAssCode.getSelectedItem().toString().equals("Ass.Code"))
                            {
                                ((android.widget.TextView) txt_radioAssCode.getSelectedView()).setError("field required");
                            }

                            if (txt_radioAssCity.getSelectedItem().toString().equals("Ass.City"))
                            {
                                ((android.widget.TextView) txt_radioAssCity.getSelectedView()).setError("field required");

                            }
                        }
                     /*   if (txt_radioAssCity.getSelectedItem().toString().equals("Ass.City")){
                            ((android.widget.TextView) txt_radioAssCity.getSelectedView()).setError("field required");

                        }
                        if (txt_radioAssCode.getSelectedItem().toString().equals("Ass.Code"))
                        {
                            ((android.widget.TextView) txt_radioAssCode.getSelectedView()).setError("field required");
                        }*/
                        if (txt_radiopickupCity.getSelectedItem().toString().equals("Select City"))
                        {
                            ((android.widget.TextView) txt_radiopickupCity.getSelectedView()).setError("field required");
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

                                new doRadioTaxiBooking().execute(
                                        Authorization,userType,corporateId,userId,corporateId,"1",spocId,groupId,
                                        subgroupId,"2",mpickupCity,mpickupLocation,finalDateAndTime,mtaxiType,massCode,massCity,mreason
                                );
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
                /*        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Radio TAXI BOOKING");
                        alertDialogBuilder.setIcon(R.drawable.check_icon);
                        alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {

                                new doRadioTaxiBooking().execute(
                                        Authorization,userType,corporateId,userId,corporateId,"1",spocId,groupId,
                                        subgroupId,"2",mpickupCity,mpickupLocation,finalDateAndTime,mtaxiType,massCode,massCity,mreason
                                );


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
        allCitiesViewModel.initAllCitiesViewModel(Authorization,loginpref.getString("usertype","n"));
        assessmentCitiesViewModel= ViewModelProviders.of(getActivity()).get(AssessmentCitiesViewModel.class);
        assessmentCitiesViewModel.initViewModel(Authorization, usertype,corporateId);
        assessmentCodeViewModel= ViewModelProviders.of(getActivity()).get(AssessmentCodeViewModel.class);
        assessmentCodeViewModel.initViewModel(Authorization, usertype,corporateId);



        List<String> cities = new ArrayList<String>();
        taxi_type_list=new ArrayList<>();
        assessmentCityList=new ArrayList<>();
        assessmentCityIdList=new ArrayList<>();
        assessmentCodeList=new ArrayList<>();
        assessmentCodeIdList=new ArrayList<>();
        taxi_type_id_list=new ArrayList<>();
        placelist=new ArrayList<>();
        places_idlist=new ArrayList<>();
        bill_namelist=new ArrayList<>();
        bill_idlist=new ArrayList<>();
        employee_idlist=new ArrayList<>();
        getEmployee_idphno=new ArrayList<>();



        allCitiesViewModel.getCitiesLiveData(Authorization, usertype);
        placelist.add(new String("Select City"));
        allcityadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,placelist);
        txt_radiopickupCity.setAdapter(allcityadapter);
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
                            allcityadapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }


                    }
                });



        assessmentCityList.add(new String("Ass.City"));
        assessmentCityIdList.add(new String("Ass.City"));
        assesmentadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,assessmentCityList);
        txt_radioAssCity.setAdapter(assesmentadapter);
        assessmentCitiesViewModel.getLiveAssessmentCityList(Authorization,usertype,corporateId).observe(this, new Observer<List<AssCity>>() {
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
                Log.d("Ass.Cities", GsonStringConvertor.gsonToString(assCities));
            }
        });

        assessmentCodeList.add(new String("Ass.Code"));
        assessmentCodeIdList.add(new String("Ass.Code"));
        assesmentcodeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,assessmentCodeList);
        txt_radioAssCode.setAdapter(assesmentcodeAdapter);
        assessmentCodeViewModel.getLiveAssessmentCodeList(Authorization,usertype,corporateId).observe(this, new Observer<List<AssCode>>() {
            @Override
            public void onChanged(List<AssCode> assCodes) {

                if (assCodes!=null && assCodes.size()>0){
                    for (int i=0;i<assCodes.size();i++){

                        assessmentCodeList.clear();
                        assessmentCodeIdList.clear();
                        assessmentCodeList.add(new String("Ass.Code"));
                        assessmentCodeIdList.add(new String("Ass.Code"));
                        //assesmentcodeAdapter.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeList.add(assCodes.get(i).getAssessmentCode());
                        assessmentCodeIdList.add(String.valueOf(assCodes.get(i).getId()));

                    }
                }
                Log.d("TaxiTypes", GsonStringConvertor.gsonToString(assCodes));
            }
        });

        if(isInternetConnected(getContext())) {
            String Auth="Token "+loginpref.getString("access_token", "n");
            String usertype=loginpref.getString("usertype", "n");
            String corporatreId=loginpref.getString("corporate_id", "n");

            bill_idlist.add(new String("Billing ID"));
            bill_namelist.add(new String("Billing ID"));
            new getBillingEntities().execute(Auth,usertype,corporatreId);
            billingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, bill_namelist);
            txt_radioBilling.setAdapter(billingAdapter);



        }


        return view;
    }

    private void setData()

    {

        //Getting Login Time Spoc Details for Api Call
        Authorization = "Token " + access_token;
        userType = loginpref.getString("usertype", "n");
        userId = loginpref.getString("spoc_id", "n");
        corporateId = loginpref.getString("corporate_id", "n");
        spocId = loginpref.getString("spoc_id", "n");
        groupId = loginpref.getString("group_id", "n");
        subgroupId = loginpref.getString("subgroup_id", "n");
        mpickupCity=places_idlist.get(txt_radiopickupCity.getSelectedItemPosition());
        mpickupLocation=txt_radiopickupLocation.getText().toString();
        mdropLocation=txt_radiodropLocation.getText().toString();
        massCity=assessmentCityIdList.get(txt_radioAssCity.getSelectedItemPosition());
        massCode=assessmentCodeIdList.get(txt_radioAssCode.getSelectedItemPosition());
        mbiilEntity=bill_idlist.get(txt_radioBilling.getSelectedItemPosition());
        mpickupDate = txt_radioPickupDate.getText().toString();
        mpickupTime = txt_radioPickupTime.getText().toString();
        finalDateAndTime = mpickupDate + " " + mpickupTime;
        mpessengers = txt_radioPassengers.getText().toString();
        mreason = txt_radioReason.getText().toString();

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
                txt_radiopickupLocation.setError(null);
                txt_radiopickupLocation.setText(place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=data.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.

                txt_radiodropLocation.setError(null);
                txt_radiodropLocation.setText(place.getAddress());

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
                        txt_radioPickupDate.setText(d + "-" + month + "-" + year);
                    } else {
                        txt_radioPickupDate.setText(day + "-" + month + "-" + year);
                    }
                    txt_radioPickupDate.setError(null);

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
                    txt_radioPickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    txt_radioPickupTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    txt_radioPickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    txt_radioPickupTime.setText(hourOfDay + ":" + minute + ":00");
                }
                txt_radioPickupTime.setError(null);
            }
        }
    }

    public static class NoOfPassengers extends  DialogFragment{
        private ImageView plus,minus;
        private ImageView passenger1,passenger2,passenger3,passenger4,passenger5,passenger6;
        private com.rey.material.widget.TextView total_text;
        com.rey.material.widget.Button ok;
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
            total_text = (com.rey.material.widget.TextView) view.findViewById(R.id.total_text);
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
            ok = (com.rey.material.widget.Button) view.findViewById(R.id.ok);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==4){
                        Toasty.error(getActivity(),"Number of Radio Passenger should be less then four",Toast.LENGTH_SHORT).show();
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
                        Toasty.error(getActivity(),"Number of Radio Passenger should be more then Zero",Toast.LENGTH_SHORT).show();
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
                    txt_radioPassengers.setError(null);
                    txt_radioPassengers.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(),EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




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
                    bill_namelist.add(new String(obj.get("entity_name").toString()));
                    bill_idlist.add(new String(obj.get("id").toString()));

                }


            }
            pd.dismiss();
        }


    }
    public boolean isInternetConnected(Context context){
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


    protected  class doRadioTaxiBooking extends AsyncTask<String,Integer,String>
    {
        AddTaxiBookingAPI addTaxiBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {

            //String Auth=params[0].toString();
           // String usertype=params[1].toString();
            //ArrayList<String> emp=new ArrayList<>();
            //emp.add("10");
            //emp.add("20");

            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data",params.toString());
            addTaxiBookingAPI = ConfigRetrofit.configRetrofit(AddTaxiBookingAPI.class);
            if (hasAssCode.equals("0")){
                massCode="0";
                massCity="0";
            }
            addTaxiBookingAPI.addRadioTaxiBooking(Authorization,userType,userId,corporateId,mbiilEntity,spocId,groupId,subgroupId,
                    "1",mpickupCity,mpickupLocation,mdropLocation,finalDateAndTime,mtaxiType,massCode,massCity,
                    mreason,mpessengers,employee_id_value
            ).enqueue(new Callback<FragmentLocalTaxi.AddTaxiResponce>() {
                @Override
                public void onResponse(Call<FragmentLocalTaxi.AddTaxiResponce> call, Response<FragmentLocalTaxi.AddTaxiResponce> response) {

                    if(response.code()==200)
                    {
                        Toast.makeText(getContext(),""+response.body().getMessage().toString(),Toast.LENGTH_LONG).show();
                        assesmentvalue=response.body().toString();

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("Booking Status");
                        mDialogmsg.setText(""+response.body().getMessage());

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

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
                      /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Booking Status");
                        alertDialogBuilder.setMessage(""+response.body().getMessage());
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
                        alert11.show();    */                }
                }

                @Override
                public void onFailure(Call<FragmentLocalTaxi.AddTaxiResponce> call, Throwable t) {

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
            pd.show();
        }


        @Override
        protected void onPostExecute(String s)
        {

            pd.dismiss();

        }
    }

}
