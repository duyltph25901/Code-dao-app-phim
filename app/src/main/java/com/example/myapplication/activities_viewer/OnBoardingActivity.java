package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.viewpager_adapter.OnBoardingAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class OnBoardingActivity extends AppCompatActivity {
    private TextView txtSkip;
    private ViewPager2 viewPager;
    private CircleIndicator3 circleIndicator;
    private ImageButton btnNext;
    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        init();
        setTextColor(txtSkip,
                getResources().getColor(R.color.color_second_primary),
                getResources().getColor(R.color.color_primary));

        btnNext.setOnClickListener(v -> nextSlide());
        txtSkip.setOnClickListener(v -> skip());
        btnGetStarted.setOnClickListener(v -> getStarted());
    }

    private void init() {
        txtSkip = findViewById(R.id.txtSkipOnBoarding);
        viewPager = findViewById(R.id.layoutContentBoarding);
        circleIndicator = findViewById(R.id.circleIndicatorOnBoarding);
        btnNext = findViewById(R.id.btnNextOnBoarding);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        OnBoardingAdapter adapter = new OnBoardingAdapter(this);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void setTextColor(TextView txtView, int... color) {
        TextPaint textPaint = txtView.getPaint();
        float width = textPaint.measureText(txtView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, txtView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        txtView.getPaint().setShader(shader);
        txtView.setTextColor(color[0]);
    }

    private void nextSlide() {
        int currentIndex = viewPager.getCurrentItem();
        if (currentIndex >= 2) {
            currentIndex = 0;
            viewPager.setCurrentItem(currentIndex);
        } else {
            ++currentIndex;
            viewPager.setCurrentItem(currentIndex);
        }
    }

    private void skip() {
        viewPager.setCurrentItem(2);
    }

    private void getStarted() {
        startActivity(new Intent(getBaseContext(), LoginScreenActivity.class));
    }
}