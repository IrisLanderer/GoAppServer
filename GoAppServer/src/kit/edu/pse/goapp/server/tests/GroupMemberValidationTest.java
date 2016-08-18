/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.GroupMemberValidation;

/**
 * @author Iris
 *
 */
public class GroupMemberValidationTest {

	@Test
	public void checkIfMemberTest() throws CustomServerException {
		GroupMemberValidation validation = new GroupMemberValidation();
		List<User> groupMembers = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		groupMembers.add(user1);
		groupMembers.add(user2);
		validation.checkIfMember(groupMembers, 1);
	}

	@Test(expected = CustomServerException.class)
	public void checkIfNotMemberTest() throws CustomServerException {
		GroupMemberValidation validation = new GroupMemberValidation();
		List<User> groupMembers = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		groupMembers.add(user1);
		groupMembers.add(user2);
		validation.checkIfMember(groupMembers, 3);
	}

	@Test
	public void checkIfNotAlreadyMemberTest() throws CustomServerException {
		GroupMemberValidation validation = new GroupMemberValidation();
		List<User> groupMembers = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		groupMembers.add(user1);
		groupMembers.add(user2);
		validation.checkIfAlreadyMember(groupMembers, 3);
	}

	@Test(expected = CustomServerException.class)
	public void checkIfAlreadyMemberTest() throws CustomServerException {
		GroupMemberValidation validation = new GroupMemberValidation();
		List<User> groupMembers = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		groupMembers.add(user1);
		groupMembers.add(user2);
		validation.checkIfAlreadyMember(groupMembers, 1);
	}

}
