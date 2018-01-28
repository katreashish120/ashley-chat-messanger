package com.vr.ashley.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.vr.ashley.utils.Constants;

/**
 * Prefrence Manager
 *
 * @author Ashish Katre
 */
public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context currentContext;

    // shared pref mode
    int PREF_PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "activity_executed";

    public static final String PREF_IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final int PREF_ZERO = 0;
    public static final String PREF_EMPTY_STRING = "";


    public PrefManager(Context context) {

        this.currentContext = context;
        pref = currentContext.getSharedPreferences(PREF_NAME, PREF_PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {

        editor.putBoolean(PREF_IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {

        return pref.getBoolean(PREF_IS_FIRST_TIME_LAUNCH, true);
    }

    public void setPatientName(String userName) {

        editor.putString(Constants.PATIENT_NAME, userName);
        editor.commit();
    }

    public String getPatientName() {

        return pref.getString(Constants.PATIENT_NAME, PREF_EMPTY_STRING);
    }

    public void setDoctorId(int userId) {

        editor.putInt(Constants.DOCTOR_ID, userId);
        editor.commit();
    }

    public int getDoctorId() {

        return pref.getInt(Constants.DOCTOR_ID, PREF_ZERO);
    }

    public void setPhobia(String phobia) {

        editor.putString(Constants.PHOBIA, phobia);
        editor.commit();
    }

    public String getPhobia() {

        return pref.getString(Constants.PHOBIA, PREF_EMPTY_STRING);
    }

    public void setPatientId(int userId) {

        editor.putInt(Constants.PATIENT_ID, userId);
        editor.commit();
    }

    public int getPatientId() {

        return pref.getInt(Constants.PATIENT_ID, PREF_ZERO);
    }
}
