package com.vr.ashley.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vr.ashley.R;
import com.vr.ashley.domain.ChatHistory;
import com.vr.ashley.domain.LastLogin;

import java.util.List;

/**
 * Created by Ashish on 4/4/2017.
 */

public class DashBoardAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<LastLogin> lastLoginList;

    public DashBoardAdapter(Activity activity, List<LastLogin> lastLoginList) {

        this.lastLoginList = lastLoginList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return lastLoginList.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LastLogin lastLogin = (LastLogin) lastLoginList.get(position);

        View view = convertView;

        if (convertView == null) {

            view = inflater.inflate(R.layout.dashboard, null);
        }

        TextView textView_id = (TextView) view.findViewById(R.id.textView_dashboard_id);

        TextView textView_date = (TextView) view.findViewById(R.id.textView_dashboard_date);

        textView_id.setText("ID: " + lastLogin.getId());
        textView_id.setTextColor(Color.BLACK);

        textView_date.setText("          Date: "+lastLogin.getDateOfLogin());
        textView_date.setTextColor(Color.BLACK);

        return view;
    }

    public void add(LastLogin lastLogin) {

        lastLoginList.add(lastLogin);
    }
}
