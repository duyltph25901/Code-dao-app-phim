package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db_user.UserDatabase;
import com.example.myapplication.model.UserApp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private LinearLayout layoutBack;
    private EditText inputName, inputEmail, inputPass;
    private Button btnSignUp;
    private TextView txtLogin;
    
    private List<UserApp> userApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        layoutBack.setOnClickListener(v -> backToMainActivity());
        txtLogin.setOnClickListener(v -> goToLoginActivity());
        btnSignUp.setOnClickListener(v -> signUp());
    }

    private void init() {
        layoutBack = findViewById(R.id.btnBackSignUp);
        inputName = findViewById(R.id.inputNameSignUp);
        inputEmail = findViewById(R.id.inputEmailSignUp);
        inputPass = findViewById(R.id.inputPassSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
    }

    private void backToMainActivity() {
        layoutBack.setOnClickListener(v -> {
            Intent i=new Intent(getBaseContext(), LoginScreenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    private void goToLoginActivity() {
        txtLogin.setOnClickListener(v -> {
            Intent i=new Intent(getBaseContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    private void signUp() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();

        boolean isNullInput = isNullInput(name, email, pass);
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        } // Check null data
        boolean isEmail = checkValidateEmail(email);
        if (!isEmail) {
            Toast.makeText(this, "Email invalidate!", Toast.LENGTH_SHORT).show();

            return;
        } // Check validate email
        if (pass.length() < 6) {
            Toast.makeText(this, "Weak password, please change another password!", Toast.LENGTH_SHORT).show();

            return;
        } // Check pass strong or weak

        // Check email is registered
        userApps = UserDatabase.getInstance(getBaseContext()).userDao().searchUserByEmail(email);
        int isRegister = 0;
        for (UserApp user : userApps) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                ++isRegister;
                break;
            }
        }
        if (isRegister != 0) {
            Toast.makeText(this, "Email is registered!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Add data
        UserApp user = new UserApp(name, email, pass);
        UserDatabase.getInstance(this).userDao().insert(user);
        clearForm();
        Toast.makeText(this, "Successfully insertion!", Toast.LENGTH_SHORT).show();
    }

    private boolean isNullInput(String... input) {
        boolean flag = false;

        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    private boolean checkValidateEmail(String input){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private void clearForm() {
        inputPass.setText("");
        inputEmail.setText("");
        inputName.setText("");
    }
}