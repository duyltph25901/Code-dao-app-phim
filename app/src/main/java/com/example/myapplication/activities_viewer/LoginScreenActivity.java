package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoginScreenActivity extends AppCompatActivity {
    private Button btnLogin, btnSignUp;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();

        setTextColor(txtTitle,
                getResources().getColor(R.color.color_second_primary),
                getResources().getColor(R.color.color_primary));

        setEventForButton();
    }

    private void init() {
        btnLogin = findViewById(R.id.btnShowLogin);
        btnSignUp = findViewById(R.id.btnShowSignUp);
        txtTitle = findViewById(R.id.txtTitleApp);
    }

    private void setTextColor(TextView txtView, int... color) {
        TextPaint textPaint = txtView.getPaint();
        float width = textPaint.measureText(txtView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, txtView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        txtView.getPaint().setShader(shader);
        txtView.setTextColor(color[0]);
    }

    private void setEventForButton() {
        btnLogin.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), LoginActivity.class)));
        btnSignUp.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), SignUpActivity.class)));
    }
}