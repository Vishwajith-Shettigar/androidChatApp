package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {

            case 0:
                return  new Chatfragment();

            case 1:
                return  new Statusfragment();


            case 2:
                return  new callfragment();






        }
        return  new Chatfragment();
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
