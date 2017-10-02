package com.example.valentino.enerlet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MylifeActivity extends AppCompatActivity implements View.OnClickListener {

    String user;
    Button submit;
    Spinner diet_select;
    Spinner car_select;
    Spinner mileage_select;
    Spinner public_select;
    Spinner flight_select;
    int diet_pos;
    int car_pos;
    int mileage_pos;
    int public_pos;
    int flight_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylife);
        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("My Life");
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
        bottomNavigation.setCurrentItem(4);

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
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), TripActivity.class);
                        intent2.putExtra("curr_user", user);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), ActionActivity.class);
                        intent3.putExtra("curr_user", user);
                        startActivity(intent3);
                        break;
                    case 4:
                        break;
                }
            }
        });

        Intent intent = getIntent();
        user = intent.getStringExtra("curr_user");

        SharedPreferences user_prefs = getSharedPreferences(user, Context.MODE_PRIVATE);
        diet_pos = user_prefs.getInt("diet", -1);
        car_pos = user_prefs.getInt("car", -1);
        mileage_pos = user_prefs.getInt("mileage", -1);
        public_pos = user_prefs.getInt("public", -1);
        flight_pos = user_prefs.getInt("flight", -1);

        submit = (Button) findViewById(R.id.mylife_submit);
        submit.setOnClickListener(this);

        diet_select = (Spinner) findViewById(R.id.diet_select);
        ArrayAdapter<CharSequence> diet_adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.support_simple_spinner_dropdown_item);
        diet_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diet_select.setAdapter(diet_adapter);
        diet_select.setSelection(diet_pos);

        car_select = (Spinner) findViewById(R.id.car_select);
        ArrayAdapter<CharSequence> car_adapter = ArrayAdapter.createFromResource(this, R.array.car_array, R.layout.support_simple_spinner_dropdown_item);
        car_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        car_select.setAdapter(car_adapter);
        car_select.setSelection(car_pos);

        mileage_select = (Spinner) findViewById(R.id.milage_select);
        ArrayAdapter<CharSequence> milage_adapter = ArrayAdapter.createFromResource(this, R.array.milage_array, R.layout.support_simple_spinner_dropdown_item);
        milage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mileage_select.setAdapter(milage_adapter);
        mileage_select.setSelection(mileage_pos);

        public_select = (Spinner) findViewById(R.id.public_select);
        ArrayAdapter<CharSequence> public_adapter = ArrayAdapter.createFromResource(this, R.array.public_array, R.layout.support_simple_spinner_dropdown_item);
        public_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        public_select.setAdapter(public_adapter);
        public_select.setSelection(public_pos);

        flight_select = (Spinner) findViewById(R.id.flight_select);
        ArrayAdapter<CharSequence> flight_adapter = ArrayAdapter.createFromResource(this, R.array.flight_array, R.layout.support_simple_spinner_dropdown_item);
        flight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flight_select.setAdapter(flight_adapter);
        flight_select.setSelection(flight_pos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mylife_submit) {
            diet_pos = diet_select.getSelectedItemPosition();
            car_pos = car_select.getSelectedItemPosition();
            mileage_pos = mileage_select.getSelectedItemPosition();
            public_pos = public_select.getSelectedItemPosition();
            flight_pos = flight_select.getSelectedItemPosition();

            int diet_carbon = 0;

            if (diet_pos == 0)
                    diet_carbon = 7275;
            else if (diet_pos == 1)
                    diet_carbon = 5511;
            else if (diet_pos == 2)
                    diet_carbon = 4189;
            else if (diet_pos == 3)
                    diet_carbon = 3748;
            else if (diet_pos == 4)
                    diet_carbon = 3307;

            int car_carbon = 0;

            if (car_pos == 0)
                car_carbon = 0;
            else if (car_pos == 1 && mileage_pos == 0)
                car_carbon = 2866;
            else if (car_pos == 1 && mileage_pos == 1)
                car_carbon = 7275;
            else if (car_pos == 1 && mileage_pos == 2)
                car_carbon = 14771;
            else if (car_pos == 1 && mileage_pos == 3)
                car_carbon = 25251;
            else if (car_pos == 2 && mileage_pos == 0)
                car_carbon = 4409;
            else if (car_pos == 2 && mileage_pos == 1)
                car_carbon = 10803;
            else if (car_pos == 2 && mileage_pos == 2)
                car_carbon = 21605;
            else if (car_pos == 2 && mileage_pos == 3)
                car_carbon = 35274;
            else if (car_pos == 3 && mileage_pos == 0)
                car_carbon = 5071;
            else if (car_pos == 3 && mileage_pos == 1)
                car_carbon = 12346;
            else if (car_pos == 3 && mileage_pos == 2)
                car_carbon = 24251;
            else if (car_pos == 3 && mileage_pos == 3)
                car_carbon = 41887;
            else if (car_pos == 4 && mileage_pos == 0)
                car_carbon = 5732;
            else if (car_pos == 4 && mileage_pos == 1)
                car_carbon = 14550;
            else if (car_pos == 4 && mileage_pos == 2)
                car_carbon = 28660;
            else if (car_pos == 4 && mileage_pos == 3)
                car_carbon = 48501;

            int public_carbon = 0;

            if (public_pos == 0)
                public_carbon = 0;
            else if (public_pos == 1)
                public_carbon = 285;
            else if (public_pos == 2)
                public_carbon = 713;
            else if (public_pos == 3)
                public_carbon = 1425;
            else if (public_pos == 4)
                public_carbon = 2850;
            else if (public_pos == 5)
                public_carbon = 4750;


            int flight_carbon = 0;

            if (flight_pos == 0)
                flight_carbon = 1236;
            else if (flight_pos == 1)
                flight_carbon = 3708;
            else if (flight_pos == 2)
                flight_carbon = 6180;
            else if (flight_pos == 3)
                flight_carbon = 8652;
            else if (flight_pos == 4)
                flight_carbon = 11948;

            int total_carbon = diet_carbon + car_carbon + public_carbon + flight_carbon + 11464 + 12787;
            int total_trees = total_carbon / 911;

            SharedPreferences user_prefs = getSharedPreferences(user, Context.MODE_PRIVATE);
            SharedPreferences.Editor user_editor = user_prefs.edit();

            user_editor.putInt("diet", diet_pos);
            user_editor.putInt("car", car_pos);
            user_editor.putInt("mileage", mileage_pos);
            user_editor.putInt("public", public_pos);
            user_editor.putInt("flight", flight_pos);
            user_editor.putInt("total_carbon", total_carbon);
            user_editor.putInt("total_trees", total_trees);
            user_editor.commit();
            Toast.makeText(MylifeActivity.this, "Submitted!", Toast.LENGTH_SHORT).show();
        }
    }


}
