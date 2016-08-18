/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class GroupTest extends Group {

	/**
		 * 
		 */
	public GroupTest() {
		super(0, null);
	}

	@Test
	public void isMemberTest() throws CustomServerException, IOException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> tmpMembers = new ArrayList<>();
		tmpMembers.add(user1);
		tmpMembers.add(user2);
		GroupTest group = new GroupTest();
		group.isMember(user1, tmpMembers);

	}

	@Test(expected = CustomServerException.class)
	public void isNotMemberTest() throws CustomServerException, IOException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> tmpMembers = new ArrayList<>();
		tmpMembers.add(user1);
		GroupTest group = new GroupTest();
		group.isMember(user2, tmpMembers);

	}

	@Test
	public void isAdminTest() throws CustomServerException, IOException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> tmpAdmins = new ArrayList<>();
		tmpAdmins.add(user1);
		tmpAdmins.add(user2);
		GroupTest group = new GroupTest();
		group.isAdmin(user1, tmpAdmins);

	}

	@Test(expected = CustomServerException.class)
	public void isNotAdminTest() throws CustomServerException, IOException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> tmpAdmins = new ArrayList<>();
		tmpAdmins.add(user1);
		GroupTest group = new GroupTest();
		group.isAdmin(user2, tmpAdmins);

	}

	@Test
	public void testIsMemberSuccessfully() throws CustomServerException, IOException {
		User user = new User(1, "test");
		Group group = new Group(1, "group");
		group.isMember(user);

	}

	@Test
	public void testIsAdminSuccessfully() throws CustomServerException, IOException {
		User user = new User(1, "test");
		Group group = new Group(1, "group");
		group.isAdmin(user);

	}
}
