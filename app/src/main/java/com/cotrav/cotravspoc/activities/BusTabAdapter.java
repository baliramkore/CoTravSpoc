package com.cotrav.cotravspoc.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotravspoc.fragments.showbus.CancelledBusFragment;
import com.cotrav.cotravspoc.fragments.showbus.CompletedBusFragment;
import com.cotrav.cotravspoc.fragments.showbus.TodaysBusFragment;
import com.cotrav.cotravspoc.fragments.showbus.UpcomingBusFragment;


public class BusTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;

    public BusTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysBusFragment();
                break;
            case 1:
                tab = new UpcomingBusFragment();
                break;
            case 2:
                tab = new CompletedBusFragment();
                break;
            case 3:
                tab = new CancelledBusFragment();
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
