package com.cotrav.cotravspoc.activities.addbookings.addflight;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCity;
import com.cotrav.cotravspoc.models.assesmentcodemodel.AssCode;
import com.cotrav.cotravspoc.retrofit.APIurls;
import com.cotrav.cotravspoc.retrofit.AddFlightBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.viewmodels.AssessmentCitiesViewModel;
import com.cotrav.cotravspoc.viewmodels.AssessmentCodeViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
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
import java.util.Locale;
import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddFlightBookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar mtoolbar;
    Context context;
    static TextView pickupLocation,dropLocation;
    static SearchableSpinner seatType,assCity,assCode,billingEntity;
    static TextView pickupDate;
    static TextView pickupTime;
    static TextView txt_pessengers;
    static EditText txt_reason,flightType;

    Button btnSubmit;
    String strPickup,strDrop,strPickDate,strPickTime,strBookingDate,strPickFinalDate,strPassenger,
            strSeatType,strFlightType,strAssCity, strAssCode,strBillingEntity,strReason;
    Boolean strOneWay,strTwoWay;
    final int FLIGHT_PICKUP_LOCATION=1,FLIGHT_DROP_LOCATION=0,FLIGHT_BOARDING_LOCATION=3;
    public static final String TAG = "FlightBookingActivity";
    ArrayList<String> employee_idlist,employee_id_value,getEmployee_idphno,age_list,male_female_list,id_type_list,id_no_list;
    ArrayList<String> bill_namelist,bill_idlist,assessmentCityList,assessmentCityIdList,assessmentCodeList,assessmentCodeIdList;
    ArrayAdapter<String> billingAdapter,assesmentadapter,assesmentcodeAdapter;
    AssessmentCitiesViewModel assessmentCitiesViewModel;
    AssessmentCodeViewModel assessmentCodeViewModel;
    static int total=1,EMPLOYEE_INFO=100;
    static  String start_date_send;
    String city,finalAddress;
    double lat;
    double lng;
    RadioButton journeyType;
    String strjourneyType;
    RadioGroup radioGroup;
    String travelClass[]={"Select Class","Economy","Business","Premimum"};
    SharedPreferences loginpref,billing_pref,spocpref;
    static String hasAssCode,access_token,Authorization,userType,corporateId,userId,spocId,groupId,subgroupId;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    LinearLayout layoutAssisment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight_booking);



        context= AddFlightBookingActivity.this;
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        userType = loginpref.getString("usertype", "n");
        billing_pref= getSharedPreferences("billing_info", MODE_PRIVATE);
        access_token= loginpref.getString("access_token", "n");
        Authorization="Token "+loginpref.getString("access_token", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocpref=getSharedPreferences("spoc_info",MODE_PRIVATE);
        hasAssCode=spocpref.getString("has_assisment_code","n");


        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

        Log.d("====token===",access_token);

        mtoolbar=findViewById(R.id.toolbar);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        mtoolbar.setTitle("Add Flight Booking");
        mtoolbar.setNavigationIcon(R.drawable.test_back);

        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        //txt_oneway=(CheckBox)findViewById(R.id.check_oneway);
        //txt_twoway=(CheckBox)findViewById(R.id.check_twoway);
        pickupLocation=(TextView)findViewById(R.id.flight_pickupLocation);
        dropLocation=(TextView)findViewById(R.id.flight_dropLocation);
        pickupDate=(TextView)findViewById(R.id.flight_datePickup);
        pickupTime=(TextView)findViewById(R.id.flight_timePickup);
        seatType=(SearchableSpinner)findViewById(R.id.seat_type);
        flightType=(EditText) findViewById(R.id.flight_type);
        txt_pessengers=(TextView)findViewById(R.id.flight_pessangers);
        assCity=(SearchableSpinner)findViewById(R.id.flight_assesmentCity);
        assCode=(SearchableSpinner)findViewById(R.id.flight_assesmentCode);
        billingEntity=(SearchableSpinner)findViewById(R.id.flight_billingEntity);
        txt_reason=(EditText)findViewById(R.id.flight_reason);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        layoutAssisment=(LinearLayout)findViewById(R.id.layout_assisment);
        if (hasAssCode.equals("0"))
        {
            layoutAssisment.setVisibility(View.GONE);
        }
        btnSubmit=(Button)findViewById(R.id.btn_submit);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, travelClass);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        seatType.setAdapter(spinnerArrayAdapter);


        employee_idlist = new ArrayList<String>();
        getEmployee_idphno = new ArrayList<String>();
        employee_id_value = new ArrayList<>();
        age_list = new ArrayList<String>();
        male_female_list = new ArrayList<String>();
        id_type_list = new ArrayList<String>();
        id_no_list = new ArrayList<String>();
        bill_namelist=new ArrayList<>();
        bill_idlist=new ArrayList<>();
        assessmentCityList=new ArrayList<>();
        assessmentCityIdList=new ArrayList<>();
        assessmentCodeList=new ArrayList<>();
        assessmentCodeIdList=new ArrayList<>();

        assessmentCitiesViewModel= ViewModelProviders.of(this).get(AssessmentCitiesViewModel.class);
        assessmentCitiesViewModel.initViewModel(Authorization, userType,corporateId);
        assessmentCodeViewModel= ViewModelProviders.of(this).get(AssessmentCodeViewModel.class);
        assessmentCodeViewModel.initViewModel(Authorization, userType,corporateId);


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


        /*pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickup = new Intent(AddFlightBookingActivity.this, GetMapLocationActivity.class);
                pickup.putExtra("city", city);
                pickup.putExtra("calledby","local");
                pickup.putExtra("address",finalAddress);
                pickup.putExtra("lat",lat);
                pickup.putExtra("lng",lng);
                startActivityForResult(pickup, FLIGHT_PICKUP_LOCATION);
            }
        });*/
        pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(context);
                startActivityForResult(autocompleteIntent, PICKUP_CODE_AUTOCOMPLETE);
            }
        });

        dropLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(context);
                startActivityForResult(autocompleteIntent, DROP_CODE_AUTOCOMPLETE);
            }
        });

        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle=new Bundle();
                bundle.putString("start","start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");
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
            public void onClick(View view)
            {

                setData();

                if (isInternetConnected(AddFlightBookingActivity.this))
                {
                    if (    (billingEntity.getSelectedItemPosition()==0)
                            ||(seatType.getSelectedItemPosition()==0)
                            ||(pickupLocation.getText().toString().equals("Pickup Location"))
                            ||(dropLocation.getText().toString().equals("Drop Location"))
                            ||(pickupDate.getText().toString().equals("Pickup Date"))
                            ||(pickupTime.getText().toString().equals("Pickup Time"))
                            ||(txt_pessengers.getText().toString().equals("Passenger"))
                            ||(txt_reason.getText().toString().length() < 1)


                    ) {

                        if (pickupLocation.getText().toString().equals("Pickup Location")){
                            pickupLocation.setError("field required");

                        }

                        if (dropLocation.getText().toString().equals("Drop Location")){
                            dropLocation.setError("field required");

                        }
                        if (pickupDate.getText().toString().equals("Pickup Date")) {
                            pickupDate.setError("field required");

                        }
                        if (pickupTime.getText().toString().equals("Pickup Time")) {
                            pickupTime.setError("field required");

                        }
                        if (txt_pessengers.getText().toString().equals("Passenger")) {
                            txt_pessengers.setError("field required");
                        }
                        if (employee_idlist.size() == 0) {
                            txt_pessengers.setError("field required");
                            Toast.makeText(AddFlightBookingActivity.this, "please choose Employee ", Toast.LENGTH_SHORT).show();
                        }

                        if (txt_reason.getText().toString().length() < 1){
                            txt_reason.setError("field required");
                        }
                        if (billingEntity.getSelectedItem().toString().equals("Billing ID")) {
                            ((android.widget.TextView) billingEntity.getSelectedView()).setError("field required");
                        }

                        if (seatType.getSelectedItem().toString().equals("Select Class")) {
                            ((android.widget.TextView) seatType.getSelectedView()).setError("field required");
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



                    } else {


                        final Dialog dialog = new Dialog(AddFlightBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("FLIGHT BOOKING");
                        mDialogmsg.setText("Please Press Yes to Complete booking");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        myes.setText("Yes");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new doFlightBooking().execute(Authorization,userType);
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
                /*        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddFlightBookingActivity.this, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("FLIGHT BOOKING");
                        alertDialogBuilder.setIcon(R.drawable.check_icon);
                        alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {

                                new doFlightBooking().execute(Authorization,userType);

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
            }
            /*{

                setData();


                if (isInternetConnected(AddFlightBookingActivity.this))
                {

                    {



                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddFlightBookingActivity.this, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("FLIGHT BOOKING");
                        alertDialogBuilder.setIcon(R.drawable.check_icon);
                        alertDialogBuilder.setMessage("Please Press" + " Yes " +"Complete booking");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {

                                new doFlightBooking().execute(Authorization,userType);

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
                }
            }*/
        });




        if(isInternetConnected(context))
        {

            bill_idlist.add(new String("Billing ID"));
            bill_namelist.add(new String("Billing ID"));
            new getBillingEntities().execute(Authorization,userType,corporateId);
            billingAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, bill_namelist);
            billingEntity.setAdapter(billingAdapter);
        }

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
    private void setData()
    {

        Authorization="Token "+access_token;
        userId=loginpref.getString("spoc_id", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        spocId=loginpref.getString("spoc_id", "n");
        groupId=loginpref.getString("group_id","n");
        subgroupId=loginpref.getString("subgroup_id","n");


        strPickup=pickupLocation.getText().toString();
        strDrop=dropLocation.getText().toString();
        strPickDate=pickupDate.getText().toString();
        strPickTime=pickupTime.getText().toString();
        strPickFinalDate= strPickDate;
    /*    String currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        strBookingDate=currentDate+" "+currentTime;
       */ strPassenger=txt_pessengers.getText().toString();
        //strSeatType= String.valueOf(seatType.getSelectedItemPosition());
        strFlightType= String.valueOf(flightType.getText().toString());
        strAssCity= assessmentCityIdList.get(assCity.getSelectedItemPosition());
        strAssCode= assessmentCodeIdList.get(assCode.getSelectedItemPosition());
        strBillingEntity= bill_idlist.get(billingEntity.getSelectedItemPosition());
        strReason=txt_reason.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        journeyType = (RadioButton) findViewById(selectedId);
        strjourneyType=journeyType.getText().toString();
        strSeatType=seatType.getSelectedItem().toString();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        Log.d("request code",requestCode+"");

        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.
                pickupLocation.setError(null);
                pickupLocation.setText(place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=data.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);
                dropLocation.setError(null);
                dropLocation.setText(place.getAddress());


            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
        else if (requestCode ==EMPLOYEE_INFO){
            Log.d("employeee", Arrays.toString(employee_idlist.toArray()));

            if(resultCode == RESULT_OK){
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                    start_date_send = year + "/" + month + "/" + day;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        pickupDate.setText(d + "-" + month + "-" + year);
                    } else {
                        pickupDate.setText(day + "-" + month + "-" + year);
                    }
                    pickupDate.setError(null);

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
                    pickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    pickupTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    pickupTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    pickupTime.setText(hourOfDay + ":" + minute + ":00");
                }
                pickupTime.setError(null);
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
                        Toasty.error(getActivity(),"Number of Flight Passengers Must be  Four",Toast.LENGTH_SHORT).show();
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
                        Toasty.error(getActivity(),"Number of Flight Passenger should be more then Zero",Toast.LENGTH_SHORT).show();
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

    protected  class doFlightBooking extends AsyncTask<String,Integer,String> {
        AddFlightBookingAPI addFlightBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {
            String Auth=params[0].toString();
            String usertype=params[1].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];

            Log.d("posting data",params.toString());

            addFlightBookingAPI = ConfigRetrofit.configRetrofit(AddFlightBookingAPI.class);
            if (hasAssCode.equals("0")){
                strAssCode="0";
                strAssCity="0";
            }

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = df.format(c);
            strBookingDate = formattedDate;

            addFlightBookingAPI.addFlightBooking(Authorization,userType,"Flight",strjourneyType,strSeatType,strPickup,strDrop,
                    strBookingDate,strPickFinalDate,strFlightType,strAssCode,strAssCity,strPassenger,groupId,subgroupId,
                    spocId,corporateId,strBillingEntity,strReason,userId,userType,employee_id_value

            ).enqueue(new Callback<Responce>()
            {
                @Override
                public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {

                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(getApplicationContext(),"Booking Added :"+response.body().message,Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(AddFlightBookingActivity.this);
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
            pd = new ProgressDialog(AddFlightBookingActivity.this);
            pd.setMessage("completing booking");
            d=new android.app.AlertDialog.Builder(AddFlightBookingActivity.this);
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
