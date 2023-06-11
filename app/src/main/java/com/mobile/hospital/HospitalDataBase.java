package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HospitalDataBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_data_base);
    }

    public void openMainActivity(View view) {
        Intent openMainActivity = new Intent(this, MainActivity.class);
        startActivity(openMainActivity);
    }

    public void openPrescriptions(View view) {
        Intent openPrescription = new Intent(this, Prescription_DB.class);
        startActivity(openPrescription);
    }

    public void openDoctors(View view) {
        Intent openDoctor = new Intent(this, Doctor_DB.class);
        startActivity(openDoctor);
    }
}