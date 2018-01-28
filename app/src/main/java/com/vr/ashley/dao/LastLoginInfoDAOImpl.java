package com.vr.ashley.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vr.ashley.domain.LastLogin;

import java.util.ArrayList;
import java.util.List;

import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_ASC;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_ORDER_BY;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SELECT_ALL;
import static com.vr.ashley.dao.DbConstants.DATABASE_COMMAND_SYMBOL;

/**
 * Implementation class for emergency contacts database operations
 * Created by Ashish Katre
 */
public class LastLoginInfoDAOImpl extends DBHelper implements LastLoginInfoDAO {

    // Logger name
    private static final String LOG = "LastLoginInfoDAOImpl";

    /**
     * Constructor for LastLoginInfoDAOImpl
     *
     * @param context
     */
    public LastLoginInfoDAOImpl(Context context) {

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

        int result = sqLiteDatabase.delete(TABLE_LAST_LOGIN_INFO, LAST_LOGIN_INFO_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        return result;
    }

    /**
     * select all operation
     *
     * @return Cursor
     */
    @Override
    public List<LastLogin> findAll() {

        List<LastLogin> lastLoginList = null;

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_LAST_LOGIN_INFO + DATABASE_COMMAND_ORDER_BY + LAST_LOGIN_INFO_KEY_DATE + DATABASE_COMMAND_ASC;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        Log.d(LOG, "FindAll: " + selectQuery);

        if (null != cursor) {

            cursor.moveToFirst();

            lastLoginList = new ArrayList<>();

            while (!cursor.isAfterLast()) {

                Log.d(LOG, "Number of records: " + cursor.getCount());

                LastLogin lastLogin = new LastLogin();

                lastLogin.setId(cursor.getInt(cursor.getColumnIndex(LAST_LOGIN_INFO_KEY_ID)));
                lastLogin.setPatientId(cursor.getInt(cursor.getColumnIndex(LAST_LOGIN_INFO_KEY_PATIENT_ID)));
                lastLogin.setDoctorId(cursor.getInt(cursor.getColumnIndex(LAST_LOGIN_INFO_KEY_DOCTOR_ID)));
                lastLogin.setDateOfLogin(cursor.getString(cursor.getColumnIndex(LAST_LOGIN_INFO_KEY_DATE)));

                lastLoginList.add(lastLogin);

                cursor.moveToNext();
            }
        }

        return lastLoginList;
    }

    /**
     * insert operation
     *
     * @param lastLogin LastLogin
     * @return long
     */
    @Override
    public long insert(LastLogin lastLogin) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LAST_LOGIN_INFO_KEY_PATIENT_ID, lastLogin.getPatientId());
        values.put(LAST_LOGIN_INFO_KEY_DOCTOR_ID, lastLogin.getDoctorId());
        values.put(LAST_LOGIN_INFO_KEY_DATE, lastLogin.getDateOfLogin());

        // insert row
        long result = sqLiteDatabase.insert(TABLE_LAST_LOGIN_INFO, null, values);

        sqLiteDatabase.close();

        return result;
    }
}
