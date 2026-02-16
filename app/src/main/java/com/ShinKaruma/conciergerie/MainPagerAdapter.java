package com.ShinKaruma.conciergerie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(AppCompatActivity activity){super(activity);}

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CalendarFragment();
            case 1:
                return new LocationFragment();
            default:
                return new AppartementsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
