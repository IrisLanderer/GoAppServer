/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class UserDaoTest extends UserDaoImpl {

	@Test(expected = CustomServerException.class)
	public void addUserWithoutName() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setGoogleId("test");
		dao.addUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addUserWithEmptyName() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setName("");
		dao.setGoogleId("test");
		dao.addUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addUserWithoutGoogleId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setName("test");
		dao.addUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addUserWithEmptyGoogleId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setName("test");
		dao.setGoogleId("");
		dao.addUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void addUserSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(1);

		UserDaoTest dao = new UserDaoTest();
		dao.setName("User 1");
		dao.setGoogleId("googleID = 1");
		dao.setNotificationEnabled(true);
		dao.addUser(mock);

	}

	@Test(expected = IOException.class)
	public void addUserFails() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(-1);

		UserDaoTest dao = new UserDaoTest();
		dao.setName("User 1");
		dao.setGoogleId("googleID = 1");
		dao.setNotificationEnabled(true);
		dao.addUser(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getUserByIdWithoutId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.getUserByID(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getUserByIdSuccessfully() throws Exception {
		User expectedUser = new User(1, "test");
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		UserDaoTest dao = new UserDaoTest();
		dao.setUserId(1);
		dao.setName("test");
		User user = dao.getUserByID(mock);
		assertEquals(expectedUser.getId(), user.getId());
		assertEquals(expectedUser.getName(), user.getName());
	}

	@Test(expected = CustomServerException.class)
	public void deleteUserWithoutId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.deleteUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void deleteUserSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		UserDaoTest dao = new UserDaoTest();
		dao.setUserId(1);
		dao.deleteUser(mock);

	}

	@Test(expected = CustomServerException.class)
	public void updateUserWithoutId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setName("test");
		dao.updateUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateUserWithoutName() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setUserId(1);
		dao.updateUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateUserWithEmptyName() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setUserId(1);
		dao.setName("");
		dao.updateUser(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void updateUserSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		UserDaoTest dao = new UserDaoTest();
		dao.setUserId(1);
		dao.setName("test");
		dao.setNotificationEnabled(true);
		dao.updateUser(mock);

	}

	@Test()
	public void getAllUsersSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		UserDaoTest dao = new UserDaoTest();
		dao.getAllUsers(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getUserByGoogleIdWithoutGoogleId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.getUserByGoogleID(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void getUserByGoogleIdWithEmptyGoogleId() throws Exception {
		UserDaoTest dao = new UserDaoTest();
		dao.setGoogleId("");
		dao.getUserByGoogleID(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getUserByGoogleIdSuccessfully() throws Exception {
		User expectedUser = new User(1, "test");
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		UserDaoTest dao = new UserDaoTest();
		dao.setGoogleId("google");
		dao.setUserId(1);
		dao.setName("test");
		User user = dao.getUserByGoogleID(mock);
		assertEquals(expectedUser.getId(), user.getId());
		assertEquals(expectedUser.getName(), user.getName());
	}

}
