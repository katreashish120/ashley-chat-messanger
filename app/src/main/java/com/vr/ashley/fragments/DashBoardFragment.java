package com.vr.ashley.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vr.ashley.Managers.LastLoginManager;
import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.adapter.DashBoardAdapter;

/**
 * Class for About the application
 *
 * @author Ashish Katre
 */
public class DashBoardFragment extends Fragment {

    private Context context;
    private PrefManager prefManager;
    private ListView listView_dashboard;
    private DashBoardAdapter dashBoardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = this.getActivity().getApplicationContext();

        prefManager = new PrefManager(context);

        listView_dashboard = (ListView) view.findViewById(R.id.listView_dashboard);

        listView_dashboard.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        LastLoginManager lastLoginManager = new LastLoginManager();

        dashBoardAdapter = new DashBoardAdapter(getActivity(), lastLoginManager.findAll(context));

        listView_dashboard.setAdapter(dashBoardAdapter);

        getActivity().setTitle("Dashboard "+prefManager.getPhobia());

        return view;
    }
}
