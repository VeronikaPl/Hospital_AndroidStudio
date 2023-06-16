package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class Doctor_DB_Save extends AppCompatActivity {
    private EditText nameEditText, surnameEditText, treatmentEditText, numberOfPatientsEditText;
    private Button deleteButton;
    private Doctor selectedDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_db_save);
        initWidgets();
    }

    private void initWidgets() {
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        treatmentEditText = findViewById(R.id.treatmentEditText);
        numberOfPatientsEditText = findViewById(R.id.numberOfPatientsEditText);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void checkForEditDoctor() {
        Intent previousIntent = getIntent();

        int passedDoctorID = previousIntent.getIntExtra(Doctor.DOCTOR_EDIT_EXTRA, -1);
        selectedDoctor = Doctor.getDoctorByID(passedDoctorID);

        if (selectedDoctor != null) {
            nameEditText.setText(selectedDoctor.getName());
            surnameEditText.setText(selectedDoctor.getSurname());
            treatmentEditText.setText(selectedDoctor.getTreatment());
            numberOfPatientsEditText.setText(selectedDoctor.getNumberOfPatients());
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void save(View view) {
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        String name = String.valueOf(nameEditText.getText());
        String surname = String.valueOf(surnameEditText.getText());
        String treatment = String.valueOf(treatmentEditText.getText());
        int numOfPatients = Integer.parseInt(String.valueOf(numberOfPatientsEditText.getText()));

        if (selectedDoctor == null) {
            int id = Doctor.doctorArrayList.size();
            Doctor newDoctor = new Doctor(id, name, surname, treatment, numOfPatients);
            Doctor.doctorArrayList.add(newDoctor);
            dataBaseConnector.insertRowDoctor(newDoctor);
        } else {
            selectedDoctor.setName(name);
            selectedDoctor.setSurname(surname);
            selectedDoctor.setTreatment(treatment);
            selectedDoctor.setNumberOfPatients(numOfPatients);
        }

        finish();
    }

    public void deleteRow(View view) {
        selectedDoctor.setDeleted(new Date());
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        finish();
    }
}