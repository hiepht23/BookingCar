package com.vnpt.media.bookingcar.function.bookingcar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vnpt.media.bookingcar.function.bookingcar.fragment.CarListFragment;
import com.vnpt.media.bookingcar.function.bookingcar.fragment.EmployeeListFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new EmployeeListFragment();
            case 1:
                return new CarListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
