/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.GroupMember;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class DatamodelsTest {

	@Test
	public void testEqualsGroupSuccessfully() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(1, "test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupNull() {
		Group object1 = new Group(1, "test");
		Group object2 = null;
		assertFalse(object1.equals(object2));
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

	@Test
	public void testEqualsGroupMemberSuccessfully() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		GroupMember object2 = new GroupMember();
		object2.setGroupId(1);
		object2.setUserId(1);
		object2.setAdmin(true);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupMemberNull() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		GroupMember object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourSuccessfully() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(1, "test", gps, 4, 5, creator);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsTourNull() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventSuccessfully() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(1, "test", gps, 4, 5, creator);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsEventNull() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testIsCreatorSuccessfully() throws CustomServerException, IOException {
		User user = new User(1, "test");
		Group group = new Group(1, "group");
		group.isAdmin(user);

	}

	@Test
	public void testEqualsParticipantSuccessfully() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipnatNull() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsSuccessfully() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = new GPS(1, 2, 3);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsNull() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsUserSuccessfully() {
		User object1 = new User(1, "test");
		User object2 = new User(1, "test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsUserNull() {
		User object1 = new User(1, "test");
		User object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationSuccessfully() {
		Notification object1 = new Notification("test");
		Notification object2 = new Notification("test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationNull() {
		Notification object1 = new Notification("test");
		Notification object2 = null;
		assertFalse(object1.equals(object2));
	}

}
