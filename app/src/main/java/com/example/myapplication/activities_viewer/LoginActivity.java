package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db_user.UserDatabase;
import com.example.myapplication.model.UserApp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextView txtSignUp;
    private Button btnLogin;
    private EditText inputEmail, inputPass;
    private ImageButton imgBackLogin;

    public static final String BUNDLE_KEY = "userApp";

    private List<UserApp> userApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        imgBackLogin.setOnClickListener(v -> backToMainActivity());
        txtSignUp.setOnClickListener(v -> goToSignUpActivity());
        btnLogin.setOnClickListener(v -> login());
    }

    private void init() {
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.inputEmailLogin);
        inputPass = findViewById(R.id.inputPassLogin);
        imgBackLogin = findViewById(R.id.btnBackLogin);
    }

    private void backToMainActivity() {
        imgBackLogin.setOnClickListener(v -> {
            Intent i=new Intent(getBaseContext(), LoginScreenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    private void goToSignUpActivity() {
        txtSignUp.setOnClickListener(v -> {
            Intent i=new Intent(getBaseContext(), SignUpActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }
    
    private void login() {
        String email = inputEmail.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();

        boolean isNullDataInput = isNullDataInput(email, pass);
        if (isNullDataInput) {
            Toast.makeText(getBaseContext(), "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        } // Check null data input
        boolean isEmail = checkValidateEmail(email);
        if (!isEmail) {
            Toast.makeText(getBaseContext(), "Email invalidate!", Toast.LENGTH_SHORT).show();

            return;
        } // Check validate email

        // Check account is exit in database
        userApps = new ArrayList<>();
        userApps = UserDatabase.getInstance(getBaseContext()).userDao().searchUserByEmail(inputEmail.getText().toString());
        if (userApps.size() == 0) {
            Toast.makeText(LoginActivity.this, "Account information not found!", Toast.LENGTH_SHORT).show();
            
            return;
        }

        // Check account is true
        UserApp userApp = userApps.get(0);
        String passUserApp = userApp.getPass();
        boolean isTrueAccount = passUserApp.equalsIgnoreCase(pass);
        if (!isTrueAccount) {
            Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();

            return;
        }

        Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();

        // Send data
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, email);
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    
    private boolean isNullDataInput (String... input) {
        boolean flag = false;
        for (int i=0; i<input.length; i++) {
            if (input[0].isEmpty()) {
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
}