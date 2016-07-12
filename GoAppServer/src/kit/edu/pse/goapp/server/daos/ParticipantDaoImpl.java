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

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public class ParticipantDaoImpl implements ParticipantDAO {

	private int participantId;
	private int userId;
	private int meetingId;
	private MeetingConfirmation confirmation;
	private List<Integer> participantIds = new ArrayList<>();

	public ParticipantDaoImpl() {

	}

	@Override
	public void addParticipant() throws IOException {
		if (meetingId <= 0) {
			throw new IllegalArgumentException("A participant must have an MeetingID!");
		}
		try (DatabaseConnection conn = new DatabaseConnection()) {
			String query = MessageFormat.format("INSERT INTO participants (users_id, meetings_id, confirmation) VALUES"
					+ " (''{0}'', ''{1}'', ''{2}'')", userId, meetingId, confirmation);
			participantId = conn.insert(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteParticipant() throws IOException {
		if (participantId <= 0) {
			throw new IllegalArgumentException("A participant must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("DELETE FROM participants WHERE participants_id = ''{0}''",
					participantId);
			connection.delete(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<Participant> getAllParticipants() throws IOException, CustomServerException {
		List<Participant> participants = new ArrayList<>();
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("SELECT participants.participants_id FROM participants  "
					+ " WHERE participants.meetings_id = ''{0}''", meetingId);
			connection.select(query, new ParticipantsSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException();
		}
		for (int tmpParticipantId : participantIds) {
			ParticipantDAO dao = new ParticipantDaoImpl();
			dao.setParticipantId(tmpParticipantId);
			Participant participant = dao.getParticipantByID();
			participants.add(participant);
		}
		return participants;
	}

	@Override
	public Participant getParticipantByID() throws IOException, CustomServerException {
		if (participantId <= 0) {
			throw new IllegalArgumentException("A participant must have an ID!");
		}
		try (DatabaseConnection connection = new DatabaseConnection()) {
			String query = MessageFormat.format("SELECT participants_id, users_id, meetings_id, confirmation "
					+ "FROM participants " + "WHERE participants_id = ''{0}''", participantId);
			connection.select(query, new ParticipantSqlSelectionHandler());
		} catch (Throwable e) {
			throw new IOException(e);
		}
		UserDAO dao = new UserDaoImpl();
		dao.setUserId(userId);
		User user = dao.getUserByID();
		Participant participant = new Participant(participantId, meetingId, user, confirmation);
		return participant;
	}

	@Override
	public void updateParticipant() throws IOException {
		if (participantId <= 0) {
			throw new IllegalArgumentException("A participant must have an ID!");
		}
		try (DatabaseConnection connetion = new DatabaseConnection()) {
			String query = MessageFormat.format(
					"UPDATE participants SET confirmation = ''{0}''" + " WHERE participants_id = ''{1}''", confirmation,
					participantId);
			connetion.update(query);
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public int getParticipantId() {
		return participantId;
	}

	@Override
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
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
	public MeetingConfirmation getConfirmation() {
		return confirmation;
	}

	@Override
	public void setConfirmation(MeetingConfirmation confirmation) {
		this.confirmation = confirmation;
	}

	private final class ParticipantSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				if (userId <= 0) {
					userId = resultSet.getInt(2);
				}
				if (meetingId <= 0) {
					meetingId = resultSet.getInt(3);
				}
				confirmation = MeetingConfirmation.valueOf(resultSet.getString(4).toUpperCase());
			}
		}
	}

	private final class ParticipantsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				participantIds.add(resultSet.getInt(1));

			}
		}

	}

}
