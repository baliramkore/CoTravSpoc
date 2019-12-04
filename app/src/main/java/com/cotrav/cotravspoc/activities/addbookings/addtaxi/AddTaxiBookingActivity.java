package com.cotrav.cotravspoc.activities.addbookings.addtaxi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.adapter.ViewPagerAdapter;
import com.cotrav.cotravspoc.fragments.FragmentLocalTaxi;
import com.cotrav.cotravspoc.fragments.FragmentOutstationTaxi;
import com.cotrav.cotravspoc.fragments.FragmentRadioTaxi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddTaxiBookingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    Toolbar mtoolbar;
    MenuItem prevMenuItem;
FragmentLocalTaxi fragmentLocalTaxi;
FragmentOutstationTaxi fragmentOutstationTaxi;
FragmentRadioTaxi fragmentRadioTaxi;
@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taxi_booking);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_local:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_radio:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_outstation:
                        viewPager.setCurrentItem(2);
                        break;
                }

                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setupViewPager(viewPager);
        mtoolbar.setTitle("Add Taxi Booking");
        mtoolbar.setNavigationIcon(R.drawable.test_back);
        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentRadioTaxi = new FragmentRadioTaxi();
        fragmentLocalTaxi = new FragmentLocalTaxi();
        fragmentOutstationTaxi = new FragmentOutstationTaxi();
        viewPagerAdapter.addFragment(fragmentRadioTaxi);
        viewPagerAdapter.addFragment(fragmentLocalTaxi);
        viewPagerAdapter.addFragment(fragmentOutstationTaxi);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
