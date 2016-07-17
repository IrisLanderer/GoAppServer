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
		try {
			this.addParticipant(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void addParticipant(DatabaseConnection conn) throws IOException, CustomServerException {
		if (meetingId <= 0) {
			throw new CustomServerException("A participant must have an MeetingID!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A participant must have an UserID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format("INSERT INTO participants (users_id, meetings_id, confirmation) VALUES"
					+ " (''{0}'', ''{1}'', ''{2}'')", userId, meetingId, confirmation);
			participantId = conn.insert(query);
			if (participantId <= 0) {
				throw new CustomServerException("The ParticipantID wasn't assigned to a value!",
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			conn.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public void deleteParticipant() throws IOException {
		try {
			this.deleteParticipant(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void deleteParticipant(DatabaseConnection connection) throws IOException, CustomServerException {
		if (participantId <= 0) {
			throw new CustomServerException("A participant must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format("DELETE FROM participants WHERE participants_id = ''{0}''",
					participantId);
			connection.delete(query);
			connection.close();
		} catch (Throwable e) {
			throw new IOException(e);
		}

	}

	@Override
	public List<Participant> getAllParticipants() throws IOException {
		List<Participant> participants = null;
		try {
			participants = getAllParticipants(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return participants;
	}

	protected List<Participant> getAllParticipants(DatabaseConnection connection) throws Exception {
		if (meetingId <= 0) {
			throw new CustomServerException("The MeetingId is necessary to get all participants!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		List<Participant> participants = new ArrayList<>();
		try {
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
		connection.close();
		return participants;
	}

	@Override
	public Participant getParticipantByID() throws IOException {
		Participant participant = null;
		try {
			participant = getParticipantByID(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
		return participant;
	}

	protected Participant getParticipantByID(DatabaseConnection connection) throws Exception {
		if (participantId <= 0) {
			throw new CustomServerException("A participant must have an ID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format("SELECT participants_id, users_id, meetings_id, confirmation "
					+ "FROM participants " + "WHERE participants_id = ''{0}''", participantId);
			connection.select(query, new ParticipantSqlSelectionHandler());
		} catch (Throwable e) {
			if (e.getCause().getClass() == CustomServerException.class) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			throw new IOException(e);
		}
		UserDAO dao = new UserDaoImpl();
		dao.setUserId(userId);
		User user = dao.getUserByID();
		Participant participant = new Participant(participantId, meetingId, user, confirmation);
		connection.close();
		return participant;
	}

	@Override
	public void updateParticipant() throws IOException {
		try {
			this.updateParticipant(new DatabaseConnection());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void updateParticipant(DatabaseConnection connection) throws IOException, CustomServerException {
		if (participantId <= 0) {
			throw new CustomServerException("A participant must have a ParticipantID!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (userId <= 0) {
			throw new CustomServerException("A participant must have an UserID!", HttpServletResponse.SC_BAD_REQUEST);
		}
		if (meetingId <= 0) {
			throw new CustomServerException("A participant must have an MeetingID!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		if (confirmation == null) {
			throw new CustomServerException("A participant must give a cofirmation status of a meeting!",
					HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			String query = MessageFormat.format(
					"UPDATE participants SET confirmation = ''{0}''" + " WHERE participants_id = ''{1}''", confirmation,
					participantId);
			connection.update(query);
			connection.close();
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

	// private List<User> createParticipantsWithDao() throws IOException,
	// CustomServerException {
	// GroupDAO dao = new GroupDaoImpl();
	// dao.setGroupId(groupId);
	// Group group = dao.getGroupByID();
	// List<User> groupMembers = group.getGroupMembers();
	// return groupMembers;
	// }
	//
	// private void checkIfParticipant(List<User> groupMembers) throws
	// CustomServerException {
	// boolean isMember = false;
	// for (User member : groupMembers) {
	// if (member.getId() == userId) {
	// isMember = true;
	// }
	// }
	// if (!isMember) {
	// throw new CustomServerException("The user is not a member of this
	// group!",
	// HttpServletResponse.SC_BAD_REQUEST);
	// }
	// }

	private final class ParticipantSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {
			boolean resultEmpty = true;
			while (resultSet.next()) {
				resultEmpty = false;
				userId = resultSet.getInt(2);
				meetingId = resultSet.getInt(3);
				confirmation = MeetingConfirmation.valueOf(resultSet.getString(4).toUpperCase());
			}
			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

	private final class ParticipantsSqlSelectionHandler implements SqlSelectHandler {
		@Override
		public void handleResultSet(ResultSet resultSet) throws SQLException, CustomServerException {
			boolean resultEmpty = true;
			while (resultSet.next()) {
				resultEmpty = false;
				participantIds.add(resultSet.getInt(1));
			}
			if (resultEmpty) {
				throw new CustomServerException("The selected resultset from the database is empty",
						HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}

}
