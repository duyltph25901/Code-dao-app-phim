
package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db_user.UserDatabase;
import com.example.myapplication.model.UserApp;
import com.example.myapplication.viewpager_adapter.LayoutContentAdapter;

public class HomeActivity extends AppCompatActivity {
    private TextView txtUserName;
    private ViewPager2 layoutContent;
    private TextView txtHome, txtFavorite, txtPerson;
    private ImageView imgHome, imgFavorite, imgPerson;
    private LinearLayout layoutHome, layoutFavorite, layoutPerson;

    private static final int TAB_HOME = 0;
    private static final int TAB_FAVORITE = 1;
    private static final int TAB_PERSON = 2;
    private int currentTab = TAB_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//         get data from login activity
        savedInstanceState = this.getIntent().getExtras();
        String value = (String) savedInstanceState.get(LoginActivity.BUNDLE_KEY);
//        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        if (value.isEmpty()) return;
        UserApp userApp = UserDatabase.getInstance(this).userDao().searchByEmail(value);
//        txtUserName.setText(userApp.getName());
        Toast.makeText(this, "Hello "+userApp.getName(), Toast.LENGTH_SHORT).show();

        init();
        layoutPerson.setOnClickListener(v -> replaceTabPerson());
        layoutHome.setOnClickListener(v -> replaceTabHome());
        layoutFavorite.setOnClickListener(v -> replaceTabFavorite());
    }

    private void init() {
        txtUserName = findViewById(R.id.txtUserAppName);
        layoutContent = findViewById(R.id.layoutContent);
        txtHome = findViewById(R.id.txtViewHome);
        txtFavorite = findViewById(R.id.txtFavorite);
        txtPerson = findViewById(R.id.txtPerson);
        imgHome = findViewById(R.id.imgHome);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgPerson = findViewById(R.id.imgPerson);
        layoutHome = findViewById(R.id.layoutHome);
        layoutFavorite = findViewById(R.id.layoutFavorite);
        layoutPerson = findViewById(R.id.layoutPerson);

        LayoutContentAdapter adapter = new LayoutContentAdapter(HomeActivity.this);
        layoutContent.setAdapter(adapter);
        layoutContent.setUserInputEnabled(false);
    }

    private void replaceTabHome() {
        if (currentTab != TAB_HOME) {
            currentTab = TAB_HOME;

            // unselected other tab
            txtFavorite.setVisibility(View.GONE);
            txtPerson.setVisibility(View.GONE);

            layoutFavorite.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutPerson.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab home
            txtHome.setVisibility(View.VISIBLE);
            layoutHome.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutHome.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(0);
        }
    }

    private void replaceTabFavorite() {
        if (currentTab != TAB_FAVORITE) {
            currentTab = TAB_FAVORITE;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtPerson.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutPerson.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab home
            txtFavorite.setVisibility(View.VISIBLE);
            layoutFavorite.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutFavorite.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(1);
        }
    }

    private void replaceTabPerson() {
        if (currentTab != TAB_PERSON) {
            currentTab = TAB_PERSON;

            // unselected other tab
            txtHome.setVisibility(View.GONE);
            txtFavorite.setVisibility(View.GONE);

            layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutFavorite.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // selected tab home
            txtPerson.setVisibility(View.VISIBLE);
            layoutPerson.setBackgroundResource(R.drawable.rounded_bottom_bar);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutPerson.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(2);
        }
    }
}