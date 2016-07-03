package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;

public class UserDaoConverter implements DaoConverter<UserDAO> {

	@Override
	public UserDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		
		Gson gson = new Gson();
		User userJsonObject = gson.fromJson(jsonString, User.class);
		UserDAO dao = new UserDaoImpl();
		dao.setName(userJsonObject.getName());
		dao.setUserId(userJsonObject.getId());
		return dao;
	}

}
