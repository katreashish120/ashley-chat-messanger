package com.vr.ashley.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for creating tables
 * Ashish Katre
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "therabot";

    private static final String DATABASE_COMMAND_DROP = "DROP TABLE IF EXISTS ";
    private static final String DATABASE_COMMAND_CREATE = "CREATE TABLE ";
    private static final String DATABASE_COMMAND_LEFT_BRACKET = "(";
    private static final String DATABASE_COMMAND_INTEGER = " INTEGER";
    private static final String DATABASE_COMMAND_TEXT = " TEXT";
    private static final String DATABASE_COMMAND_INTEGER_COMMA = " INTEGER,";
    private static final String DATABASE_COMMAND_TEXT_COMMA = " TEXT,";
    private static final String DATABASE_COMMAND_PRIMARY_KEY = " PRIMARY KEY,";
    private static final String DATABASE_COMMAND_RIGHT_BRACKET = ")";

    // login info
    public static final String TABLE_LAST_LOGIN_INFO = "logininfo";
    public static final String LAST_LOGIN_INFO_KEY_ID = "id";
    public static final String LAST_LOGIN_INFO_KEY_PATIENT_ID = "patient";
    public static final String LAST_LOGIN_INFO_KEY_DOCTOR_ID = "doctor";
    public static final String LAST_LOGIN_INFO_KEY_DATE = "date";

    // login info
    public static final String TABLE_CHAT_HISTORY = "chathistory";
    public static final String CHAT_HISTORY_KEY_ID = "id";
    public static final String CHAT_HISTORY_KEY_PATIENT_ID = "patient";
    public static final String CHAT_HISTORY_KEY_DOCTOR_ID = "doctor";
    public static final String CHAT_HISTORY_KEY_DATE = "date";
    public static final String CHAT_HISTORY_KEY_WHO = "who";
    public static final String CHAT_HISTORY_KEY_DATA = "data";


    private static final int DATABASE_VERSION = 1;


    // Create table login info
    private static final String CREATE_TABLE_LAST_LOGIN_INFO = DATABASE_COMMAND_CREATE
            + TABLE_LAST_LOGIN_INFO + DATABASE_COMMAND_LEFT_BRACKET + LAST_LOGIN_INFO_KEY_ID + DATABASE_COMMAND_INTEGER + DATABASE_COMMAND_PRIMARY_KEY
            + LAST_LOGIN_INFO_KEY_PATIENT_ID + DATABASE_COMMAND_INTEGER_COMMA + LAST_LOGIN_INFO_KEY_DOCTOR_ID + DATABASE_COMMAND_INTEGER_COMMA
            + LAST_LOGIN_INFO_KEY_DATE + DATABASE_COMMAND_TEXT + DATABASE_COMMAND_RIGHT_BRACKET;

    // Create table login info
    private static final String CREATE_TABLE_CHAT_HISTORY = DATABASE_COMMAND_CREATE
            + TABLE_CHAT_HISTORY + DATABASE_COMMAND_LEFT_BRACKET + CHAT_HISTORY_KEY_ID + DATABASE_COMMAND_INTEGER + DATABASE_COMMAND_PRIMARY_KEY
            + CHAT_HISTORY_KEY_PATIENT_ID + DATABASE_COMMAND_INTEGER_COMMA + CHAT_HISTORY_KEY_DOCTOR_ID + DATABASE_COMMAND_INTEGER_COMMA
            + CHAT_HISTORY_KEY_DATE + DATABASE_COMMAND_TEXT_COMMA + CHAT_HISTORY_KEY_WHO + DATABASE_COMMAND_INTEGER_COMMA
            + CHAT_HISTORY_KEY_DATA + DATABASE_COMMAND_TEXT + DATABASE_COMMAND_RIGHT_BRACKET;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_LAST_LOGIN_INFO);
        db.execSQL(CREATE_TABLE_CHAT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DATABASE_COMMAND_DROP + TABLE_LAST_LOGIN_INFO);
        db.execSQL(DATABASE_COMMAND_DROP + TABLE_CHAT_HISTORY);
        onCreate(db);
    }
}
