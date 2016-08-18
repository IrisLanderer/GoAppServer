/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class MeetingTest extends Meeting {

	public MeetingTest() {
		super(0, null, null, 0, 0, null);
	}

	@Test
	public void isParticipantTest() throws CustomServerException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		MeetingTest meeting = new MeetingTest();
		meeting.isParticipant(user1, 1, participants);

	}

	@Test(expected = CustomServerException.class)
	public void isNotParticipantTest() throws CustomServerException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part2);
		MeetingTest meeting = new MeetingTest();
		meeting.isParticipant(user1, 1, participants);

	}

	@Test
	public void testIsParticipantSuccessfully() throws CustomServerException, IOException {
		User user = new User(1, "test");
		Participant creator = new Participant(1, 1, user, MeetingConfirmation.CONFIRMED);
		GPS gps = new GPS(1, 2, 3);
		Meeting meeting = new Event(1, "meeting", gps, 3, 2, creator);
		meeting.isParticipant(user);

	}

	@Test
	public void isCreatorTest() throws CustomServerException, IOException {
		User user = new User(1, "user");
		Participant creator = new Participant(1, 1, user, MeetingConfirmation.CONFIRMED);
		MeetingTest meeting = new MeetingTest();
		meeting.isCreator(user, creator);
	}

	@Test(expected = CustomServerException.class)
	public void isNotCreatorTest() throws CustomServerException, IOException {
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant creator = new Participant(1, 1, user2, MeetingConfirmation.CONFIRMED);
		MeetingTest meeting = new MeetingTest();
		meeting.isCreator(user1, creator);
	}

	@Test(expected = CustomServerException.class)
	public void testIsCreatorFails() throws CustomServerException, IOException {
		User user = new User(1, "test");
		Participant creator = new Participant(1, 1, user, MeetingConfirmation.CONFIRMED);
		GPS gps = new GPS(1, 2, 3);
		Meeting meeting = new Event(1, "meeting", gps, 3, 2, creator);
		meeting.isCreator(user);

	}

}
