package com.example.myapplication.viewpager_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment_tab_ad.ActorFragment;
import com.example.myapplication.fragment_tab_ad.DirectorFragment;
import com.example.myapplication.fragment_tab_ad.FilmFragment;

public class TabAdAdapter extends FragmentStateAdapter {
    public TabAdAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new  ActorFragment();
            } case 1: {
                return new DirectorFragment();
            } case 2: {
                return new FilmFragment();
            } default: {
                return new  ActorFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
