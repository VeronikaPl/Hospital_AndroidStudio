package com.mobile.hospital;

import static com.mobile.hospital.DBHelper.TABLE_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private DataBaseConnector connector;

    @Before
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.mobile.hospital", appContext.getPackageName());
        connector = new DataBaseConnector(appContext);
    }

    @After
    public void tearDown() {
        connector.close();
    }

    @Test
    public void tableDoctorRowAddition_isCorrect() {
        int id = 0;
        String name = "name";
        String surname = "surname";
        String treatment = "treatment";
        Doctor doctor = new Doctor(id++, name, surname, treatment, 1);
        connector.insertRowDoctor(doctor);
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_name, doctor.getName());
        row.put(DBHelper.TABLE_COLUMN_surname, doctor.getSurname());
        row.put(DBHelper.TABLE_COLUMN_treatment, doctor.getTreatment());
        row.put(DBHelper.TABLE_COLUMN_number_of_patients, doctor.getNumberOfPatients());
        long rowId = doctor.getId();
        assertEquals(0, rowId);
    }

    @Test
    public void tablePrescriptionRowAddition_isCorrect() {
        int id = 0;
        String name = "name";
        Prescription prescription = new Prescription(id++, name, 1, Doctor.getDoctorByID(1));
        connector.insertRowPrescription(prescription);
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_name_medicine, prescription.getName());
        row.put(DBHelper.TABLE_COLUMN_amount, prescription.getAmount());
        row.put(DBHelper.TABLE_COLUMN_Prescription_Doctor_ID, prescription.getDoctor_id().getId());
        long rowId = prescription.getId();
        assertEquals(0, rowId);
    }

    @Test
    public void testUpdateDoctor() {
        connector.updateDoctor(2, "Ivan", "Ivanov", "take medicines", 15);
    }

    @Test
    public void testUpdatePrescription() {
        connector.updatePrescription(2, "Ivan", 3, Doctor.getDoctorByID(2));
    }

    @Test
    public void testDeleteRowDoctor() {
        Connection connection = (Connection) connector;
        String deleteQuery = "DELETE FROM Doctor WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, 2);
            int affectedRows = preparedStatement.executeUpdate();

            assertEquals(1, affectedRows);
        } catch (java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testDeleteRowPrescription() {
        Connection connection = (Connection) connector;
        String deleteQuery = "DELETE FROM Prescription WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, 2);
            int affectedRows = preparedStatement.executeUpdate();

            assertEquals(1, affectedRows);
        } catch (java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}