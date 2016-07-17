/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author grisc
 *
 */
public class ObjectConverterTest {

	@Test
	public void testObjectToJson() throws CustomServerException {
		User user = new User(1, "testName");
		Participant creator = new Participant(1, 15, user, MeetingConfirmation.CONFIRMED);
		Meeting meeting = new Tour(15, "testTour", new GPS(1, 2, 3), 0, 2, creator);

		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		String json = converter.serialize(meeting, Meeting.class);
		String expected = "{\"type\":\"Tour\",\"center\":{\""
				+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
				+ ":15,\"name\":\"testTour\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
				+ "\":3.0},\"timestamp\":0,\"duration\":2,\"creator"
				+ "\":{\"participantId\":1,\"meetingId\":15,\"user\":{\"userId\":1,\"name\":\"testName\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
				+ "\"CONFIRMED\"},\"participants\":[]}";

		assertEquals(expected, json);
	}

	@Test
	public void testJsonToObject() throws CustomServerException {

		String json = "{\"type\":\"Tour\",\"center\":{\""
				+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
				+ ":15,\"name\":\"testTour\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
				+ "\":3.0},\"timespamp\":0,\"duration\":2,\"creator"
				+ "\":{\"participantId\":1,\"meetingId\":15,\"user\":{\"userId\":1,\"name\":\"testName\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
				+ "\"CONFIRMED\"},\"participants\":[]}";
		User user = new User(1, "testName");
		Participant creator = new Participant(1, 15, user, MeetingConfirmation.CONFIRMED);
		Meeting expected = new Tour(15, "testTour", new GPS(1, 2, 3), 0, 2, creator);

		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		Meeting meeting = converter.deserialize(json, Meeting.class);
		assertEquals(expected, meeting);

	}

	@Test
	public void testJsonToListObject() throws CustomServerException {

		String json = "[{\"groupId\":1,\"name\":\"test\",\"admins\":[{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],"
				+ "\"groups\":[]}],\"members\":[{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]}]},"
				+ "{\"groupId\":2,\"name\":\"pse\",\"admins\":[{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],"
				+ "\"groups\":[]}],\"members\":[{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]}]}]";
		System.out.println(json);
		Group expectedGroup1 = new Group(1, "test");
		Group expectedGroup2 = new Group(2, "pse");
		ObjectConverter<List<Group>> converter = new ObjectConverter<>();
		List<Group> groups = new ArrayList<>();
		groups = converter.deserializeList(json, Group[].class);
		System.out.println(groups);
		assertEquals(expectedGroup1.getId(), groups.get(0).getId());
		assertEquals(expectedGroup2.getId(), groups.get(1).getId());
		assertEquals(expectedGroup1.getName(), groups.get(0).getName());
		assertEquals(expectedGroup2.getName(), groups.get(1).getName());

	}

	// @Test
	// public void testJsonToMeetingListObject() throws CustomServerException {
	//
	// String json =
	// "[{\"type\":\"Tour\",\"center\":{\"participants\":[],\"place\":{\"x\":55.0,\"y\":4.0,\"z\":0.0}},\"meetingId\":32,\"name\":\"rgrg\",\"place\":{\"x\":55.0,\"y\":4.0,\"z\":0.0},\"timestamp\":1,\"duration\":1,\"creator\":{\"participantId\":178,\"meetingId\":32,\"user\":{\"userId\":1,\"name\":\"Iris\","
	// +
	// "\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":\"PENDING\"},\"participants\":[{\"participantId\":178,\"meetingId\":32,\"user\":{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,"
	// +
	// "\"meetings\":[],\"groups\":[]},\"confirmation\":\"PENDING\"}]}{\"type\":\"Tour\",\"center\":{\"participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},"
	// +
	// "\"meetingId\":31,\"name\":\"pse\",\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0},\"timestamp\":157,\"duration\":3,\"creator\":{\"participantId\":177,"
	// +
	// "\"meetingId\":31,\"user\":{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},"
	// +
	// "\"confirmation\":\"PENDING\"},\"participants\":[{\"participantId\":177,\"meetingId\":31,\"user\":{\"userId\":1,\"name\":\"Iris\","
	// +
	// "\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":\"PENDING\"}]}]";
	// User user = new User(1, "Iris");
	// Participant creator1 = new Participant(178, 32, user,
	// MeetingConfirmation.PENDING);
	// Participant creator2 = new Participant(177, 31, user,
	// MeetingConfirmation.PENDING);
	// Meeting expected1 = new Tour(32, "rgrg", new GPS(55, 4, 0), 1, 1,
	// creator1);
	// Meeting expected2 = new Tour(31, "pse", new GPS(1, 2, 3), 157, 3,
	// creator2);
	// ObjectConverter<Meeting> converter = new ObjectConverter<>();
	// List<Meeting> meetings = new ArrayList<>();
	// meetings = converter.deserializeList(json, Meeting[].class);
	// assertEquals(expected1.getMeetingId(), meetings.get(0).getMeetingId());
	//
	//
	//
	// }

	@Test
	public void testDoubleDirection() throws CustomServerException {
		User user = new User(1, "testName");
		Participant creator = new Participant(1, 15, user, MeetingConfirmation.CONFIRMED);
		Meeting expected = new Tour(15, "testTour", new GPS(1, 2, 3), 0, 2, creator);

		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		String json = converter.serialize(expected, Meeting.class);

		Meeting meeting = converter.deserialize(json, Meeting.class);
		assertEquals(expected, meeting);

	}
}
