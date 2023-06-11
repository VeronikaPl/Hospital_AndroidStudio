package com.mobile.hospital;

import java.util.ArrayList;
import java.util.Date;

public class Prescription {
    public static ArrayList<Prescription> prescriptionArrayList = new ArrayList<>();
    public static String PRESCRIPTION_EDIT_EXTRA = "prescriptionEdit";

    private int id;
    private String name;
    private int amount;
    private Doctor doctor_id;
    private Date deleted;

    public Prescription(int id, String name, int amount, Doctor doctor_id, Date deleted) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.doctor_id = doctor_id;
        this.deleted = deleted;
    }

    public Prescription(int id, String name, int amount, Doctor doctor_id) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.doctor_id = doctor_id;
        deleted = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Doctor getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Doctor doctor_id) {
        this.doctor_id = doctor_id;
    }

    public static Prescription getPrescriptionByID(int passedPrescriptionID) {
        for (Prescription prescription : prescriptionArrayList) {
            if (prescription.getId() == passedPrescriptionID)
                return prescription;
        }
        return null;
    }

    public static ArrayList<Prescription> nonDeletedPrescription() {
        ArrayList<Prescription> nonDeleted = new ArrayList<>();
        for (Prescription prescription : prescriptionArrayList) {
            if (prescription.getDeleted() == null)
                nonDeleted.add(prescription);
        }
        return nonDeleted;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
