package kit.edu.pse.goapp.server.daos;

import kit.edu.pse.goapp.server.datamodels.Group;

import java.io.IOException;
import java.util.List;



public interface GroupDAO {
	
	public void addGroup() throws IOException;
	
	public List<Group> getAllGroups();
	
	public void updateGroup() throws IOException;
	
	public void deleteGroup() throws IOException;
	
	public Group getGroupByID() throws IOException;

	void setName(String name);

	String getName();

	void setGroupId(int groupId);

	int getGroupId();

}
