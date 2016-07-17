/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Interface userDAO
 * 
 */
public interface UserDAO {

    /**
     * Add user
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void addUser() throws IOException, CustomServerException;

    /**
     * Deletes user
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public void deleteUser() throws IOException, CustomServerException;

    /**
     * Updates user
     * 
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomUserException
     */
    public void updateUser() throws IOException, CustomServerException;

    /**
     * Return list of all users
     * 
     * @return users list of all users
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public List<User> getAllUsers() throws IOException, CustomServerException;

    /**
     * Return user by its ID
     * 
     * @return user user
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public User getUserByID() throws IOException, CustomServerException;

    /**
     * Return google ID
     * 
     * @return googleID String googleID
     */
    public String getGoogleID();

    /**
     * Return user by user ID
     * 
     * @return user user
     * @throws IOException
     *             IOException
     * @throws CustomServerException
     *             CustomServerException
     */
    public User getUserByGoogleID() throws IOException, CustomServerException;

    /**
     * Return userId
     * 
     * @return userId userId
     */
    public int getUserId();

    /**
     * Return name
     * 
     * @return name String name
     */
    public String getName();

    /**
     * Set user name
     * 
     * @param name
     *            user name
     */
    public void setName(String name);

    /**
     * Set userId
     * 
     * @param userId
     *            userId
     */
    public void setUserId(int userId);

    /**
     * set googleId
     * 
     * @param googleId
     *            googleId
     */
    void setGoogleId(String googleId);

    /**
     * Set notification enabled
     * 
     * @param notificationEnabled
     *            notifications enabled
     */
    void setNotificationEnabled(boolean notificationEnabled);

    /**
     * Shows if notifications are enabled
     * 
     * @return boolean true if notifications are enabled, else false
     */
    boolean isNotificationEnabled();

}
