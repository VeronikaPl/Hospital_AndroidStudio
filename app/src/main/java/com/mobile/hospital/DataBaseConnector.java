package com.mobile.hospital;

import static com.mobile.hospital.DBHelper.TABLE_1;
import static com.mobile.hospital.DBHelper.TABLE_2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBaseConnector {
    private static final String DATABASE_NAME = "Hospital";
    private SQLiteDatabase database;
    private DBHelper databaseOpenHelper;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public DataBaseConnector(Context context) {
        databaseOpenHelper = new DBHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    public void insertRowDoctor(Doctor doctor) {
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_name, doctor.getName());
        row.put(DBHelper.TABLE_COLUMN_surname, doctor.getSurname());
        row.put(DBHelper.TABLE_COLUMN_treatment, doctor.getTreatment());
        row.put(DBHelper.TABLE_COLUMN_number_of_patients, doctor.getNumberOfPatients());
        open();
        database.insert(TABLE_1, null, row);
        close();
    }

    public void insertRowPrescription(Prescription prescription) {
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_name_medicine, prescription.getName());
        row.put(DBHelper.TABLE_COLUMN_amount, prescription.getAmount());
        row.put(DBHelper.TABLE_COLUMN_Prescription_Doctor_ID, prescription.getDoctor_id().getId());
        open();
        database.insert(TABLE_2, null, row);
        close();
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

    public void getTableAllRowsDoctor() {
        SQLiteDatabase sqLiteDatabase = this.databaseOpenHelper.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_1, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String surname = result.getString(3);
                    String treatment = result.getString(4);
                    int numOfPatients = result.getInt(5);
                    String stringDeleted = result.getString(6);
                    Date deleted = getDateFromString(stringDeleted);
                    Doctor doctor = new Doctor(id, name, surname, treatment, numOfPatients, deleted);
                    Doctor.doctorArrayList.add(doctor);
                }
            }
        }
    }

    public void getTableAllRowsPrescription() {
        SQLiteDatabase sqLiteDatabase = this.databaseOpenHelper.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_2, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    int amount = result.getInt(3);
                    Doctor doctor_id = Doctor.getDoctorByID(result.getInt(4));
                    String stringDeleted = result.getString(6);
                    Date deleted = getDateFromString(stringDeleted);
                    Prescription prescription = new Prescription(id, name, amount, doctor_id, deleted);
                    Prescription.prescriptionArrayList.add(prescription);
                }
            }
        }
    }
}
