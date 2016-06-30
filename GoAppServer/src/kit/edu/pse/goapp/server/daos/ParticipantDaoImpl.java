package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;

public class ParticipantDaoImpl implements ParticipantDAO {
	
	private int participantId;
	private int userId;
	private int meetingId;
	private MeetingConfirmation confirmation;
	
	
	public ParticipantDaoImpl(int participantId, int userId, int meetingId, MeetingConfirmation confirmation) {
		
		this.participantId = participantId;
		this.userId = userId;
		this.meetingId = meetingId;
		this.confirmation = confirmation;
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

}
