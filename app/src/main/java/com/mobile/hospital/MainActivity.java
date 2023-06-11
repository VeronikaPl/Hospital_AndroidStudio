package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDataBase(View view) {
        Intent goToDBIntent = new Intent(this, HospitalDataBase.class);
        startActivity(goToDBIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}