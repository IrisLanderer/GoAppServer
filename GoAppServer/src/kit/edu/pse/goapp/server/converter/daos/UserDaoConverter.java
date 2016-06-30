package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GPS_DAO;
import kit.edu.pse.goapp.server.daos.UserDAO;

public class UserDaoConverter implements DaoConverter<UserDAO> {

	@Override
	public UserDAO parse(String jsonString) {
		Gson gson = new Gson();
		UserDAO userDao = gson.fromJson(jsonString, UserDAO.class);
		return userDao;
	}

}
