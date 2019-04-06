package com.example.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class adapterPager extends FragmentPagerAdapter {

    public adapterPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ListFragment listFragment = new ListFragment();
        position+=1;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment :"+position);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
