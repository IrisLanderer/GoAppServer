/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.GroupMember;
import kit.edu.pse.goapp.server.datamodels.MeetingCenter;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;

/**
 * @author Iris
 *
 */
public class DatamodelsEqualsTest {

	@Test
	public void testEqualsGroupSuccessfully() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(1, "test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupSuccessfullyBecauseOfSameObj() {
		Group object1 = new Group(1, "test");
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsGroupFailsBecauseOfId() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(2, "test");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupFailsBecauseOfName() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupFailsBecauseOfMembers() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(1, "test");
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> members = new ArrayList<>();
		members.add(user1);
		members.add(user2);
		object1.setGroupMembers(members);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupFailsBecauseOfAdmins() {
		Group object1 = new Group(1, "test");
		Group object2 = new Group(1, "test");
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		List<User> admins = new ArrayList<>();
		admins.add(user1);
		admins.add(user2);
		object1.setAdmins(admins);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupNull() {
		Group object1 = new Group(1, "test");
		Group object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupFailsBecauseOfDifferentClass() {
		Group object1 = new Group(1, "test");
		User object2 = new User(2, "test");
		assertFalse(object1.equals(object2));
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
	public void testEqualsGroupMemberSuccessfullyBecauseOfSameObj() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsGroupMemberFailsBecauseOfGroupId() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		GroupMember object2 = new GroupMember();
		object2.setGroupId(2);
		object2.setUserId(1);
		object2.setAdmin(true);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupMemberFailsBecauseOfUserId() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		GroupMember object2 = new GroupMember();
		object2.setGroupId(1);
		object2.setUserId(2);
		object2.setAdmin(true);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGroupMemberFailsBecauseOfAdminStatus() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		GroupMember object2 = new GroupMember();
		object2.setGroupId(1);
		object2.setUserId(1);
		object2.setAdmin(false);
		assertFalse(object1.equals(object2));
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
	public void testEqualsGroupMemberFailsBecauseOfDifferentClass() {
		GroupMember object1 = new GroupMember();
		object1.setGroupId(1);
		object1.setUserId(1);
		object1.setAdmin(true);
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantSuccessfully() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantSuccessfullyBecauseOfSameObj() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsParticipantFailsBecauseOfParticipantId() {
		User user1 = new User(2, "user1");
		Participant object1 = new Participant(1, 1, user1, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(2, 1, user1, MeetingConfirmation.PENDING);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantFailsBecauseOfMeetingId() {
		User user1 = new User(2, "user1");
		Participant object1 = new Participant(1, 1, user1, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(1, 2, user1, MeetingConfirmation.PENDING);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantFailsBecauseOfUser() {
		User user1 = new User(2, "user1");
		User user2 = new User(1, "user2");
		Participant object1 = new Participant(1, 1, user1, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(1, 1, user2, MeetingConfirmation.PENDING);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantFailsBecauseOfConfirmation() {
		User user1 = new User(2, "user1");
		Participant object1 = new Participant(1, 1, user1, MeetingConfirmation.PENDING);
		Participant object2 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantNull() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsParticipantDifferentClass() {
		User user = new User(2, "user");
		Participant object1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsSuccessfully() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = new GPS(1, 2, 3);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsSuccessfullyBecauseOfSameObj() {
		GPS object1 = new GPS(1, 2, 3);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsGpsFailsBecauseOfX() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = new GPS(2, 2, 3);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsFailsBecauseOfY() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = new GPS(1, 1, 3);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsFailsBecauseOfZ() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = new GPS(1, 2, 1);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsNull() {
		GPS object1 = new GPS(1, 2, 3);
		GPS object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsGpsFailsBecauseOfDifferentClass() {
		GPS object1 = new GPS(1, 2, 3);
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsUserSuccessfully() {
		User object1 = new User(1, "test");
		User object2 = new User(1, "test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsUserSuccessfullyBecauseOfSameobj() {
		User object1 = new User(1, "test");
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsUserFailsBecauseOfId() {
		User object1 = new User(1, "test");
		User object2 = new User(2, "test");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsUserFailsBecauseOfName() {
		User object1 = new User(1, "test");
		User object2 = new User(1, "user2");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsUserNull() {
		User object1 = new User(1, "test");
		User object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsUserFailsBecauseOfDifferentClass() {
		User object1 = new User(1, "test");
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationSuccessfully() {
		Notification object1 = new Notification("test");
		Notification object2 = new Notification("test");
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationSuccessfullyBecauseOfSameObj() {
		Notification object1 = new Notification("test");
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsNotificationFailsBecauseOfText() {
		Notification object1 = new Notification("test");
		Notification object2 = new Notification("hallo");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationNull() {
		Notification object1 = new Notification("test");
		Notification object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsNotificationBecauseOfDifferentClass() {
		Notification object1 = new Notification("test");
		Group object2 = new Group(1, "group");
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
	public void testEqualsTourSuccessfullyBecauseOfSameObj() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsTourFailsBecauseOfMeetingId() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(2, "test", gps, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfName() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(1, "tour", gps, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfGps() {
		GPS gps1 = new GPS(1, 4, 5);
		GPS gps2 = new GPS(52, 3, 2);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps1, 4, 5, creator);
		Tour object2 = new Tour(1, "test", gps2, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfTimestamp() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(1, "test", gps, 3, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfDuration() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(1, "test", gps, 4, 3, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfCreator() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant creator2 = new Participant(7, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator1);
		Tour object2 = new Tour(1, "test", gps, 4, 5, creator2);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsTourFailsBecauseOfParticipants() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		List<Participant> participants = new ArrayList<>();
		participants.add(creator);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Tour object2 = new Tour(1, "test", gps, 4, 5, creator);
		object1.setParticipants(participants);
		assertFalse(object1.equals(object2));
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
	public void testEqualsTourFailsBecauseOfDifferentClass() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Tour object1 = new Tour(1, "test", gps, 4, 5, creator);
		Group object2 = new Group(1, "test");
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
	public void testEqualsEventSuccessfullyBecauseOfSameObj() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsEventFailsBecauseOfMeetingId() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(2, "test", gps, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfName() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(1, "tour", gps, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfGps() {
		GPS gps1 = new GPS(1, 4, 5);
		GPS gps2 = new GPS(52, 3, 2);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps1, 4, 5, creator);
		Event object2 = new Event(1, "test", gps2, 4, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfTimestamp() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(1, "test", gps, 3, 5, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfDuration() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(1, "test", gps, 4, 3, creator);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfCreator() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator1 = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Participant creator2 = new Participant(7, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator1);
		Event object2 = new Event(1, "test", gps, 4, 5, creator2);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsEventFailsBecauseOfParticipants() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		List<Participant> participants = new ArrayList<>();
		participants.add(creator);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Event object2 = new Event(1, "test", gps, 4, 5, creator);
		object1.setParticipants(participants);
		assertFalse(object1.equals(object2));
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
	public void testEqualsEventFailsBecauseOfDifferentClass() {
		GPS gps = new GPS(1, 4, 5);
		User user = new User(2, "user");
		Participant creator = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		Event object1 = new Event(1, "test", gps, 4, 5, creator);
		Group object2 = new Group(1, "test");
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsMeetingCenterSuccessfully() {
		GPS place = new GPS(1, 2, 3);
		MeetingCenter object1 = new MeetingCenter(place);
		MeetingCenter object2 = new MeetingCenter(place);
		assertTrue(object1.equals(object2));
	}

	@Test
	public void testEqualsMeetingCenterSuccessfullyBecauseOfSameObj() {
		GPS place = new GPS(1, 2, 3);
		MeetingCenter object1 = new MeetingCenter(place);
		assertTrue(object1.equals(object1));
	}

	@Test
	public void testEqualsMeetingCenterFailsBecauseOfPlace() {
		GPS place1 = new GPS(1, 2, 3);
		GPS place2 = new GPS(33, 2, 3);
		MeetingCenter object1 = new MeetingCenter(place1);
		MeetingCenter object2 = new MeetingCenter(place2);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsMeetingCenterFailsBecauseOfParticipants() {
		GPS place1 = new GPS(1, 2, 3);
		List<Participant> participants = new ArrayList<>();
		Participant participant = new Participant(1, 1, null, MeetingConfirmation.CONFIRMED);
		participants.add(participant);
		MeetingCenter object1 = new MeetingCenter(place1);
		MeetingCenter object2 = new MeetingCenter(place1);
		object1.setParticipants(participants);
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsMeetingCenterFailsBecauseOfNull() {
		GPS place = new GPS(1, 2, 3);
		MeetingCenter object1 = new MeetingCenter(place);
		MeetingCenter object2 = null;
		assertFalse(object1.equals(object2));
	}

	@Test
	public void testEqualsMeetingCenterFailsBecauseOfDifferentClass() {
		GPS place = new GPS(1, 2, 3);
		MeetingCenter object1 = new MeetingCenter(place);
		Group object2 = new Group(1, "group");
		assertFalse(object1.equals(object2));
	}

}
