package com.cotrav.cotravspoc.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotravspoc.fragments.showflight.CancelledFlightFragment;
import com.cotrav.cotravspoc.fragments.showflight.CompletedFlightFragment;
import com.cotrav.cotravspoc.fragments.showflight.TodaysFlightFragment;
import com.cotrav.cotravspoc.fragments.showflight.UpcomingFlightFragment;

public class FlightTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;


    public FlightTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysFlightFragment();
                break;
            case 1:
                tab = new UpcomingFlightFragment();
                break;
            case 2:
                tab = new CompletedFlightFragment();
                break;
            case 3:
                tab = new CancelledFlightFragment();
                break;
            default:
                return null;

        }
        return tab;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
