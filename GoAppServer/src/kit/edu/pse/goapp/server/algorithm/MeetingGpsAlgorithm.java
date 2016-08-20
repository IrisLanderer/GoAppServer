/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.algorithm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingCenter;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;

/**
 * Implements Algorithm for GPS Position for Meetings.
 */
public class MeetingGpsAlgorithm {

	/**
	 * Sets GPS Data of participants for a Tour with various places
	 * 
	 * @param tour
	 *            Tour
	 * @return tour Tour
	 */
	public static Tour setGpsTour(Tour tour) {
		if (!isRunning(tour)) {
			return tour;
		}

		List<Participant> participants = new ArrayList<>();
		for (Participant p : tour.getParticipants()) {
			if (p.getConfirmation() == MeetingConfirmation.CONFIRMED) {
				participants.add(p);
			}
		}

		List<Participant> biggestBlob = new ArrayList<>();
		for (Participant p : participants) {
			List<Participant> smallBlob = new ArrayList<>();
			smallBlob.add(p);
			boolean abort = false;
			while (!abort) {
				abort = true;
				for (Participant inner : participants) {
					if (!smallBlob.contains(inner)) {
						if (isInBlob(smallBlob, inner)) {
							smallBlob.add(inner);
							abort = false;
						}
					}
				}
			}
			if (smallBlob.size() > biggestBlob.size()) {
				biggestBlob = new ArrayList<>(smallBlob);
			}
		}
		List<GPS> positions = new ArrayList<>();
		for (Participant p : biggestBlob) {
			GpsDaoImpl dao = new GpsDaoImpl();
			dao.setUserId(p.getUser().getId());
			GPS gpsP = dao.userGetGPS();
			positions.add(gpsP);
			p.getUser().setGPS(gpsP);
		}

		GPS median = GPS.median(positions);
		MeetingCenter center = new MeetingCenter(median);
		center.setParticipants(biggestBlob);
		return tour;
	}

	/**
	 * Shows if a participant is in blob
	 * 
	 * @param blob
	 *            List of participants of a meeting
	 * @param test
	 *            test-participant
	 * @return boolean true if participant is in Blob, else false
	 */
	private static boolean isInBlob(List<Participant> blob, Participant test) {
		for (Participant p : blob) {
			GpsDaoImpl dao = new GpsDaoImpl();
			dao.setUserId(p.getUser().getId());
			GPS gpsP = dao.userGetGPS();
			dao.setUserId(test.getUser().getId());
			GPS gpsTest = dao.userGetGPS();
			if (GPS.isClose(gpsP, gpsTest, Tour.MAX_CENTER_DISTANCE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * * Sets GPS Data of participants for an Event with a static place
	 * 
	 * 
	 * @param event
	 * @return
	 */
	public static Event setGpsEvent(Event event) {
		if (!isRunning(event)) {
			return event;
		}

		for (Participant p : event.getParticipants()) {
			if (p.getConfirmation() == MeetingConfirmation.CONFIRMED) {
				GpsDaoImpl dao = new GpsDaoImpl();
				dao.setUserId(p.getUser().getId());
				GPS gps = dao.userGetGPS();
				if (GPS.isClose(gps, event.getPlace(), Event.RADIUS)) {
					p.getUser().setGPS(gps);
				}
			}
		}
		return event;
	}

	/**
	 * Shows if the Event is already running
	 * 
	 * @param meeting
	 *            Meeting
	 * @return boolean true if event is running, else false
	 */
	private static boolean isRunning(Meeting meeting) {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		long halfHour = 30 * 60 * 1000;
		long duration = meeting.getDuration() * 60 * 1000;
		if ((stamp.getTime() >= (meeting.getTimestamp() * 1000) - halfHour)
				&& (stamp.getTime() <= (meeting.getTimestamp() * 1000) + duration)) {
			return true;
		}

		return false;
	}

	/**
	 * Shows if the Event is already running
	 * 
	 * @param meeting
	 *            Meeting
	 * @return boolean true if event is running, else false
	 */
	public static boolean isOver(Meeting meeting) {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());

		long duration = meeting.getDuration() * 60 * 1000;
		if ((stamp.getTime() > (meeting.getTimestamp() * 1000) + duration)) {
			return true;
		}

		return false;
	}

}
