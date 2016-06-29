package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;

public class GroupMemberDaoImpl implements GroupMemberDAO{
	
	private int groupId;
	private int userId;
	private boolean isAdmin;
	public GroupMemberDaoImpl(int groupId, int userId, boolean isAdmin) {
		super();
		this.groupId = groupId;
		this.userId = userId;
		this.isAdmin = isAdmin;
	}


	@Override
	public void addMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdmin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getAllAdminGroups() {
		// TODO Auto-generated method stub
		return null;
	}

}
