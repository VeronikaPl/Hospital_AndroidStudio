package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Prescription_DB extends AppCompatActivity {
    private ListView prescriptionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_db);
        initWidgets();
        loadFromDBToMemory();
        setOnClickListener();
    }

    private void initWidgets() {
        prescriptionListView = findViewById(R.id.prescriptionListView);
    }

    private void loadFromDBToMemory() {
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        dataBaseConnector.getAllPrescriptions();
    }


    public void newPrescription(View view) {
        Intent newPrescriptionIntent = new Intent(this, Prescription_DB_Save.class);
        startActivity(newPrescriptionIntent);
    }

    private void setOnClickListener() {
        prescriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Prescription selectedNote = (Prescription) prescriptionListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), Prescription_DB_Save.class);
                editNoteIntent.putExtra(Prescription.PRESCRIPTION_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}