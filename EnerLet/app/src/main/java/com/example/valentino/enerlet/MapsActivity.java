package com.example.valentino.enerlet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    String user;
    private GoogleMap mMap;
    String origin;
    String dest;
    LatLng origin_ll = null; // A list to save the coordinates if they are available
    LatLng dest_ll = null;
    ImageButton driving_button;
    ImageButton walking_button;
    ImageButton biking_button;
    ImageButton transit_button;
    String transport_mode = TransportMode.DRIVING;
    String duration;
    String distance;

    TextView driving_duration_text;
    TextView walking_duration_text;
    TextView biking_duration_text;
    TextView transit_duration_text;
    TextView driving_carbon_text;
    TextView walking_carbon_text;
    TextView biking_carbon_text;
    TextView transit_carbon_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("Greenest Trip");
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
        bottomNavigation.setCurrentItem(2);

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        origin = intent.getStringExtra("ORIGIN");
        dest = intent.getStringExtra("DEST");

        try {
            Geocoder gc = new Geocoder(this);
            List<Address> origin_add= gc.getFromLocationName(origin, 1); // get the found Address Objects
            for(Address a : origin_add){
                if(a.hasLatitude() && a.hasLongitude()){
                    origin_ll = new LatLng(a.getLatitude(), a.getLongitude());
                }
            }
            List<Address> dest_add= gc.getFromLocationName(dest, 1); // get the found Address Objects
            for(Address a : dest_add){
                if(a.hasLatitude() && a.hasLongitude()){
                    dest_ll = new LatLng(a.getLatitude(), a.getLongitude());
                }
            }
        } catch (IOException e) {
            // handle the exception
        }

        driving_duration_text = (TextView) findViewById(R.id.drive_duration);
        driving_carbon_text = (TextView) findViewById(R.id.drive_carbon);
        getInfo(TransportMode.DRIVING);

        walking_duration_text = (TextView) findViewById(R.id.walk_duration);
        walking_carbon_text = (TextView) findViewById(R.id.walk_carbon);
        getInfo(TransportMode.WALKING);

        biking_duration_text = (TextView) findViewById(R.id.bike_duration);
        biking_carbon_text = (TextView) findViewById(R.id.bike_carbon);
        getInfo(TransportMode.BICYCLING);

        transit_duration_text = (TextView) findViewById(R.id.transit_duration);
        transit_carbon_text = (TextView) findViewById(R.id.transit_carbon);
        getInfo(TransportMode.TRANSIT);


        String route = "Route: '" + origin + "'  to  '" + dest + "'";
        TextView put_route = (TextView) findViewById(R.id.route);
        put_route.setText(route);

        driving_button = (ImageButton) findViewById(R.id.driving);
        driving_button.setOnClickListener(this);
        walking_button = (ImageButton) findViewById(R.id.walking);
        walking_button.setOnClickListener(this);
        biking_button = (ImageButton) findViewById(R.id.biking);
        biking_button.setOnClickListener(this);
        transit_button = (ImageButton) findViewById(R.id.transit);
        transit_button.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getInfo(final String mode) {
        String serverKey = "AIzaSyBqxH0vJw2N855LbIgKs5pxTO8OKFIduew";
        GoogleDirection.withServerKey(serverKey)
                .from(origin_ll)
                .to(dest_ll)
                .transportMode(mode)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            duration = leg.getDuration().getText();
                            distance = leg.getDistance().getText();
                            String[] temp = distance.split(" ");
                            int carbon = Integer.valueOf(temp[0]);

                            switch (mode) {
                                case TransportMode.DRIVING:
                                    driving_duration_text.setText(duration);
                                    driving_carbon_text.setText(String.format("%.2f",Math.pow(carbon, 0.9042)));
                                    break;
                                case TransportMode.WALKING:
                                    walking_duration_text.setText(duration);
                                    walking_carbon_text.setText(String.valueOf(carbon * 0));
                                    break;
                                case TransportMode.BICYCLING:
                                    biking_duration_text.setText(duration);
                                    biking_carbon_text.setText(String.valueOf(carbon * 0));
                                    break;
                                case TransportMode.TRANSIT:
                                    transit_duration_text.setText(duration);
                                    transit_carbon_text.setText(String.format("%.2f", Math.pow(carbon, 0.19)));
                                    break;
                            }



                        } else {
                            Toast.makeText(MapsActivity.this, status, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setMap() {
        String serverKey = "AIzaSyBqxH0vJw2N855LbIgKs5pxTO8OKFIduew";
        GoogleDirection.withServerKey(serverKey)
                .from(origin_ll)
                .to(dest_ll)
                .transportMode(transport_mode)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(origin_ll).title("Origin"));
                            mMap.addMarker(new MarkerOptions().position(dest_ll).title("Destination"));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLng(origin_ll));
                            if(origin_ll.latitude < dest_ll.latitude) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(origin_ll, dest_ll), 150));
                            }
                            else {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(dest_ll, origin_ll), 150));
                            }
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> pointList = leg.getDirectionPoint();

                            Info durationInfo = leg.getDuration();
                            String duration = durationInfo.getText();

                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED);
                            mMap.addPolyline(polylineOptions);

                        } else {
                            Toast.makeText(MapsActivity.this, status, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.driving) {
            transport_mode = TransportMode.DRIVING;
            setMap();
        }
        if (v.getId() == R.id.walking) {
            transport_mode = TransportMode.WALKING;
            setMap();
        }
        if (v.getId() == R.id.biking) {
            transport_mode = TransportMode.BICYCLING;
            setMap();
        }
        if (v.getId() == R.id.transit) {
            transport_mode = TransportMode.TRANSIT;
            setMap();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        setMap();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                char mode_flag = 'd';
                switch (transport_mode) {
                    case TransportMode.DRIVING:
                        mode_flag = 'd';
                        break;
                    case TransportMode.WALKING:
                        mode_flag = 'w';
                        break;
                    case TransportMode.BICYCLING:
                        mode_flag = 'b';
                        break;
                    case TransportMode.TRANSIT:
                        mode_flag = 'r';
                        break;
                }
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f&dirflg=%c", origin_ll.latitude, origin_ll.longitude, dest_ll.latitude, dest_ll.longitude, mode_flag);
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

//                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}
