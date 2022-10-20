package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.activities_viewer.LoginScreenActivity;
import com.example.myapplication.activities_viewer.OnBoardingActivity;
import com.example.myapplication.check_is_first_install_app.DataLocalManagement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( !DataLocalManagement.GetFirstInstall() ) {
//            Toast.makeText(this, "This is the first install app!", Toast.LENGTH_LONG).show();

            // Tao man hinh cho trong vong 7s
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getBaseContext(), OnBoardingActivity.class));
                DataLocalManagement.SetFirstInstall(true);
            }, 7000);

        } else {
//            Toast.makeText(this, "This is not the first install app!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> startActivity(new Intent(getBaseContext(), LoginScreenActivity.class)), 7000);
        }
    }
}