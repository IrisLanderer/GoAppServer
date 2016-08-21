/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.algorithm.MeetingGpsAlgorithm;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;

/**
 * @author Iris
 *
 */
public class MeetingGpsAlgorithmsTest extends MeetingGpsAlgorithm {

	@Test
	public void testSetGpsEventIsClose() {
		GPS gpsUser1 = new GPS(22, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		long currentTime = System.currentTimeMillis();
		Event event = new Event(1, "Event", gpsMeeting, currentTime, 60, part1);
		event.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		setGpsEvent(event);
		assertNotNull(user1.getGps());
	}

	@Test
	public void testSetGpsEventIsNotClose() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		long currentTime = System.currentTimeMillis();
		Event event = new Event(1, "Event", gpsMeeting, currentTime, 60, part1);
		event.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		setGpsEvent(event);
		assertNull(user1.getGps());
	}

	@Test
	public void testSetGpsEventParticipantNotConfirmed() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.REJECTED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		long currentTime = System.currentTimeMillis();
		Event event = new Event(1, "Event", gpsMeeting, currentTime, 60, part1);
		event.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		setGpsEvent(event);
		assertNull(user1.getGps());
	}

	@Test
	public void testSetGpsEventIsNotRunningTooEarly() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		long meetingTime = System.currentTimeMillis() + 40 * 60 * 1000;
		Event event = new Event(1, "Event", gpsMeeting, meetingTime, 60, part1);
		event.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		setGpsEvent(event);
		assertNull(user1.getGps());
	}

	@Test
	public void testSetGpsEventIsNotRunningTooLate() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		long meetingTime = System.currentTimeMillis() - 61 * 60 * 1000;
		Event event = new Event(1, "Event", gpsMeeting, meetingTime, 60, part1);
		event.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		setGpsEvent(event);
		assertNull(user1.getGps());
	}

	@Test
	public void testIsOver() {
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		long meetingTime = System.currentTimeMillis() - 61 * 60 * 1000;
		Event event = new Event(1, "Event", gpsMeeting, meetingTime, 60, part1);
		assertTrue(isOver(event));
	}

	@Test
	public void testIsNotOver() {
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		long meetingTime = System.currentTimeMillis();
		Event event = new Event(1, "Event", gpsMeeting, meetingTime, 60, part1);
		assertFalse(isOver(event));
	}

	// user 1 is not in blob
	// user 2 and 3 are in blob
	@Test
	public void testSetGpsTourIsInBlob() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsUser2 = new GPS(24, 22, 1);
		GPS gpsUser3 = new GPS(25, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		User user3 = new User(3, "user3");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		Participant part3 = new Participant(3, 1, user3, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		participants.add(part3);
		long currentTime = System.currentTimeMillis() + 200000;
		Tour tour = new Tour(1, "Tour", gpsMeeting, currentTime, 60, part1);
		tour.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		dao.setUserId(2);
		dao.setGps(gpsUser2);
		dao.userSetGPS();
		dao.setUserId(3);
		dao.setGps(gpsUser3);
		dao.userSetGPS();
		setGpsTour(tour);
		assertNull(user1.getGps());
		assertNotNull(user2.getGps());
		assertNotNull(user3.getGps());
	}

	@Test
	public void testSetGpsTourParticipantNotConfirmed() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsUser2 = new GPS(24, 22, 1);
		GPS gpsUser3 = new GPS(25, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		User user3 = new User(3, "user3");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.REJECTED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		Participant part3 = new Participant(3, 1, user3, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		participants.add(part3);
		long currentTime = System.currentTimeMillis() + 200000;
		Tour tour = new Tour(1, "Tour", gpsMeeting, currentTime, 60, part1);
		tour.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		dao.setUserId(2);
		dao.setGps(gpsUser2);
		dao.userSetGPS();
		dao.setUserId(3);
		dao.setGps(gpsUser3);
		dao.userSetGPS();
		setGpsTour(tour);
		assertNull(user1.getGps());
		assertNotNull(user2.getGps());
		assertNotNull(user3.getGps());
	}

	@Test
	public void testSetGpsTourParticipantIsNotRunning() {
		GPS gpsUser1 = new GPS(500, 22, 1);
		GPS gpsUser2 = new GPS(24, 22, 1);
		GPS gpsUser3 = new GPS(25, 22, 1);
		GPS gpsMeeting = new GPS(23, 23, 1);
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		User user3 = new User(3, "user3");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.REJECTED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		Participant part3 = new Participant(3, 1, user3, MeetingConfirmation.CONFIRMED);
		List<Participant> participants = new ArrayList<>();
		participants.add(part1);
		participants.add(part2);
		participants.add(part3);
		long currentTime = System.currentTimeMillis() + 31 * 60 * 1000;
		Tour tour = new Tour(1, "Tour", gpsMeeting, currentTime, 60, part1);
		tour.setParticipants(participants);
		GpsDaoImpl dao = new GpsDaoImpl();
		dao.setUserId(1);
		dao.setGps(gpsUser1);
		dao.userSetGPS();
		dao.setUserId(2);
		dao.setGps(gpsUser2);
		dao.userSetGPS();
		dao.setUserId(3);
		dao.setGps(gpsUser3);
		dao.userSetGPS();
		setGpsTour(tour);
		assertNull(user1.getGps());
		assertNull(user2.getGps());
		assertNull(user3.getGps());
	}

}
