package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Notification;

public interface NotificationDAO {
	
	public List<Notification> userGetNotification();
}
