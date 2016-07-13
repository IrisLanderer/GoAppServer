/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class ParticipantDaoTest extends ParticipantDaoImpl {

	@Test(expected = CustomServerException.class)
	public void addParticipantWithoutMeetingId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setUserId(1);
		dao.addParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addParticipantWithoutUserId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setMeetingId(1);
		dao.addParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void addParticipantSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(1);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setMeetingId(1);
		dao.setUserId(2);
		dao.addParticipant(mock);

	}

	@Test(expected = IOException.class)
	public void addParticipantFails() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		Mockito.when(mock.insert(Mockito.anyString())).thenReturn(-1);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setMeetingId(1);
		dao.setUserId(2);
		dao.addParticipant(mock);

	}

	@Test(expected = CustomServerException.class)
	public void deleteParticipantWithoutParticipantId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.deleteParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void deleteParticipantSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.deleteParticipant(mock);

	}

	@Test(expected = CustomServerException.class)
	public void updateParticipantWithoutParticipantId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setUserId(1);
		dao.setMeetingId(2);
		dao.setConfirmation(MeetingConfirmation.CONFIRMED);
		dao.updateParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateParticipantWithoutUserId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.setMeetingId(2);
		dao.setConfirmation(MeetingConfirmation.CONFIRMED);
		dao.updateParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateParticipantWithoutMeetingId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.setUserId(2);
		dao.setConfirmation(MeetingConfirmation.CONFIRMED);
		dao.updateParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateParticipantWithoutConfirmation() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.setUserId(1);
		dao.setMeetingId(2);
		dao.updateParticipant(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void updateParticipantSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.setUserId(1);
		dao.setMeetingId(2);
		dao.setConfirmation(MeetingConfirmation.CONFIRMED);
		dao.updateParticipant(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getParticipantByIdWithoutParticipantId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.getParticipantByID(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getParticipantByIdSuccessfully() throws Exception {
		User user = new User(1, "test");
		Participant expectedParticipant = new Participant(1, 2, user, MeetingConfirmation.PENDING);
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setParticipantId(1);
		dao.setMeetingId(2);
		dao.setUserId(1);
		dao.setConfirmation(MeetingConfirmation.PENDING);
		Participant participant = dao.getParticipantByID(mock);
		assertEquals(expectedParticipant.getParticipantId(), participant.getParticipantId());
		assertEquals(expectedParticipant.getMeetingId(), participant.getMeetingId());
		assertEquals(expectedParticipant.getUser().getId(), participant.getUser().getId());
		assertEquals(expectedParticipant.getConfirmation(), participant.getConfirmation());

	}

	@Test(expected = CustomServerException.class)
	public void getAllParticipantsWithoutMeetingId() throws Exception {
		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.getAllParticipants(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getAllParticipantsSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		ParticipantDaoTest dao = new ParticipantDaoTest();
		dao.setMeetingId(1);
		dao.getAllParticipants(mock);

	}

}
