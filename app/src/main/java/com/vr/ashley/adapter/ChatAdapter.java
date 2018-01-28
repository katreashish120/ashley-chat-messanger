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
import com.vr.ashley.domain.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Ashish on 4/4/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<ChatHistory> chatHistoryList;

    public ChatAdapter(Activity activity, List<ChatHistory> chatHistoryList) {

        this.chatHistoryList = chatHistoryList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return chatHistoryList.size();
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

        ChatHistory chatHistory = (ChatHistory) chatHistoryList.get(position);

        View view = convertView;

        if (convertView == null) {

            view = inflater.inflate(R.layout.chatbubble, null);
        }

        TextView textView_Message = (TextView) view.findViewById(R.id.message_text);
        ImageView imageViewDoctor = (ImageView) view.findViewById(R.id.imageView_doctor);
        ImageView imageViewUser = (ImageView) view.findViewById(R.id.imageView_user);



        LinearLayout bubbleLayout = (LinearLayout) view.findViewById(R.id.bubble_layout);

        LinearLayout bubbleLayout_parent = (LinearLayout) view.findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (chatHistory.getWho() == 2) {
            imageViewUser.setVisibility(View.GONE);
            bubbleLayout.setBackgroundResource(R.drawable.ic_bubble_rect_4);
            bubbleLayout_parent.setGravity(Gravity.RIGHT);
            imageViewDoctor.setVisibility(View.VISIBLE);
            textView_Message.setText("  " + chatHistory.getData() + "          ");

        } else {
            // If not mine then align to left
            imageViewDoctor.setVisibility(View.GONE);
            bubbleLayout.setBackgroundResource(R.drawable.ic_bubble_rect_3);
            bubbleLayout_parent.setGravity(Gravity.LEFT);
            imageViewUser.setVisibility(View.VISIBLE);
            textView_Message.setText("          " + chatHistory.getData() + "  ");
        }

        textView_Message.setTextColor(Color.BLACK);

        return view;
    }

    public void add(ChatHistory chatHistory) {

        chatHistoryList.add(chatHistory);
    }
}
