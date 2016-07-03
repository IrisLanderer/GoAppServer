package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

public interface GroupMemberDAO {
	
	public void addMember();
	public void deleteMember();
	public void updateMember();
	public List<User> getAllMembers();
	public void setAdmin();
	public List<Group> getAllAdminGroups();
	
	public int getGroupId();


	public void setGroupId(int groupId);


	public int getUserId();


	public void setUserId(int userId);


	public boolean isAdmin();


	public void setAdmin(boolean isAdmin);


}
