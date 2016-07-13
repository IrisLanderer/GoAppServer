/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author iris
 *
 */
public class GroupDaoTest extends GroupDaoImpl {

	@Test(expected = CustomServerException.class)
	public void addGroupWithoutName() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.addGroup(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void addGroupSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(1);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setName("Group 1");
		dao.addGroup(mock);

	}

	@Test(expected = IOException.class)
	public void addGroupFails() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(-1);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setName("Group 1");
		dao.addGroup(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getGroupWithoutID() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.getGroupByID(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getGroupByIdSuccessfully() throws Exception {
		Group expectedGroup = new Group(1, "test");
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setGroupId(1);
		dao.setName("test");
		Group group = dao.getGroupByID(mock);
		assertEquals(expectedGroup.getId(), group.getId());
		assertEquals(expectedGroup.getName(), group.getName());

	}

	@Test(expected = CustomServerException.class)
	public void deleteGroupWithoutID() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.deleteGroup(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void deleteGroupSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setGroupId(1);
		dao.deleteGroup(mock);

	}

	@Test(expected = CustomServerException.class)
	public void deleteGroupFails() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupDaoTest dao = new GroupDaoTest();
		dao.deleteGroup(mock);

	}

	@Test(expected = CustomServerException.class)
	public void updateGroupWithoutID() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.updateGroup(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateGroupWithoutName() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.setGroupId(1);
		dao.updateGroup(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateGroupWithEmptyName() throws Exception {
		GroupDaoTest dao = new GroupDaoTest();
		dao.setGroupId(1);
		dao.setName("");
		dao.updateGroup(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void updateGroupSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setGroupId(1);
		dao.setName("update");
		dao.updateGroup(mock);

	}

	@Test()
	public void getAllGroupsSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupDaoTest dao = new GroupDaoTest();
		dao.setUserId(1);
		dao.getAllGroups(mock);

	}

}
