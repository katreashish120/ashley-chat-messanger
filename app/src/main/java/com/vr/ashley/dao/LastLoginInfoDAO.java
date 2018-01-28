package com.vr.ashley.dao;

import android.database.Cursor;

import com.vr.ashley.domain.LastLogin;

import java.util.List;

/**
 * Interface for Last Login
 * Created by Ashish Katre
 */
public interface LastLoginInfoDAO {

    /**
     * delete operation
     *
     * @param id int
     * @return int
     */
    public int delete(int id);

    /**
     * select all operation
     *
     * @return LastLogin
     */
    public List<LastLogin> findAll();

    /**
     * insert operation
     *
     * @param lastLogin
     * @return long
     */
    public long insert(LastLogin lastLogin);
}
