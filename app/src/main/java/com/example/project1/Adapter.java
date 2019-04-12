package com.example.project1;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitle = new ArrayList<>();


    public void addFragment(Fragment fragment, String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitle.add(title);
    }

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
