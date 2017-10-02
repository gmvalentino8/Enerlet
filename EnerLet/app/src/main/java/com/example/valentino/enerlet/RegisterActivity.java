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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button create_button;
    EditText email;
    EditText password;
    EditText confpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar NavBar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(NavBar);
        setTitle("Register");
        NavBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        NavBar.getOverflowIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        email = (EditText) findViewById(R.id.email_entry);
        password = (EditText) findViewById(R.id.password_entry);
        confpassword = (EditText) findViewById(R.id.confirm_password_entry);
        create_button = (Button) findViewById(R.id.create);
        create_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.create) {
            String email_text = email.getText().toString();
            String password_text = password.getText().toString();
            String confpassword_text = confpassword.getText().toString();
            boolean flag = true;
            if(!password_text.equals(confpassword_text)) {
                confpassword.setError("Passwords do not Match");
                flag = false;
            }
            if(TextUtils.isEmpty(email_text) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_text).matches()) {
                email.setError("Please Enter a Valid Email Address");
                flag = false;
            }
            else if (flag) {
                SharedPreferences user_prefs = getSharedPreferences(email_text, Context.MODE_PRIVATE);
                SharedPreferences.Editor user_editor = user_prefs.edit();

                if(user_prefs.contains("password")) {
                    email.setError("This Email Address is Already Taken");
                }
                else {
                    user_editor.putString("password", password_text);
                    user_editor.putInt("diet", 0);
                    user_editor.putInt("car", 0);
                    user_editor.putInt("mileage", 0);
                    user_editor.putInt("public", 0);
                    user_editor.putInt("flight", 0);
                    user_editor.putInt("total_carbon", (5512+28000+11464+12787));
                    user_editor.putInt("total_trees", (5512+28000+11464+12787)/911);
                    user_editor.commit();
                    Intent register = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(register);
                }
            }
        }
    }
}
