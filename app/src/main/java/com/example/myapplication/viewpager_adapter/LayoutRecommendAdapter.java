package com.example.myapplication.viewpager_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment_list_recommend.ActionFragment;
import com.example.myapplication.fragment_list_recommend.AnimatedFragment;
import com.example.myapplication.fragment_list_recommend.HorroFragment;
import com.example.myapplication.fragment_list_recommend.SciFragment;

public class LayoutRecommendAdapter extends FragmentStateAdapter {
    public LayoutRecommendAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new ActionFragment();
            } case 1: {
                return new AnimatedFragment();
            } case 2: {
                return new HorroFragment();
            } case 3: {
                return new SciFragment();
            } default: {
                return new ActionFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
