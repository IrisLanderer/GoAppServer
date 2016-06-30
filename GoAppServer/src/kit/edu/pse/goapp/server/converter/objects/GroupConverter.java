package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GPS_DAO;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

public class GroupConverter implements ObjectConverter<Group> {

	@Override
	public String serialize(Group group) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(group);
		return jsonString;
	}

	

	

}
