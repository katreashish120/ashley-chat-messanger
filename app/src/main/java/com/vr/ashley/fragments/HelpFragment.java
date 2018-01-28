package com.vr.ashley.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vr.ashley.R;

/**
 * Class for Help Fragment operations
 *
 * @author Ashish Katre
 */
public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.title_help);

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}
