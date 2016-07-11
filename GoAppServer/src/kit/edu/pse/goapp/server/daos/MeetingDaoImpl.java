/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;

public class MeetingDaoImpl implements MeetingDAO {

	private int meetingId;
	private String name;
	private double placeX;
	private double placeY;
	private double placeZ;
	private long timestamp;
	private int duration;
	private String type;
	private int creatorId;
	private int userId;
	private List<Integer> meetingIds = new ArrayList<>();

	public MeetingDaoImpl() {
		super();
	}

	@Override
	public void addMeeting() throws IOException {
		if (name == null) {
			throw new IllegalArgumentException("A new meeting must have a name!");
		}
		if (placeX < 0) {
			throw new IllegalArgumentException("A new meeting must have a x-coordinate!");
		}
		if (placeY < 0) {
			throw new IllegalArgumentException("A new meeting must have a y-coordinate!");
		}
		if (placeZ < 0) {
			throw new IllegalArgumentException("A new meeting must have a z-coordinate!");
		}
		if (timestamp <= 0) {
			throw new IllegalArgumentException("A new meeting must have a timestamp!");
		}
		if (duration <= 0) {
			throw new IllegalArgumentException("A new meeting must have a duration!");
		}
		if (type == null) {
			throw new IllegalArgumentException("A new meeting must have a Type!");
		}
		if (userId <= 0) {
			throw new IllegalArgumentException("A new meeting must have a creator!");
		}
		creatorId = 0;

		try (DatabaseConnection conn = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"INSERT INTO meetings "
							+ "(name, place_x, place_y, place_z, timestamp, duration, type, creator_id) "
							+ "VALUES (''{0}'', ''{1}'', ''{2}'', ''{3}'', ''{4}'', ''{5}'', ''{6}'', ''{7}'')",
					name, placeX, placeY, placeZ, timestamp, duration, type, creatorId);
			meetingId = conn.insert(query);
			ParticipantDAO dao = new ParticipantDaoImpl();
			dao.setUserId(userId);
			dao.setMeetingId(meetingId);
			dao.setConfirmation(MeetingConfirmation.PENDING);
			dao.addParticipant();
			creatorId = dao.getParticipantId();
			String updateQuery = MessageFormat.format(
					"UPDATE meetings SET creator_id = ''{0}''  WHERE meetings_id = ''{1}''", creatorId, meetingId);
			conn.update(updateQuery);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<Meeting> getAllMeetings() throws IOException {
		List<Meeting> meetings = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"SELECT meetings.meetings_id FROM meetings join participants "
							+ "on meetings.meetings_id = participants.meetings_id where participants.users_id = ''{0}''",
					userId);
			connection.select(query, new MeetingsSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpMeetingId : meetingIds) {
			MeetingDAO dao = new MeetingDaoImpl();
			dao.setMeetingId(tmpMeetingId);
			Meeting meeting = dao.getMeetingByID();
			meetings.add(meeting);
		}
		return meetings;
	}

	@Override
	public void updateMeeting() throws IOException {
		if (meetingId <= 0) {
			throw new IllegalArgumentException("A meeting must have an ID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"UPDATE meetings " + "SET name = ''{0}'', place_x = ''{1}'', place_y = ''{2}'', place_z = ''{3}'',"
							+ " timestamp = ''{4}'', duration = ''{5}'', type = ''{6}'', creator_id = ''{7}''"
							+ " WHERE meetings_id = ''{8}''",
					name, placeX, placeY, placeZ, timestamp, duration, type, creatorId, meetingId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	@Override
	public void deleteMeeting() throws IOException {
		if (meetingId <= 0) {
			throw new IllegalArgumentException("A meeting must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String queryMeeting = MessageFormat.format("DELETE FROM meetings WHERE meetings_id = ''{0}''", meetingId);
			String queryParticipants = MessageFormat.format("DELETE FROM participants WHERE meetings_id = ''{0}''",
					meetingId);
			connection.delete(queryMeeting);
			connection.delete(queryParticipants);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public Meeting getMeetingByID() throws IOException {
		if (meetingId <= 0) {
			throw new IllegalArgumentException("A meeting must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("SELECT * FROM meetings WHERE meetings_id = ''{0}''", meetingId);
			connection.select(query, new MeetingSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException(e);
		}
		GPS gps = new GPS(placeX, placeY, placeZ);
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(creatorId);
		Participant creator = dao.getParticipantByID();

		ParticipantDAO pdao = new ParticipantDaoImpl();
		pdao.setMeetingId(meetingId);
		List<Participant> participants = pdao.getAllParticipants();

		if (type == "Event") {
			Event event = new Event(meetingId, name, gps, timestamp, duration, creator);
			event.setParticipants(participants);
			return event;
		} else {
			Tour tour = new Tour(meetingId, name, gps, timestamp, duration, creator);
			tour.setParticipants(participants);
			return tour;
		}

	}

	@Override
	public int getMeetingId() {
		return meetingId;
	}

	@Override
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPlaceX() {
		return placeX;
	}

	@Override
	public void setPlaceX(double placeX) {
		this.placeX = placeX;
	}

	@Override
	public double getPlaceY() {
		return placeY;
	}

	@Override
	public void setPlaceY(double placeY) {
		this.placeY = placeY;
	}

	@Override
	public double getPlaceZ() {
		return placeZ;
	}

	@Override
	public void setPlaceZ(double placeZ) {
		this.placeZ = placeZ;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getCreatorId() {
		return creatorId;
	}

	@Override
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	private final class MeetingSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {

				meetingId = resultSet.getInt(1);

				name = resultSet.getString(2);

				placeX = resultSet.getDouble(3);

				placeY = resultSet.getDouble(4);

				placeZ = resultSet.getDouble(5);

				timestamp = resultSet.getLong(6);

				duration = resultSet.getInt(7);

				type = resultSet.getString(8);

				creatorId = resultSet.getInt(9);

			}
		}
	}

	private final class MeetingsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				meetingIds.add(resultSet.getInt(1));

			}
		}

	}

}
