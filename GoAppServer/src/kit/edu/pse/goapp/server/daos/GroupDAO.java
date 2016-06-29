package kit.edu.pse.goapp.server.daos;

import kit.edu.pse.goapp.server.datamodels.Group;


import java.util.List;



public interface GroupDAO {
	
	
	public void addGroup();
	
	public List<Group> getAllGroups();
	
	public void updateGroup();
	
	public void deleteGroup();
	
	public Group getGroupByID();

}
