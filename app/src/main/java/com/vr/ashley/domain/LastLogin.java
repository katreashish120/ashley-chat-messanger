package com.vr.ashley.domain;

/**
 * Domain class of contact
 * Created by Ashish katre on 3/19/2017.
 */
public class LastLogin {

    // Variables
    private int id;
    private int patientId;
    private int doctorId;
    private String dateOfLogin;

    // Getters and Setters
    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getPatientId() {

        return patientId;
    }

    public void setPatientId(int patientId) {

        this.patientId = patientId;
    }

    public int getDoctorId() {

        return doctorId;
    }

    public void setDoctorId(int doctorId) {

        this.doctorId = doctorId;
    }

    public String getDateOfLogin() {

        return dateOfLogin;
    }

    public void setDateOfLogin(String dateOfLogin) {

        this.dateOfLogin = dateOfLogin;
    }


    /**
     * Default Constructor
     */
    public LastLogin() {

    }

    /**
     * Constructor
     */
    public LastLogin(int patientId, int doctorId, String dateOfLogin) {

        this.dateOfLogin = dateOfLogin;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}
