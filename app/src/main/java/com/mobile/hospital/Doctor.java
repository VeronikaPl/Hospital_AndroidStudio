package com.mobile.hospital;

import java.util.ArrayList;
import java.util.Date;

public class Doctor {
    public static ArrayList<Doctor> doctorArrayList = new ArrayList<>();
    public static String DOCTOR_EDIT_EXTRA =  "doctorEdit";

    private int id;
    private String name;
    private String surname;
    private String treatment;
    private int numberOfPatients;
    private Date deleted;

    public Doctor(int id, String name, String surname, String treatment, int numberOfPatients, Date deleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.treatment = treatment;
        this.numberOfPatients = numberOfPatients;
        this.deleted = deleted;
    }

    public Doctor(int id, String name, String surname, String treatment, int numberOfPatients) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.treatment = treatment;
        this.numberOfPatients = numberOfPatients;
        deleted = null;
    }

    public Doctor(String name, String surname, String treatment, int numberOfPatients) {
        id++;
        this.name = name;
        this.surname = surname;
        this.treatment = treatment;
        this.numberOfPatients = numberOfPatients;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    public static Doctor getDoctorByID(int passedDoctorID) {
        for (Doctor doctor : doctorArrayList) {
            if (doctor.getId() == passedDoctorID)
                return doctor;
        }
        return null;
    }

    public static ArrayList<Doctor> nonDeletedDoctors() {
        ArrayList<Doctor> nonDeleted = new ArrayList<>();
        for (Doctor doctor : doctorArrayList) {
            if (doctor.getDeleted() == null)
                nonDeleted.add(doctor);
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
