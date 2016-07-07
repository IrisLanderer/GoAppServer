package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;

public class GroupDaoConverter implements DaoConverter<GroupDAO> {

	@Override
	public GroupDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}

		Gson gson = new Gson();
		Group groupJsonObject = gson.fromJson(jsonString, Group.class);
		GroupDAO dao = new GroupDaoImpl();
		dao.setName(groupJsonObject.getName());
		dao.setGroupId(groupJsonObject.getId());
		return dao;
	}
}
