package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GroupMember;

public class GroupMemberDaoConverter implements DaoConverter<GroupMemberDAO> {

	@Override
	public GroupMemberDAO parse(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		Gson gson = new Gson();
		GroupMember groupMemberJsonObject = gson.fromJson(jsonString, GroupMember.class);
		GroupMemberDAO dao = new GroupMemberDaoImpl();
		dao.setGroupId(groupMemberJsonObject.getGroupId());
		dao.setUserId(groupMemberJsonObject.getUserId());
		dao.setAdmin(groupMemberJsonObject.isAdmin());
		return dao;
	}

}
