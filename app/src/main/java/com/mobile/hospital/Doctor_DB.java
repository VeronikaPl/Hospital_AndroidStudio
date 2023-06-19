package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Doctor_DB extends AppCompatActivity {
    private ListView doctorListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_db);
        initWidgets();
        loadFromDBToMemory();
        setOnClickListener();
    }

    private void initWidgets() {
        doctorListView = findViewById(R.id.doctorListView);
    }

    private void loadFromDBToMemory() {
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        dataBaseConnector.getAllDoctors();
    }

    public void newDoctor(View view) {
        Intent newDoctorIntent = new Intent(this, Doctor_DB_Save.class);
        startActivity(newDoctorIntent);
    }

    private void setOnClickListener() {
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Doctor selectedNote = (Doctor) doctorListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), Doctor_DB_Save.class);
                editNoteIntent.putExtra(Doctor.DOCTOR_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}