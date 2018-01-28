package com.vr.ashley.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vr.ashley.domain.ChatHistory;
import com.vr.ashley.domain.LastLogin;

import java.util.ArrayList;
import java.util.List;

import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_AND;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_ASC;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_COMMA;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_ORDER_BY;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SELECT_ALL;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SELECT_WHERE;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SYMBOL;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SYMBOL_EQUAL;

/**
 * Implementation class for emergency contacts database operations
 * Created by Ashish Katre
 */
public class ChatHistoryDAOImpl extends DBHelper implements ChatHistoryDAO {

    // Logger name
    private static final String LOG = "ChatHistoryDAOImpl";

    /**
     * Constructor for LastLoginInfoDAOImpl
     *
     * @param context
     */
    public ChatHistoryDAOImpl(Context context) {

        super(context);
    }

    /**
     * delete operation
     *
     * @param id int
     * @return int
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int result = sqLiteDatabase.delete(TABLE_CHAT_HISTORY, CHAT_HISTORY_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        Log.d(LOG, "Delete: " + result);

        sqLiteDatabase.close();

        return result;
    }

    /**
     * select all operation
     *
     * @return List<ChatHistory>
     */
    @Override
    public List<ChatHistory> findAll(int patientId, int doctorId) {

        List<ChatHistory> chatHistoryList = null;


        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CHAT_HISTORY
                + DATABASE_COMMAND_SELECT_WHERE + CHAT_HISTORY_KEY_PATIENT_ID + DATABASE_COMMAND_SYMBOL_EQUAL + patientId
                + DATABASE_COMMAND_AND + CHAT_HISTORY_KEY_DOCTOR_ID + DATABASE_COMMAND_SYMBOL_EQUAL + doctorId
                + DATABASE_COMMAND_ORDER_BY + CHAT_HISTORY_KEY_DATE + DATABASE_COMMAND_ASC
                + DATABASE_COMMAND_COMMA + CHAT_HISTORY_KEY_ID + DATABASE_COMMAND_ASC;

        // String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CHAT_HISTORY;

        Log.d(LOG, "FindAll: " + selectQuery);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (null != cursor) {

            Log.d(LOG, "Number of records: " + cursor.getCount());

            cursor.moveToFirst();
            chatHistoryList = new ArrayList<>();

            while (!cursor.isAfterLast()) {

                ChatHistory chatHistory = new ChatHistory();

                chatHistory.setId(cursor.getInt(cursor.getColumnIndex(CHAT_HISTORY_KEY_ID)));
                chatHistory.setPatientId(cursor.getInt(cursor.getColumnIndex(CHAT_HISTORY_KEY_PATIENT_ID)));
                chatHistory.setDoctorId(cursor.getInt(cursor.getColumnIndex(CHAT_HISTORY_KEY_DOCTOR_ID)));
                chatHistory.setData(cursor.getString(cursor.getColumnIndex(CHAT_HISTORY_KEY_DATE)));
                chatHistory.setWho(cursor.getInt(cursor.getColumnIndex(CHAT_HISTORY_KEY_WHO)));
                chatHistory.setData(cursor.getString(cursor.getColumnIndex(CHAT_HISTORY_KEY_DATA)));

                chatHistoryList.add(chatHistory);

                cursor.moveToNext();
            }
            cursor.close();
        }

        sqLiteDatabase.close();

        return chatHistoryList;
    }

    /**
     * insert operation
     *
     * @param chatHistory ChatHistory
     * @return long
     */
    @Override
    public long insert(ChatHistory chatHistory) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHAT_HISTORY_KEY_PATIENT_ID, chatHistory.getPatientId());
        values.put(CHAT_HISTORY_KEY_DOCTOR_ID, chatHistory.getDoctorId());
        values.put(CHAT_HISTORY_KEY_DATE, chatHistory.getDateOfLogin());
        values.put(CHAT_HISTORY_KEY_WHO, chatHistory.getWho());
        values.put(CHAT_HISTORY_KEY_DATA, chatHistory.getData());

        // insert row
        long result = sqLiteDatabase.insert(TABLE_CHAT_HISTORY, null, values);

        Log.d(LOG, "Insert: " + result);

        sqLiteDatabase.close();

        return result;
    }
}
