package com.example.myapplication.activity_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.activities_viewer.LoginScreenActivity;
import com.example.myapplication.fragment_ad.ActorFragment;
import com.example.myapplication.fragment_ad.DirectorFragment;
import com.example.myapplication.fragment_ad.FilmFragment;
import com.example.myapplication.fragment_ad.ListFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeAdActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private NavigationView nav;
    private Toolbar toolbar;

    private static final int ACTOR_FRAGMENT = 0;
    private static final int DIRECTOR_FRAGMENT = 1;
    private static final int FILM_FRAGMENT = 2;
    private static final int LIST_FRAGMENT = 3;
    private static final int LOGOUT = 4;
    private int currentIndex = ACTOR_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_home);

        init();

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openNav, R.string.closeNav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(HomeAdActivity.this);

        setDefaultFragment();
    }

    private void setDefaultFragment () {
        replaceFragment(new ActorFragment());
        currentIndex = ACTOR_FRAGMENT;
        nav.getMenu().findItem(R.id.itemActor).setChecked(true);
    }

    private void init() {
        drawer = findViewById(R.id.layoutDrawer);
        nav = findViewById(R.id.nav);
        toolbar = findViewById(R.id.toolBarAd);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemActor: {
                toolbar.setVisibility(View.VISIBLE);
                replaceFragment(new ActorFragment());
                drawer.closeDrawer(GravityCompat.START);
                break;
            } case R.id.itemDirector: {
                toolbar.setVisibility(View.VISIBLE);
                replaceFragment(new DirectorFragment());
                drawer.closeDrawer(GravityCompat.START);
                break;
            } case R.id.itemFilm: {
                toolbar.setVisibility(View.VISIBLE);
                replaceFragment(new FilmFragment());
                drawer.closeDrawer(GravityCompat.START);
                break;
            } case R.id.itemLogout: {
                drawer.closeDrawer(GravityCompat.START);
                logout();
                break;
            } case R.id.itemList: {
                replaceFragment(new ListFragment());
                toolbar.setVisibility(View.GONE);
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Exit confirmation")
                .setMessage("Do you want to logout?")
                .setNegativeButton("Yes", (dialog, which) -> {
                    Intent i = new Intent(getBaseContext(), LoginScreenActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                })
                .setPositiveButton("No", null)
                .show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutContentAd, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}