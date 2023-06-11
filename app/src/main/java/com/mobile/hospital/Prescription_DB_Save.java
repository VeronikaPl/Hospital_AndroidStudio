package com.mobile.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class Prescription_DB_Save extends AppCompatActivity {
    private EditText nameMedEditText, amountEditText, doctorEditText;
    private Button deleteButton;
    private Prescription selectedPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_db_save);
        initWidgets();
    }

    private void initWidgets() {
        nameMedEditText = findViewById(R.id.nameMedEditText);
        amountEditText = findViewById(R.id.amountEditText);
        doctorEditText = findViewById(R.id.doctorEditText);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void checkForEditPrescription() {
        Intent previousIntent = getIntent();

        int passedDPrescriptionID = previousIntent.getIntExtra(Prescription.PRESCRIPTION_EDIT_EXTRA, -1);
        selectedPrescription = Prescription.getPrescriptionByID(passedDPrescriptionID);

        if (selectedPrescription != null) {
            nameMedEditText.setText(selectedPrescription.getName());
            amountEditText.setText(selectedPrescription.getAmount());
            doctorEditText.setText(selectedPrescription.getDoctor_id().getId());
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteRow(View view) {
        selectedPrescription.setDeleted(new Date());
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        finish();
    }

    public void save(View view) {
        DataBaseConnector dataBaseConnector = new DataBaseConnector(this);
        String nameMed = String.valueOf(nameMedEditText.getText());
        int amount = Integer.parseInt(String.valueOf(amountEditText.getText()));
        Doctor doctor_id = Doctor.getDoctorByID(Integer.parseInt(String.valueOf(doctorEditText.getText())));

        if (selectedPrescription == null) {
            int id = Prescription.prescriptionArrayList.size();
            Prescription newPrescription = new Prescription(id, nameMed, amount, doctor_id);
            Prescription.prescriptionArrayList.add(newPrescription);
            dataBaseConnector.insertRowPrescription(newPrescription);
        } else {
            selectedPrescription.setName(nameMed);
            selectedPrescription.setAmount(amount);
            selectedPrescription.setDoctor_id(doctor_id);
        }

        finish();
    }
}