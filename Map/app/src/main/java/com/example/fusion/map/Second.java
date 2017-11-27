package com.example.fusion.map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Second extends AppCompatActivity {


    Button bb;
    EditText phone;
    EditText password;
    String userphone,userPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        bb=(Button) findViewById(R.id.signIn);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userphone=phone.getText().toString();
                userPassword=password.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Registered", true);
                editor.putString(userphone, userPassword);
                editor.putString(userPassword, userphone);
                editor.apply();
                finish();


                Toast.makeText(Second.this,userphone+"\n"+password, Toast.LENGTH_LONG).show();




                Intent vc=new Intent(Second.this,MainActivity.class);
                startActivity(vc);
            }
        });



    }
}
