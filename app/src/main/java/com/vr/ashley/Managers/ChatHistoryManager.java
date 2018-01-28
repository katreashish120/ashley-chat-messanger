package com.vr.ashley.Managers;

import android.content.Context;
import android.database.Cursor;

import com.vr.ashley.dao.ChatHistoryDAO;
import com.vr.ashley.dao.ChatHistoryDAOImpl;
import com.vr.ashley.domain.ChatHistory;

import java.util.List;


/**
 * Created by ashish Katre
 */
public class ChatHistoryManager {

    //Dao
    private ChatHistoryDAO chatHistoryDAO;

    private ChatHistory chatHistory;

    /**
     * Getter for ChatHistory
     *
     * @return ChatHistory
     */
    public ChatHistory getChatHistory() {

        return chatHistory;
    }

    /**
     * Setter for ChatHistory
     *
     * @param chatHistory
     */
    public void setChatHistory(ChatHistory chatHistory) {

        this.chatHistory = chatHistory;
    }

    /**
     * Constructor of chatHistory
     *
     * @param newChatHistory
     */
    public ChatHistoryManager(ChatHistory newChatHistory) {

        this.chatHistory = newChatHistory;
    }

    /**
     * Default Constructor
     */
    public ChatHistoryManager() {

    }

    /**
     * Static method for finding all contacts
     *
     * @param context
     * @return Cursor
     */
    public List<ChatHistory> findAll(Context context, int patientId, int doctorId) {

        chatHistoryDAO = new ChatHistoryDAOImpl(context);

        return chatHistoryDAO.findAll(patientId, doctorId);
    }

    /**
     * Insert contact
     *
     * @param context
     * @return long
     */
    public long insert(Context context) {

        chatHistoryDAO = new ChatHistoryDAOImpl(context);
        return chatHistoryDAO.insert(this.chatHistory);
    }

    /**
     * delete contacts
     *
     * @param context
     * @return int
     */
    public int delete(Context context) {

        chatHistoryDAO = new ChatHistoryDAOImpl(context);
        return chatHistoryDAO.delete(this.chatHistory.getId());
    }
}
