package com.example.fusion.map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  Button button;
    EditText phone,password;
    TextView reg;
    String userphone,userPassword,registeredphone,registeredPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button) findViewById(R.id.login);
        reg=(TextView) findViewById(R.id.textView);

        phone=(EditText)findViewById(R.id.nameLogin);
        password=(EditText)findViewById(R.id.passwordLogin);


        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userphone=phone.getText().toString();
                userPassword=password.getText().toString();

                registeredphone = sharedPref.getString(userPassword, "");
                registeredPassword = sharedPref.getString(userphone, "");


                Toast.makeText(MainActivity.this,"registeredName:"+registeredphone+"\nuserName:"+userphone+"\nrepass:"+registeredPassword+"\npass:"+password, Toast.LENGTH_LONG).show();

                if (registeredphone.equals(userphone) && registeredPassword.equals(userPassword))
                {
                    Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("Loggedin", true);
                    editor.apply();
                    finish();

                    Intent a=new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(a);
                }
                else
                Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regi=new Intent(MainActivity.this,Second.class);
                startActivity(regi);
            }
        });




    }
}
