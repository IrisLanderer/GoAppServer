/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Notification;;

/**
 * Interface Notification dao
 */
public interface NotificationDAO {

    /**
     * Returns list of notification of an user
     * 
     * @return notification list of notification
     */
    public List<Notification> userGetNotification();

    /**
     * Sets notifications of an user
     */
    public void setNotification();
}
