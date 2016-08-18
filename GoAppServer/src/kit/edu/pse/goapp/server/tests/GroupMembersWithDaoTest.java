/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.creating_obj_with_dao.GroupMembersWithDao;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupMembersWithDaoTest extends GroupMembersWithDao {

	@Test
	public void membersWithDaoTest() throws IOException, CustomServerException {
		GroupDAO dao = Mockito.mock(GroupDAO.class);

		Group group = new Group(1, "group");
		Mockito.when(dao.getGroupByID()).thenReturn(group);
		User user = new User(1, "user");
		List<User> groupMembers = new ArrayList<>();
		groupMembers.add(user);
		group.setGroupMembers(groupMembers);
		GroupMembersWithDaoTest groupMembersWithDao = new GroupMembersWithDaoTest();
		assertEquals(groupMembers, groupMembersWithDao.createMembersWithDao(group));
	}

	@Test
	public void testCallMembersWithDao() throws IOException, CustomServerException {
		GroupMembersWithDaoTest membersWithDaoTest = new GroupMembersWithDaoTest();
		membersWithDaoTest.createMembersWithDao(1);
	}

}
