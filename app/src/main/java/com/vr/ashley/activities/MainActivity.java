package com.vr.ashley.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.fragments.AboutFragment;
import com.vr.ashley.fragments.DashBoardFragment;
import com.vr.ashley.fragments.SpeechFragment;
import com.vr.ashley.fragments.ContactFragment;
import com.vr.ashley.fragments.HelpFragment;
import com.vr.ashley.fragments.HomeFragment;
import com.vr.ashley.fragments.TextFragment;
import com.vr.ashley.utils.Constants;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    public static final String MAIN_TITLE_WARNING = "Warning";
    public static final String MAIN_BUTTON_YES = "YES";
    public static final String MAIN_BUTTON_NO = "NO";

    public static final int FIRST_RUN_REQUEST = 0;

    public static String currentFragment;

    private PrefManager prefManager;
    private Context context;
    private String patientName;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            boolean flag = false;

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    setFragment(new HomeFragment());
                    flag = true;
                    break;

                case R.id.navigation_dashboard:
                    setFragment(new DashBoardFragment());
                    flag = true;
                    break;

                case R.id.navigation_text:
                    setFragment(new TextFragment());
                    flag = true;
                    break;

                case R.id.navigation_speech:
                    setFragment(new SpeechFragment());
                    flag = true;
                    break;
            }
            return flag;
        }

    };

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        // Checking for first time app launch
        prefManager = new PrefManager(context);

        if (prefManager.isFirstTimeLaunch()) {

            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            Bundle b = new Bundle();
            b.putString(Constants.ACTION, Constants.NEW);
            intent.putExtras(b);
            startActivityForResult(intent, FIRST_RUN_REQUEST);
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (findViewById(R.id.fragment) != null) {
            if (currentFragment == null) {
                setFragment(new HomeFragment());
            } else {
                updateFragment(currentFragment);
            }
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FIRST_RUN_REQUEST && resultCode == RESULT_OK) {

            prefManager.setFirstTimeLaunch(false);
            prefManager.setPatientName(data.getStringExtra(Constants.PATIENT_NAME));
            prefManager.setPatientId(data.getIntExtra(Constants.PATIENT_ID, 0));
            prefManager.setDoctorId(data.getIntExtra(Constants.DOCTOR_ID, 0));

            setFragment(new HomeFragment());

        } else {

            finish();
        }
    }

    /**
     * onCreateOptionsMenu
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    /**
     * update fragment in main activity
     *
     * @param fragmentType
     */
    public void updateFragment(String fragmentType) {

        Class<?> classType = null;

        try {

            classType = Class.forName(fragmentType);
            setFragment((Fragment) classType.newInstance());

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (InstantiationException e) {

            e.printStackTrace();

        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }
    }

    /**
     * set fragment in main activity
     *
     * @param fragment
     */
    public void setFragment(Fragment fragment) {

        Bundle args = new Bundle();
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        currentFragment = fragment.getClass().getName();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    /**
     * Log Out functionality
     */
    private void performLogout() {

        AlertDialog.Builder warningDialog = new AlertDialog.Builder(this, R.style.AppTheme_Dialog);
        warningDialog.setTitle(MAIN_TITLE_WARNING);
        warningDialog.setMessage(R.string.logout_dialog);
        warningDialog.setPositiveButton(MAIN_BUTTON_YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alert, int which) {

                //logout
                prefManager.setFirstTimeLaunch(true);
                prefManager.setPatientName(PrefManager.PREF_EMPTY_STRING);
                prefManager.setPatientId(PrefManager.PREF_ZERO);
                prefManager.setDoctorId(PrefManager.PREF_ZERO);

                alert.dismiss();
                navigateToMainActivity(context);
            }
        });
        warningDialog.setNegativeButton(MAIN_BUTTON_NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alert, int which) {

                alert.dismiss();
                setFragment(new HomeFragment());
            }
        });
        warningDialog.show();

    }

    /**
     * Navigation to main activity
     *
     * @param context
     */
    private void navigateToMainActivity(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * onOptionsItemSelected
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        boolean flag = false;

        if (id == R.id.navigation_logout) {

            performLogout();
            flag = true;

        } else if (id == R.id.navigation_contact) {

            setFragment(new ContactFragment());
            flag = true;

        } else if (id == R.id.navigation_help) {

            setFragment(new HelpFragment());
            flag = true;

        } else if (id == R.id.navigation_about) {

            setFragment(new AboutFragment());
            flag = true;
        }

        flag = super.onOptionsItemSelected(item);

        return flag;
    }
}
