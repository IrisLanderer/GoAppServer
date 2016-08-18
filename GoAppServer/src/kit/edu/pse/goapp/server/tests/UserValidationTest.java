/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.UserValidation;

/**
 * @author Iris
 *
 */
public class UserValidationTest extends UserValidation {

	@Test
	public void userExistsTest() throws IOException, CustomServerException {
		UserValidationTest validation = new UserValidationTest();
		List<User> users = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		users.add(user1);
		users.add(user2);
		validation.userExists(users, 1);
	}

	@Test(expected = CustomServerException.class)
	public void userDoesNotExistTest() throws IOException, CustomServerException {
		UserValidationTest validation = new UserValidationTest();
		List<User> users = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		users.add(user1);
		users.add(user2);
		validation.userExists(users, 3);
	}

}
