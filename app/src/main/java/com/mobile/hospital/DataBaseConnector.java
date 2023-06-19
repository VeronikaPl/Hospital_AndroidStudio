package com.mobile.hospital;

import static com.mobile.hospital.DataBaseConnector.DBHelper.TABLE_1;
import static com.mobile.hospital.DataBaseConnector.DBHelper.TABLE_2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.constraintlayout.helper.widget.MotionEffect;

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

    public void deleteDoctor(long id) {
        open();
        database.delete(TABLE_1, "doctor_id=" + id, null);
        close();
    }

    public void deletePrescription(long id) {
        open();
        database.delete(TABLE_2, "prescription_id=" + id, null);
        close();
    }

    public Cursor getAllDoctors() {
        return database.query(TABLE_1, new String[]{"doctor_id", DBHelper.TABLE_COLUMN_name,
                        DBHelper.TABLE_COLUMN_surname, DBHelper.TABLE_COLUMN_treatment, DBHelper.TABLE_COLUMN_number_of_patients},
                null, null, null, null, null);
    }

    public Cursor getAllPrescriptions() {
        return database.query(TABLE_2, new String[]{"prescription_id", DBHelper.TABLE_COLUMN_name_medicine, DBHelper.TABLE_COLUMN_amount, DBHelper.TABLE_COLUMN_Prescription_Doctor_ID},
                null, null, null, null, null);
    }

    public class DBHelper extends SQLiteOpenHelper {
        public static final String TABLE_1 = "Doctor";
        public static final String TABLE_COLUMN_Doctor_ID = "doctor_id";
        public static final String TABLE_COLUMN_name = "name";
        public static final String TABLE_COLUMN_surname = "surname";
        public static final String TABLE_COLUMN_treatment = "treatment";
        public static final String TABLE_COLUMN_number_of_patients = "number of patients";

        public static final String TABLE_2 = "Prescription";
        public static final String TABLE_COLUMN_Prescription_ID = "prescription_id";
        public static final String TABLE_COLUMN_name_medicine = "name";
        public static final String TABLE_COLUMN_amount = "amount";
        public static final String TABLE_COLUMN_Prescription_Doctor_ID = "doctor_id";

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_1 + " ( "
                    + TABLE_COLUMN_Doctor_ID + " integer primary key autoincrement, "
                    + TABLE_COLUMN_name + " TEXT, "
                    + TABLE_COLUMN_surname + " TEXT, "
                    + TABLE_COLUMN_treatment + " TEXT, "
                    + TABLE_COLUMN_number_of_patients + " INTEGER " + " );");

            db.execSQL("CREATE TABLE " + TABLE_2 + " ( "
                    + TABLE_COLUMN_Prescription_ID + " integer primary key autoincrement, "
                    + TABLE_COLUMN_name_medicine + " TEXT, "
                    + TABLE_COLUMN_amount + " INEGER, "
                    + TABLE_COLUMN_Prescription_Doctor_ID + " INTEGER, "
                    + " FOREIGN KEY(" + TABLE_COLUMN_Prescription_Doctor_ID + ") REFERENCES " + TABLE_1 + "(" + TABLE_COLUMN_Doctor_ID + ") "
                    + " );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

    public void updateDoctor(long id, String name, String surname, String treatment, int numberOfPatients) {
        open();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("treatment", treatment);
        contentValues.put("number of patients", numberOfPatients);

        String whereClause = "doctor_id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("Doctor", contentValues, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Log.d(MotionEffect.TAG, "Doctor updated successfully");
        } else {
            Log.d(MotionEffect.TAG, "Failed to update doctor");
        }

        close();
    }

    public void updatePrescription(long id, String name, int amount, Doctor doctor_id) {
        open();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("amount", amount);
        contentValues.put("doctor_id", doctor_id.getId());

        String whereClause = "prescription_id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("Prescription", contentValues, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Log.d(MotionEffect.TAG, "Prescription updated successfully");
        } else {
            Log.d(MotionEffect.TAG, "Failed to update prescription");
        }

        close();
    }

    public int getRowCount(String table) {
        open();
        int rowCount = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + table, null);
        if (cursor.moveToFirst()) {
            rowCount = cursor.getInt(0);
        }
        cursor.close();
        close();
        return rowCount;
    }
}