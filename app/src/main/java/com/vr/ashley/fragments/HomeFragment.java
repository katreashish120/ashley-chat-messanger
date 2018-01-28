package com.vr.ashley.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vr.ashley.Managers.LastLoginManager;
import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.utils.Utils;
import com.vr.ashley.domain.LastLogin;

/**
 * Class for About the application
 *
 * @author Ashish Katre
 */
public class HomeFragment extends Fragment {

    private Context context;
    private PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = this.getActivity().getApplicationContext();

        prefManager = new PrefManager(context);

        LastLogin lastLogin = new LastLogin(prefManager.getPatientId(), prefManager.getDoctorId(), Utils.getCurrentDate());

        LastLoginManager lastLoginManager = new LastLoginManager(lastLogin);

        lastLoginManager.insert(context);

        getActivity().setTitle("Hi " + prefManager.getPatientName() + " ...");

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
