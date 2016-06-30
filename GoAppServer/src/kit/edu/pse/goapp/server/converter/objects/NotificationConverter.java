package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.datamodels.User;

public class NotificationConverter implements ObjectConverter<Notification> {

	@Override
	public String serialize(Notification notification) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(notification);
		return jsonString;
	}

	
}
