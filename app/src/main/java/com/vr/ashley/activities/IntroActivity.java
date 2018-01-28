package com.vr.ashley.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for personal bio activity
 *
 * @author Ashish Katre
 */
public class IntroActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText patientName;
    private EditText patientId;
    private EditText doctorId;
    private Button saveButton;
    private Spinner spinner;
    public static final String PHOBIA_SPIDER = "SPIDER";
    public static final String PHOBIA_SOCIAL = "SOCIAL";

    private Context context;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        setTitle(R.string.title_intro);

        context = getApplicationContext();

        patientName = (EditText) findViewById(R.id.editText_patientName_intro);
        patientId = (EditText) findViewById(R.id.editText_patientId_intro);
        doctorId = (EditText) findViewById(R.id.editText_DoctorId_intro);
        spinner = (Spinner) findViewById(R.id.spinner_Phobia_intro);
        saveButton = (Button) findViewById(R.id.saveIntro);

        List<String> types = new ArrayList<>();
        types.add(PHOBIA_SPIDER);
        types.add(PHOBIA_SOCIAL);

        spinner.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types));

        saveButton.setOnClickListener(this);
    }

    /**
     * view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.saveIntro:

                saveIntro();
                break;
        }
    }

    /**
     * Save Patient details
     */
    public void saveIntro() {

        if (isValid()) {

            String name = patientName.getText().toString();
            String patientIdNumber = patientId.getText().toString();
            String doctorIdNumber = doctorId.getText().toString();
            String phobia = spinner.getSelectedItem().toString();

            PrefManager prefManager = new PrefManager(context);

            prefManager.setFirstTimeLaunch(false);
            prefManager.setPatientName(name);
            prefManager.setPatientId(Integer.parseInt(patientIdNumber));
            prefManager.setDoctorId(Integer.parseInt(doctorIdNumber));
            prefManager.setPhobia(phobia);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.PATIENT_NAME, name);
            resultIntent.putExtra(Constants.PATIENT_ID, Integer.parseInt(patientIdNumber));
            resultIntent.putExtra(Constants.DOCTOR_ID, Integer.parseInt(doctorIdNumber));
            setResult(RESULT_OK, resultIntent);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Validating fields
     */
    private boolean isValid() {

        boolean isValid = true;

        if (TextUtils.isEmpty(patientName.getText().toString().trim())
                || !patientName.getText().toString().matches(Constants.PATTERN_LETTERS_ONLY)) {

            patientName.setError(Constants.INVALID_NAME);
            patientName.requestFocus();
            isValid = false;
        }
        if (TextUtils.isEmpty(patientId.getText().toString().trim())
                || patientId.getText().toString().matches(Constants.PATTERN_ZERO)) {

            patientId.setError(Constants.INVALID_PATIENT_ID);
            patientId.requestFocus();
            isValid = false;
        }
        if (TextUtils.isEmpty(doctorId.getText().toString().trim())
                || doctorId.getText().toString().matches(Constants.PATTERN_ZERO)) {

            doctorId.setError(Constants.INVALID_DOCTOR_ID);
            doctorId.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /**
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


}
