package com.vr.ashley.dao;

import android.database.Cursor;

import com.vr.ashley.domain.ChatHistory;
import com.vr.ashley.domain.LastLogin;

import java.util.List;

/**
 * Interface for Last Login
 * Created by Ashish Katre
 */
public interface ChatHistoryDAO {

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
     * @return List<ChatHistory>
     */
    public List<ChatHistory> findAll(int patientId, int doctorId);

    /**
     * insert operation
     *
     * @param chatHistory
     * @return long
     */
    public long insert(ChatHistory chatHistory);
}
