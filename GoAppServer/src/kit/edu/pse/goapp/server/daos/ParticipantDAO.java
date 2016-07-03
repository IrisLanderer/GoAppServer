package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;

public interface ParticipantDAO {
	
	public void addParticipant();
	public void deleteParticipant();
	public List<Participant> getAllParticipants();
	public Participant getParticipantByID();
	public void updateParticipant();
	void setConfirmation(MeetingConfirmation confirmation);
	MeetingConfirmation getConfirmation();
	void setMeetingId(int meetingId);
	int getMeetingId();
	void setUserId(int userId);
	int getUserId();
	void setParticipantId(int participantId);
	int getParticipantId();

}
