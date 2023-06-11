package com.mobile.hospital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
