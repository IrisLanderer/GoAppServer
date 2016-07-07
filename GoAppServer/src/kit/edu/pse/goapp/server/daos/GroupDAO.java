package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Group;

public interface GroupDAO {

	public void addGroup() throws IOException;

	public List<Group> getAllGroups() throws IOException;

	public void updateGroup() throws IOException;

	public void deleteGroup() throws IOException;

	public Group getGroupByID() throws IOException;

	void setName(String name);

	String getName();

	void setGroupId(int groupId);

	int getGroupId();

	void setUserId(int userId);

	int getUserId();

}
