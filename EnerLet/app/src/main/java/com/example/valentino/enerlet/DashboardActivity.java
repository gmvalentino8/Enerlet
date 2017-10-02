package com.example.valentino.enerlet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


public class DashboardActivity extends AppCompatActivity {

    String user;
    private ColumnChartView user_chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = 0;
    private int user_carbon;
    private int avg_carbon = 57763;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("Dashboard");
        NavBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        NavBar.getOverflowIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

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
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
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
                        Intent intent3 = new Intent(getApplicationContext(), ActionActivity.class);
                        intent3.putExtra("curr_user", user);
                        startActivity(intent3);
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

        SharedPreferences user_prefs = getSharedPreferences(user, Context.MODE_PRIVATE);
        user_carbon = user_prefs.getInt("total_carbon", -1);

        TextView my_fp = (TextView) findViewById(R.id.my_footprint_value);
        TextView avg_fp = (TextView) findViewById(R.id.avg_footprint_value);
        TextView message = (TextView) findViewById(R.id.rating_text);

        my_fp.setText(String.valueOf(user_carbon));
        avg_fp.setText(String.valueOf(avg_carbon));

        if(user_carbon < 0.7 * avg_carbon) {
            message.setText("You are using a lot less than average! You are doing great!");
        }
        else if (user_carbon < avg_carbon) {
            message.setText("You are using a bit less than average! You are doing good!");

        }
        else if (user_carbon > 1.3 * avg_carbon) {
            message.setText("You are using a bit more than average! Try harder to decrease your footprint!");
        }
        else {
            message.setText("You are using a lot more than average! Starting taking action to reduce your footprint!");

        }

        user_chart = (ColumnChartView) findViewById(R.id.chart);

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        values = new ArrayList<SubcolumnValue>();
        values.add(new SubcolumnValue((float) user_carbon, ChartUtils.pickColor()));
        Column column = new Column(values);
        column.setHasLabels(hasLabels);
        column.setHasLabelsOnlyForSelected(hasLabelForSelected);
        columns.add(column);

        values = new ArrayList<SubcolumnValue>();
        values.add(new SubcolumnValue((float) avg_carbon, ChartUtils.pickColor()));
        column = new Column(values);
        column.setHasLabels(hasLabels);
        column.setHasLabelsOnlyForSelected(hasLabelForSelected);
        columns.add(column);

        data = new ColumnChartData(columns);

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        axisValues.add(new AxisValue(0, "Your Footprint".toCharArray()));
        axisValues.add(new AxisValue(1, "Average Footprint".toCharArray()));

        if (hasAxes) {
            Axis axisX = new Axis();
            axisX.setValues(axisValues);
            data.setAxisXBottom(axisX);
        } else {
            data.setAxisXBottom(null);
        }

        user_chart.setColumnChartData(data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
