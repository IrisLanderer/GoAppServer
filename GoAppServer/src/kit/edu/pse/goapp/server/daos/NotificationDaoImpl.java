package kit.edu.pse.goapp.server.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.datamodels.User;

public class NotificationDaoImpl implements NotificationDAO{

	private static Map<Integer, List<Notification>> map = new HashMap<Integer, List<Notification>>();
	private int userId;
	private List<Notification> notifications;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public List<Notification> userGetNotification() {
		return map.get(userId);
	}

	@Override
	public void setNotification() {
		map.put(userId,notifications);		
	}

}
