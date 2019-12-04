package com.cotrav.cotravspoc.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotravspoc.fragments.showtaxi.CancelledTaxiFragment;
import com.cotrav.cotravspoc.fragments.showtaxi.CompletedTaxiFragment;
import com.cotrav.cotravspoc.fragments.showtaxi.TodaysTaxiFragment;
import com.cotrav.cotravspoc.fragments.showtaxi.UpcomingTaxiFragment;


public class TaxiTabAdapter extends FragmentPagerAdapter
{
    private FragmentManager fragmentManager;
    private int tabCount;


    public TaxiTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysTaxiFragment();
                break;
            case 1:
                tab = new UpcomingTaxiFragment();
                break;
            case 2:
                tab = new CompletedTaxiFragment();
                break;
            case 3:
                tab = new CancelledTaxiFragment();
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
