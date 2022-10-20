package com.example.myapplication.viewpager_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment_on_boarding.OneFragment;
import com.example.myapplication.fragment_on_boarding.ThreeFragment;
import com.example.myapplication.fragment_on_boarding.TwoFragment;

public class OnBoardingAdapter extends FragmentStateAdapter {
    public OnBoardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new OneFragment();
            } case 1: {
                return new TwoFragment();
            } case 2: {
                return new ThreeFragment();
            } default: {
                return new OneFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
