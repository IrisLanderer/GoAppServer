package kit.edu.pse.goapp.server.converter.daos;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

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
		dao.setAdmin(groupMemberJsonObject.isAdmin);
		return dao;
	}
	
	public static class GroupMember {
		private int groupId;
		private int userId;
		private boolean isAdmin;
		
		
		public int getGroupId() {
			return groupId;
		}
		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public boolean isAdmin() {
			return isAdmin;
		}
		public void setAdmin(boolean isAdmin) {
			this.isAdmin = isAdmin;
		}
		
		
		
	}

}
