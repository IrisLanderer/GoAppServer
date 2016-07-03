package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;

public class ParticipantDaoImpl implements ParticipantDAO {
	
	private int participantId;
	private int userId;
	private int meetingId;
	private MeetingConfirmation confirmation;
	
	
	public ParticipantDaoImpl() {
		
	}

	@Override
	public void addParticipant() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteParticipant() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Participant> getAllParticipants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Participant getParticipantByID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateParticipant() {
		// TODO Auto-generated method stub

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
	
	

}
