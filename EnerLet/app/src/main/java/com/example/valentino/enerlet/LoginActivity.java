package com.example.valentino.enerlet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_button;
    private Button register_button;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("Login");
        NavBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        NavBar.getOverflowIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        login_button = (Button) findViewById(R.id.login);
        login_button.setOnClickListener(this);
        register_button = (Button) findViewById(R.id.register);
        register_button.setOnClickListener(this);
        email = (EditText) findViewById(R.id.email_login_entry);
        password = (EditText) findViewById(R.id.password_login_entry);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login) {
            String user = email.getText().toString();
            SharedPreferences user_prefs = getSharedPreferences(user, Context.MODE_PRIVATE);

            if (TextUtils.isEmpty(user) || !user_prefs.contains("password")) {
                email.setError("This Email is not Registered");
            }
            else if (!user_prefs.getString("password", "").equals(password.getText().toString())) {
                password.setError("Password is Incorrect");
            }
            else {
                Intent login = new Intent(getApplicationContext(), DashboardActivity.class);
                login.putExtra("curr_user", user);
                startActivity(login);
            }
        }
        if(v.getId() == R.id.register) {
            Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(register);
        }
    }
}
