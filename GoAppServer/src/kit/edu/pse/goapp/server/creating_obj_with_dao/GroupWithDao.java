/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;

import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupWithDao {

	/**
	 * Creates a group
	 * 
	 * @param groupId
	 *            groupId
	 * @return group group
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public Group createGroupWithDao(int groupId) throws IOException, CustomServerException {
		GroupDAO dao = new GroupDaoImpl();
		return createGroupWithDao(groupId, dao);
	}

	protected Group createGroupWithDao(int groupId, GroupDAO dao) throws IOException, CustomServerException {
		dao.setGroupId(groupId);
		Group group = dao.getGroupByID();
		return group;
	}

}
