/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupMembersWithDao {

	/**
	 * Returns a list of users created with the dao
	 * 
	 * @param groupId
	 *            ID of group
	 * 
	 * @return groupMembers list of groupMembers
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public List<User> createMembersWithDao(int groupId) throws IOException, CustomServerException {
		GroupDAO dao = new GroupDaoImpl();
		dao.setGroupId(groupId);
		Group group = dao.getGroupByID();
		List<User> groupMembers = group.getGroupMembers();
		return groupMembers;
	}

}
