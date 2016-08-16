/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Implements interface MeetingDAO
 */
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

	/**
	 * Constructor
	 */
	public MeetingDaoImpl() {
		super();
	}

	/**
	 * Add new meeting
	 */
	@Override
	public void addMeeting() throws IOException {
		try {
			this.addMeeting(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Add new meeting
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void addMeeting(DatabaseConnection connection) throws IOException, CustomServerException {
		if (name == null || name.equals("")) {
			throw new CustomServerException("A new meeting must have a name!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeX <= 0) {
			throw new CustomServerException("A new meeting must have a x-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeY <= 0) {
			throw new CustomServerException("A new meeting must have a y-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeZ < 0) {
			throw new CustomServerException("A new meeting must have a z-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (timestamp <= 0) {
			throw new CustomServerException("A new meeting must have a timestamp!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (duration <= 0) {
			throw new CustomServerException("A new meeting must have a duration!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if ((type == null) || !(type.equals("Tour") || type.equals("Event"))) {
			throw new CustomServerException("A new meeting must have a Type!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A new meeting must have a creator!", HttpServletResponse.SC_BAD_REQUEST);
		}
		creatorId = 0;
		try {
			String query = MessageFormat.format(
					"INSERT INTO meetings "
							+ "(name, place_x, place_y, place_z, timestamp, duration, type, creator_id) "
							+ "VALUES (''{0}'', ''{1}'', ''{2}'', ''{3}'', ''{4}'', ''{5}'', ''{6}'', ''{7}'')",
					name, placeX, placeY, placeZ, Long.toString(timestamp), duration, type, creatorId);
			meetingId = connection.insert(query);
			if (meetingId <= 0) {
				throw new CustomServerException("The MeetingID wasn't assigned to a value!",
						HttpServletResponse.SC_NO_CONTENT);
			}
			ParticipantDAO dao = new ParticipantDaoImpl();
			dao.setUserId(userId);
			dao.setMeetingId(meetingId);
			dao.setConfirmation(MeetingConfirmation.PENDING);
			dao.addParticipant();
			creatorId = dao.getParticipantId();
			String updateQuery = MessageFormat.format(
					"UPDATE meetings SET creator_id = ''{0}''  WHERE meetings_id = ''{1}''", creatorId, meetingId);
			connection.update(updateQuery);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	/**
	 * Returns a list of all meetings
	 */
	@Override
	public List<Meeting> getAllMeetings() throws IOException {
		List<Meeting> meetings = null;
		try {
			meetings = getAllMeetings(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return meetings;
	}

	/**
	 * Returns a list of all meetings
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @return meetings list of all meetings
	 * @throws Exception
	 *             Exception
	 */
	protected List<Meeting> getAllMeetings(DatabaseConnection connection) throws Exception {
		List<Meeting> meetings = new ArrayList<>();
		try {
			String query = MessageFormat.format("SELECT meetings.meetings_id FROM meetings join participants "
					+ "on meetings.meetings_id = participants.meetings_id where participants.users_id = ''{0}''"
					+ "order by meetings.timestamp asc", userId);
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
		connection.close();
		return meetings;
	}

	/**
	 * Updates meeting information
	 */
	@Override
	public void updateMeeting() throws IOException {
		try {
			this.updateMeeting(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * updates meeting information
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void updateMeeting(DatabaseConnection connection) throws IOException, CustomServerException {
		if (meetingId <= 0) {
			throw new CustomServerException("A meeting must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (name == null || name.equals("")) {
			throw new CustomServerException("A new meeting must have a name!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeX <= 0) {
			throw new CustomServerException("A new meeting must have a x-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeY <= 0) {
			throw new CustomServerException("A new meeting must have a y-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (placeZ <= 0) {
			throw new CustomServerException("A new meeting must have a z-coordinate!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (timestamp <= 0) {
			throw new CustomServerException("A new meeting must have a timestamp!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (duration <= 0) {
			throw new CustomServerException("A new meeting must have a duration!", HttpServletResponse.SC_BAD_REQUEST);
		}

		if ((type == null) || !(type.equals("Tour") || type.equals("Event"))) {
			throw new CustomServerException("A new meeting must have a Type!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (creatorId <= 0) {
			throw new CustomServerException("A new meeting must have a creator!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"UPDATE meetings " + "SET name = ''{0}'', place_x = ''{1}'', place_y = ''{2}'', place_z = ''{3}'',"
							+ " timestamp = ''{4}'', duration = ''{5}'', type = ''{6}'', creator_id = ''{7}''"
							+ " WHERE meetings_id = ''{8}''",
					name, placeX, placeY, placeZ, timestamp, duration, type, creatorId, meetingId);
			connection.update(query);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	/**
	 * Deletes meeting
	 */
	@Override
	public void deleteMeeting() throws IOException {
		try {
			this.deleteMeeting(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Deletes meeting
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	protected void deleteMeeting(DatabaseConnection connection) throws IOException, CustomServerException {
		if (meetingId <= 0) {
			throw new CustomServerException("A meeting must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String queryMeeting = MessageFormat.format("DELETE FROM meetings WHERE meetings_id = ''{0}''", meetingId);
			String queryParticipants = MessageFormat.format("DELETE FROM participants WHERE meetings_id = ''{0}''",
					meetingId);
			connection.delete(queryMeeting);
			connection.delete(queryParticipants);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	/**
	 * Returns a meeting by its id
	 */
	@Override
	public Meeting getMeetingByID() throws IOException {
		Meeting meeting = null;
		try {
			meeting = getMeetingByID(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return meeting;
	}

	/**
	 * Returns a list by its id
	 * 
	 * @param connection
	 *            DatabaseConnection
	 * @return meeting meeting by its id
	 * @throws Exception
	 *             Exception
	 */
	protected Meeting getMeetingByID(DatabaseConnection connection) throws Exception {
		if (meetingId <= 0) {
			throw new CustomServerException("A meeting must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format("SELECT * FROM meetings WHERE meetings_id = ''{0}''", meetingId);
			connection.select(query, new MeetingSqlSelectionHandler());
		} catch (Throwable e) {
			if (e.getCause().getClass() == CustomServerException.class) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			throw new IOException(e);
		}
		GPS gps = new GPS(placeX, placeY, placeZ);
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(creatorId);
		dao.setUserId(userId);
		Participant creator = dao.getParticipantByID();

		ParticipantDAO pdao = new ParticipantDaoImpl();
		pdao.setMeetingId(meetingId);
		List<Participant> participants = pdao.getAllParticipants();

		if (type.equals("Event")) {
			Event event = new Event(meetingId, name, gps, timestamp, duration, creator);
			event.setParticipants(participants);
			connection.close();
			return event;
		} else {
			Tour tour = new Tour(meetingId, name, gps, timestamp, duration, creator);
			tour.setParticipants(participants);
			connection.close();
			return tour;
		}

	}

	/**
	 * Returns meetingId
	 */
	@Override
	public int getMeetingId() {
		return meetingId;
	}

	/**
	 * Sets meetingId
	 */
	@Override
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * Returns name of the meeting
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Set name of the meeting
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns GPS x position
	 */
	@Override
	public double getPlaceX() {
		return placeX;
	}

	/**
	 * Set GPS x position
	 */
	@Override
	public void setPlaceX(double placeX) {
		this.placeX = placeX;
	}

	/**
	 * Return GPS y position
	 */
	@Override
	public double getPlaceY() {
		return placeY;
	}

	/**
	 * Set GPS y position
	 */
	@Override
	public void setPlaceY(double placeY) {
		this.placeY = placeY;
	}

	/**
	 * Return GPS z position
	 */
	@Override
	public double getPlaceZ() {
		return placeZ;
	}

	/**
	 * Set GPS z position
	 */
	@Override
	public void setPlaceZ(double placeZ) {
		this.placeZ = placeZ;
	}

	/**
	 * return timestamp of the meetig
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Set timestamp of the meeting
	 */
	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Return duraion of the meeting
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * Set duration of the meeting
	 */
	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Return type of meeting
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Set type of meeting
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Return creatorId
	 */
	@Override
	public int getCreatorId() {
		return creatorId;
	}

	/**
	 * Set creatorId
	 */
	@Override
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * Return userId
	 */
	@Override
	public int getUserId() {
		return userId;
	}

	/**
	 * Set userId
	 */
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * handles the data from the database's result set for the method
	 * getMeetingsById
	 */
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

	/**
	 * handles the data from the database's result set for the method
	 * getAllMeetings
	 */
	private final class MeetingsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				meetingIds.add(resultSet.getInt(1));

			}
		}

	}

}
