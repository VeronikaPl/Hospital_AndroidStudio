package com.mobile.hospital;

import android.content.ContentValues;
import android.content.Context;

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
    @Test public void testAPreConditions() {assertNotNull(connector);}

    @Test
    public void tableDoctorRowAddition_isCorrect() {
        Doctor doctor = new Doctor("name", "surname", "treatment", 12);
        int rowCount = connector.getRowCount("Doctor");
        connector.insertRowDoctor(doctor);
        int newRowCount=connector.getRowCount("Doctor");;
        assertEquals(newRowCount, rowCount+1);
    }

    @Test
    public void tablePrescriptionRowAddition_isCorrect() {
        Prescription prescription = new Prescription("name", 3, Doctor.getDoctorByID(1));
        int rowCount = connector.getRowCount("Prescription");
        connector.insertRowPrescription(prescription);
        int newRowCount=connector.getRowCount("Prescription");
        assertEquals(newRowCount, rowCount+1);
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