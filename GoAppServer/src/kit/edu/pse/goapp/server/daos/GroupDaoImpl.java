package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;

public class GroupDaoImpl implements GroupDAO{
	private int groupId;
	private String name;
	
	

	public GroupDaoImpl(int groupId, String name) {
		super();
		this.groupId = groupId;
		this.name = name;
	}

	@Override
	public void addGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getAllGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group getGroupByID() {
		// TODO Auto-generated method stub
		return null;
	}

}
