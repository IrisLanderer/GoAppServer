/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.creating_obj_with_dao.GroupWithDao;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupWithDaoTest extends GroupWithDao {

	@Test
	public void groupWithDaoTest() throws IOException, CustomServerException {
		GroupDAO dao = Mockito.mock(GroupDAO.class);
		Group group = new Group(1, "group");
		Mockito.when(dao.getGroupByID()).thenReturn(group);
		GroupWithDaoTest groupWithDao = new GroupWithDaoTest();
		groupWithDao.createGroupWithDao(1, dao);

	}

	@Test
	public void testCallCreateGroupWithDao() throws IOException, CustomServerException {
		GroupWithDaoTest groupWithDao = new GroupWithDaoTest();
		groupWithDao.createGroupWithDao(1);
	}

}
