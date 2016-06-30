package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.datamodels.User;

public class UserConverter implements ObjectConverter<User> {

	@Override
	public String serialize(User user) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(user);
		return jsonString;
	}

	@Override
	public String serialize(List<User> users) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(users);	
		return jsonString;
	}

	

}
