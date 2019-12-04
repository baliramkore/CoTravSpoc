package com.cotrav.cotravspoc.activities.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.aboutus.AboutUsActivity;
import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.contactus.ContactUsActivity;
import com.cotrav.cotravspoc.activities.login.LoginActivity;
import com.cotrav.cotravspoc.activities.profile.ProfileActivity;
import com.cotrav.cotravspoc.activities.showbookings.ShowBusBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowFlightBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowHotelBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowTaxiBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowTrainBooking;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardData;
import com.cotrav.cotravspoc.viewmodels.DashBoardViewModel;
import com.cotrav.cotravspoc.viewmodels.UserViewModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import me.grantland.widget.AutofitTextView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
     , SwipeRefreshLayout.OnRefreshListener {

    PieChartView pieChartView;
    DrawerLayout drawer;
    ImageView taxiImagebtn, busImageBtn, trainImageBtn, flightImageBtn, hotelImageBtn;
    UserViewModel viewSpocViewModel;
    private static final int REQUEST_CALL = 1;
    private static String TAG = "HomeActivity";
    LifecycleRegistry lifecycleRegistry;
    SharedPreferences loginpref, spocPref;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token, spocId, spocName, spocEmail, Authorization, userType;
    private static String TOTAL_BOOKING = "";
    private ImageView profilePicture;
    ProgressDialog progressDialog;
    private TextView userName;
    private TextView userEmailId;
    TextView networkError;
    DashBoardViewModel dashBoardViewModel;
    private TextView countTotal,taxiCount, busCount, trainCount, hotelCount, flightCount, uptaxiCount, upbusCount, uptrainCount, uphotelCount, upflightCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        askPhoneCallPermission();
        //overridePendingTransition(R.anim.slidein, R.anim.slideout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Cotrav Spoc App");
        setSupportActionBar(toolbar);

        taxiCount = (TextView) findViewById(R.id.taxi_number);
        countTotal=(TextView)findViewById(R.id.total_bookings_pi);
        busCount = (TextView) findViewById(R.id.bus_number);
        trainCount = (TextView) findViewById(R.id.train_number);
        hotelCount = (TextView) findViewById(R.id.hotel_number);
        flightCount = (TextView) findViewById(R.id.flight_number);

        uptaxiCount = (TextView) findViewById(R.id.up_taxi_number);
        upbusCount = (TextView) findViewById(R.id.up_bus_number);
        uptrainCount = (TextView) findViewById(R.id.up_train_number);
        uphotelCount = (TextView) findViewById(R.id.up_hotel_number);
        upflightCount = (TextView) findViewById(R.id.up_flight_number);

        networkError = findViewById(R.id.network_error);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh_home);
        swipeRefreshLayout.setOnRefreshListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        spocPref = getSharedPreferences("spoc_info", MODE_PRIVATE);
        token = loginpref.getString("access_token", "n");
        spocId = loginpref.getString("spoc_id", "n");
        spocName = loginpref.getString("user_name", "n");
        spocEmail = loginpref.getString("email", "n");
        viewSpocViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewSpocViewModel.getSpocDetail("Token " + token, spocId);
        Authorization = "Token " + token;
        userType = loginpref.getString("usertype", "n");
        lifecycleRegistry = new LifecycleRegistry(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        navigationView.getMenu().getItem(0).setChecked(true);

        profilePicture = findViewById(R.id.circleView);
        pieChartView = findViewById(R.id.chart);
        View header = navigationView.getHeaderView(0);
        userName = header.findViewById(R.id.user_name);
        userEmailId = header.findViewById(R.id.user_email_id);

        //bookings navi
        taxiImagebtn = findViewById(R.id.taxiimage);
        taxiImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.StandUp).duration(200).repeat(0).playOn(taxiImagebtn);
                Intent homeIntent = new Intent(HomeActivity.this, ShowTaxiBooking.class);
                startActivity(homeIntent);
            }
        });
        busImageBtn = findViewById(R.id.busimage);
        busImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.StandUp).duration(200).repeat(0).playOn(busImageBtn);
                Intent homeIntent = new Intent(HomeActivity.this, ShowBusBooking.class);
                startActivity(homeIntent);
            }
        });
        hotelImageBtn = findViewById(R.id.hotelimage);
        hotelImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.StandUp).duration(200).repeat(0).playOn(hotelImageBtn);
                Intent homeIntent = new Intent(HomeActivity.this, ShowHotelBooking.class);
                startActivity(homeIntent);
            }
        });
        trainImageBtn = findViewById(R.id.trainimage);
        trainImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.StandUp).duration(200).repeat(0).playOn(trainImageBtn);
                Intent homeIntent = new Intent(HomeActivity.this, ShowTrainBooking.class);
                startActivity(homeIntent);
            }
        });


        flightImageBtn = findViewById(R.id.flightimage);
        flightImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(HomeActivity.this, "Soon", Toast.LENGTH_SHORT).show();
                YoYo.with(Techniques.Pulse).duration(200).repeat(0).playOn(flightImageBtn);
                Intent homeIntent = new Intent(HomeActivity.this, ShowFlightBooking.class);
                startActivity(homeIntent);

               /* Intent homeIntent = new Intent(HomeActivity.this,TaxiBookinngTabActivity.class);
                startActivity(homeIntent);*/
            }
        });

        profileDetails();


        if (isNetworkConnected()) {
            viewSpocViewModel.getSpocDetail("Token " + token, spocId);
            viewSpocViewModel.getError().observe(this, new Observer<String>() {
                @Override
                public String toString() {
                    return "$classname{}";
                }

                @Override
                public void onChanged(@Nullable String s) {

                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    if (s.equals("")) {
                        Toast.makeText(HomeActivity.this, s + "", Toast.LENGTH_SHORT).show();
                    }
                    if (s.equals("0")) {
                        Log.d(TAG, "is not LOGIN  ");
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (s.equals("Connection error")) {
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Check Your Internet Connection");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        mNo.setVisibility(View.GONE);

                        myes.setText("Okey");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        networkError.setVisibility(View.VISIBLE);
                    }

                    if (s.equals("Connection Failed")) {
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Server Connection Failed");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);

                        mNo.setVisibility(View.GONE);

                        myes.setText("Okey");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        networkError.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("Home Activity", s + "");
                        networkError.setVisibility(View.GONE);
                        //Toast.makeText(HomeActivity.this, s + "", Toast.LENGTH_SHORT).show();
                    }
                    // progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        } else {
            final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
            mDialogmsg.setText("Check Your Internet Connection.");

            Button myes = dialog.findViewById(R.id.yes_txt);
            Button mNo = dialog.findViewById(R.id.no_txt);

            mNo.setVisibility(View.GONE);

            myes.setText("Okey");
            myes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //dialog.show();
            //progressDialog.dismiss();
            networkError.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

        dashBoardViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        dashBoardViewModel.initViewModelDashBoard(Authorization, userType, spocId);
        dashBoardViewModel.getDashBoardData(Authorization, userType, spocId).observe(this, new Observer<List<DashBoardData>>() {
            @Override
            public void onChanged(List<DashBoardData> dashBoardData) {

                if (dashBoardData != null && dashBoardData.size() > 0) {
                    for (int i = 0; i < dashBoardData.size(); i++) {
                        List<SliceValue> pieData = new ArrayList<>();
                        pieData.add(new SliceValue(dashBoardData.get(i).getTaxiBookingsCount(), getResources().getColor(R.color.darkblue)));
                        pieData.add(new SliceValue(dashBoardData.get(i).getBusBookingsCount(), getResources().getColor(R.color.colorDivider)));
                        pieData.add(new SliceValue(dashBoardData.get(i).getHotelBookingsCount(), getResources().getColor(R.color.colorPrimaryLight)));
                        pieData.add(new SliceValue(dashBoardData.get(i).getTrainBookingsCount(), getResources().getColor(R.color.colorPrimary)));
                        pieData.add(new SliceValue(dashBoardData.get(i).getFlightBookingsCount(), getResources().getColor(R.color.colorSecondaryText)));
                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasCenterCircle(true).setCenterText2("");
                        //pieChartData.setHasCenterCircle(true).setCenterText1("101");
                        pieChartView.setPieChartData(pieChartData);


                        //pieChartData.setHasCenterCircle(true).setCenterText1(dashBoardData.get(i).getTotalBookingsCount().toString()).setCenterText1FontSize(20);
                        countTotal.setText(dashBoardData.get(i).getTotalBookingsCount().toString());
                        taxiCount.setText(dashBoardData.get(i).getPaTaxiBookingsCount().toString());
                        busCount.setText(dashBoardData.get(i).getPaBusBookingsCount().toString());
                        trainCount.setText(dashBoardData.get(i).getPaTrainBookingsCount().toString());
                        hotelCount.setText(dashBoardData.get(i).getPaHotelBookingsCount().toString());
                        flightCount.setText(dashBoardData.get(i).getPaFlightBookingsCount().toString());
                        uptaxiCount.setText(dashBoardData.get(i).getUcTaxiBookingsCount().toString());
                        upbusCount.setText(dashBoardData.get(i).getUcBusBookingsCount().toString());
                        uptrainCount.setText(dashBoardData.get(i).getUcTrainBookingsCount().toString());
                        uphotelCount.setText(dashBoardData.get(i).getUcHotelBookingsCount().toString());
                        upflightCount.setText(dashBoardData.get(i).getUcFlightBookingsCount().toString());

                    }

                }
            }
        });


    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.drawer_home) {
            Log.d(TAG, "HOME");
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            // CustomIntent.customType(HomeActivity.this,"fadein-to-fadeout");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            Log.d(TAG, "Profile");
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("type", "cabs");
            startActivity(intent);
            //finish();

        } else if (id == R.id.nav_support) {
            Log.d(TAG, "support");
            Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
            intent.putExtra("type", "train");
            startActivity(intent);
        } else if (id == R.id.nav_aboutus) {
            Log.d(TAG, "about us");
            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            intent.putExtra("type", "train");
            startActivity(intent);
        } else if (id == R.id.nav_sign_out) {


            final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

            mDialogTitile.setText("Are You Sure ?");
            mDialogmsg.setText("Please Press Yes to Logout");

            Button myes = dialog.findViewById(R.id.yes_txt);
            Button mNo = dialog.findViewById(R.id.no_txt);

            myes.setText("Yes");
            myes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewSpocViewModel.performLogout("Token " + token, spocId, HomeActivity.this);
                    dialog.dismiss();
                }
            });
            mNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        /*    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this, R.style.CustomDialogTheme);
            alertDialogBuilder.setTitle("Wish To Logout From App ?");
            alertDialogBuilder.setIcon(R.drawable.logout);
            // alertDialogBuilder.setMessage("Are You Sure ?");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                    viewSpocViewModel.performLogout("Token " + token, spocId, HomeActivity.this);

                }
            });
            alertDialogBuilder.setNegativeButton(
                    "Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = alertDialogBuilder.create();
            alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
            alert11.show();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }

    private void profileDetails() {
        userName.setText(spocName);
        userEmailId.setText(spocEmail);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (isNetworkConnected()) {

            viewSpocViewModel.viewSpoc("Token " + token, spocId);
            dashBoardViewModel.initViewModelDashBoard(Authorization, userType, spocId);
        } else {
            final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
            mDialogmsg.setText("Check Your Internet Connection.");

            Button myes = dialog.findViewById(R.id.yes_txt);
            Button mNo = dialog.findViewById(R.id.no_txt);

            mNo.setVisibility(View.GONE);

            myes.setText("Okey");
            myes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            progressDialog.dismiss();
            networkError.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    private void askPhoneCallPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL
            );
        } else {

           // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CALL
            );
        } else {

            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                askPhoneCallPermission();
            } else {

                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
