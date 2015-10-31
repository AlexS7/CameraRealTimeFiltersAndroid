package com.apps.alexs7.pointop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.alexs7.pointop.R;

public class OptionsViewPagerFragment extends Fragment {

    OptionsViewPagerAdapter pageAdapter;

    public OptionsViewPagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options_view_pager, container, false);
    }

    @Override
    public void onStart(){
        OptionsViewPagerAdapter optionsViewPagerAdapter = new OptionsViewPagerAdapter(getFragmentManager());
        ViewPager vpPager = (ViewPager) getView().findViewById(R.id.viewpager);
        vpPager.setAdapter(optionsViewPagerAdapter);
    }

    public static class OptionsViewPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public OptionsViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    //return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    //return FirstFragment.newInstance(1, "Page # 2");
                default:
                    return null;
            }
        }

    }
}
