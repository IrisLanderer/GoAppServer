/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kit.edu.pse.goapp.server.datamodels.Notification;

/**
 * Implements interface NotificationDAO
 */
public class NotificationDaoImpl implements NotificationDAO {

    private static Map<Integer, List<Notification>> map = new HashMap<Integer, List<Notification>>();
    private int userId;
    private List<Notification> notifications;

    /**
     * Returns userId
     * 
     * @return userId userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets userId
     * 
     * @param userId
     *            userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns list of notifications
     * 
     * @return notifications list of notifications
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Set notifications
     * 
     * @param notifications
     *            listo of notifications
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Returns notifications of an user
     */
    @Override
    public List<Notification> userGetNotification() {
        return map.get(userId);
    }

    /**
     * Sets notifications
     */
    @Override
    public void setNotification() {
        map.put(userId, notifications);
    }

}
