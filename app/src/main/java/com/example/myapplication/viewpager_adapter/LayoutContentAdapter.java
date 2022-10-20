package com.example.myapplication.viewpager_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment_viewer.FavoriteFragment;
import com.example.myapplication.fragment_viewer.HomeFragment;
import com.example.myapplication.fragment_viewer.ProfileFragment;

public class LayoutContentAdapter extends FragmentStateAdapter {
    public LayoutContentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new HomeFragment();
            } case 1: {
                return new FavoriteFragment();
            } case 2: {
                return new ProfileFragment();
            } default: {
                return new HomeFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
