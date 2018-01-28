package com.vr.ashley.Managers;

import android.content.Context;
import com.vr.ashley.dao.LastLoginInfoDAO;
import com.vr.ashley.dao.LastLoginInfoDAOImpl;
import com.vr.ashley.domain.LastLogin;

import java.util.List;


/**
 * Created by ashish Katre
 */
public class LastLoginManager {

    //Dao
    private LastLoginInfoDAO lastLoginInfoDAO;

    private LastLogin lastLogin;

    /**
     * Getter for lastLogin
     *
     * @return lastLogin
     */
    public LastLogin getLastLogin() {

        return lastLogin;
    }

    /**
     * Setter for lastLogin
     *
     * @param lastLogin
     */
    public void setLastLogin(LastLogin lastLogin) {

        this.lastLogin = lastLogin;
    }

    /**
     * Constructor of lastLogin
     *
     * @param newLastLogin
     */
    public LastLoginManager(LastLogin newLastLogin) {

        this.lastLogin = newLastLogin;
    }

    /**
     * Default Constructor
     */
    public LastLoginManager() {

    }

    /**
     * Static method for finding all contacts
     *
     * @param context
     * @return Cursor
     */
    public List<LastLogin> findAll(Context context) {

        lastLoginInfoDAO = new LastLoginInfoDAOImpl(context);

        return lastLoginInfoDAO.findAll();
    }

    /**
     * Insert contact
     *
     * @param context
     * @return long
     */
    public long insert(Context context) {

        lastLoginInfoDAO = new LastLoginInfoDAOImpl(context);
        return lastLoginInfoDAO.insert(this.lastLogin);
    }

    /**
     * delete contacts
     *
     * @param context
     * @return int
     */
    public int delete(Context context) {

        lastLoginInfoDAO = new LastLoginInfoDAOImpl(context);
        return lastLoginInfoDAO.delete(this.lastLogin.getId());
    }
}
