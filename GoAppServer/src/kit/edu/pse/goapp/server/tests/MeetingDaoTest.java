/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class MeetingDaoTest extends MeetingDaoImpl {

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutName() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setPlaceX(1);
		dao.setPlaceY(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutPlaceX() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceY(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutPlaceY() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutPlaceZ() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceY(-1);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutTimestamp() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setDuration(2);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutDuration() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setType("Event");
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutType() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(7);
		dao.setUserId(1);
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithFalseType() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(7);
		dao.setUserId(1);
		dao.setType("test");
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMeetingWithoutUserId() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(7);
		dao.setType("Event");
		dao.addMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void addMeetingSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(1);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(7);
		dao.setType("Event");
		dao.setUserId(2);
		dao.addMeeting(mock);

	}

	@Test(expected = IOException.class)
	public void addMeetingFails() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(-1);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(7);
		dao.setType("Event");
		dao.setUserId(2);
		dao.addMeeting(mock);

	}

	@Test(expected = CustomServerException.class)
	public void deleteMeetingWithoutID() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.deleteMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void deleteMeetingSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(1);
		dao.deleteMeeting(mock);
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutMeetingId() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setName("test");
		dao.setPlaceX(1);
		dao.setPlaceY(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutName() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setPlaceX(1);
		dao.setPlaceY(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutPlaceX() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceY(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutPlaceY() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutPlaceZ() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutTimestamp() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutDuration() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateMeetingWithoutType() throws Exception {
		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void updateMeetingSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(2);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(3);
		dao.setDuration(3);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.updateMeeting(Mockito.mock(DatabaseConnection.class));

	}

	@Test()
	public void getTourByIdSuccessfully() throws Exception {
		GPS gps = new GPS(2, 3, 3);
		User user = new User(1, "user");
		Participant creator = new Participant(1, 1, user, MeetingConfirmation.PENDING);
		Tour expectedTour = new Tour(1, "test", gps, 1, 2, creator);
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(1);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(1);
		dao.setDuration(2);
		dao.setCreatorId(1);
		dao.setUserId(1);
		dao.setType("Tour");
		Tour tour = (Tour) dao.getMeetingByID(mock);
		assertEquals(expectedTour.getMeetingId(), tour.getMeetingId());
		assertEquals(expectedTour.getName(), tour.getName());
		assertEquals(expectedTour.getPlace().getX(), tour.getPlace().getX(), 3);
		assertEquals(expectedTour.getPlace().getY(), tour.getPlace().getY(), 3);
		assertEquals(expectedTour.getPlace().getZ(), tour.getPlace().getZ(), 3);
		assertEquals(expectedTour.getTimestamp(), tour.getTimestamp());
		assertEquals(expectedTour.getDuration(), tour.getDuration());
		assertEquals(expectedTour.getCreator().getParticipantId(), tour.getCreator().getParticipantId());

	}

	@Test()
	public void getEventByIdSuccessfully() throws Exception {
		GPS gps = new GPS(2, 3, 3);
		User user = new User(1, "user");
		Participant creator = new Participant(1, 1, user, MeetingConfirmation.PENDING);
		Event expectedEvent = new Event(1, "test", gps, 1, 2, creator);
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setMeetingId(1);
		dao.setName("test");
		dao.setPlaceX(2);
		dao.setPlaceY(3);
		dao.setPlaceZ(3);
		dao.setTimestamp(1);
		dao.setDuration(2);
		dao.setType("Event");
		dao.setCreatorId(1);
		dao.setUserId(1);
		Event event = (Event) dao.getMeetingByID(mock);
		assertEquals(expectedEvent.getMeetingId(), event.getMeetingId());
		assertEquals(expectedEvent.getName(), event.getName());
		assertEquals(expectedEvent.getPlace().getX(), event.getPlace().getX(), 3);
		assertEquals(expectedEvent.getPlace().getY(), event.getPlace().getY(), 3);
		assertEquals(expectedEvent.getPlace().getZ(), event.getPlace().getZ(), 3);
		assertEquals(expectedEvent.getTimestamp(), event.getTimestamp());
		assertEquals(expectedEvent.getDuration(), event.getDuration());
		assertEquals(expectedEvent.getCreator().getParticipantId(), event.getCreator().getParticipantId());

	}

	@Test()
	public void getAllMeetingsSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		MeetingDaoTest dao = new MeetingDaoTest();
		dao.setUserId(1);
		dao.getAllMeetings(mock);

	}

}
