/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;

/**
 * @author grisc
 *
 */
public class ObjectConverterTest {

	@Test
	public void testObjectToJson() {
		User user = new User(1, "testName");
		Participant creator = new Participant(1, 15, user, MeetingConfirmation.CONFIRMED);
		Meeting meeting = new Tour(15, "testTour", new GPS(1, 2, 3), 0, 2, creator);

		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		String json = converter.serialize(meeting, Meeting.class);
		String expected = "{\"type\":\"Tour\",\"center\":{\""
				+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
				+ ":15,\"name\":\"testTour\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
				+ "\":3.0},\"timespamp\":0,\"duration\":2,\"creator"
				+ "\":{\"participantId\":1,\"meetingId\":15,\"user\":{\"userId\":1,\"name\":\"testName\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
				+ "\"CONFIRMED\"},\"participants\":[]}";

		assertEquals(expected, json);
	}

	@Test
	public void testJsonToObject() {

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
	public void testDoubleDirection() {
		User user = new User(1, "testName");
		Participant creator = new Participant(1, 15, user, MeetingConfirmation.CONFIRMED);
		Meeting expected = new Tour(15, "testTour", new GPS(1, 2, 3), 0, 2, creator);

		ObjectConverter<Meeting> converter = new ObjectConverter<>();
		String json = converter.serialize(expected, Meeting.class);
		
		Meeting meeting = converter.deserialize(json, Meeting.class);
		assertEquals(expected, meeting);	
		
	}
}
