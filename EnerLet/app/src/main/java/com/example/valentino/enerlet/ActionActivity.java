package com.example.valentino.enerlet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class ActionActivity extends AppCompatActivity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("Action");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        NavBar.getOverflowIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        NavBar.getNavigationIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem home = new AHBottomNavigationItem("Home", R.drawable.ic_home_white_18dp, R.color.colorPrimary);
        AHBottomNavigationItem footprint = new AHBottomNavigationItem("Footprint", R.drawable.ic_person_white_18dp, R.color.colorPrimary);
        AHBottomNavigationItem trip = new AHBottomNavigationItem("Trip", R.drawable.ic_map_white_18dp, R.color.colorPrimary);
        AHBottomNavigationItem action = new AHBottomNavigationItem("Action", R.drawable.ic_check_circle_white_18dp, R.color.colorPrimary);
        AHBottomNavigationItem mylife = new AHBottomNavigationItem("My Life", R.drawable.ic_public_white_18dp, R.color.colorPrimary);
        bottomNavigation.addItem(home);
        bottomNavigation.addItem(footprint);
        bottomNavigation.addItem(trip);
        bottomNavigation.addItem(action);
        bottomNavigation.addItem(mylife);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#1565c0"));
        bottomNavigation.setAccentColor(Color.parseColor("#FFA154"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setCurrentItem(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent0.putExtra("curr_user", user);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), FootprintActivity.class);
                        intent1.putExtra("curr_user", user);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), TripActivity.class);
                        intent2.putExtra("curr_user", user);
                        startActivity(intent2);
                        break;
                    case 3:
                        break;
                    case 4:
                        Intent intent4 = new Intent(getApplicationContext(), MylifeActivity.class);
                        intent4.putExtra("curr_user", user);
                        startActivity(intent4);
                        break;
                }
            }
        });

        Intent intent = getIntent();
        user = intent.getStringExtra("curr_user");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }}
