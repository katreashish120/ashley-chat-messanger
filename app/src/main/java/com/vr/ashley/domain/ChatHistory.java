package com.vr.ashley.domain;

/**
 * Domain class of contact
 * Created by Ashish katre on 3/19/2017.
 */
public class ChatHistory {

    // Variables
    private int id;
    private int patientId;
    private int doctorId;
    private String dateOfLogin;
    private int who;
    private String data;

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

    public int getWho() {

        return who;
    }

    public void setWho(int who) {

        this.who = who;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {

        this.data = data;
    }


    /**
     * Default Constructor
     */
    public ChatHistory() {

    }

    /**
     * Constructor
     */
    public ChatHistory(String dateOfLogin, int patientId, int doctorId, int who, String data) {

        this.dateOfLogin = dateOfLogin;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.who = who;
        this.data = data;
    }
}
