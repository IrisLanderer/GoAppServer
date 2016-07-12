/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface ParticipantDAO {

	public void addParticipant() throws IOException;

	public void deleteParticipant() throws IOException;

	public List<Participant> getAllParticipants() throws IOException, CustomServerException;

	public Participant getParticipantByID() throws IOException, CustomServerException;

	public void updateParticipant() throws IOException;

	void setConfirmation(MeetingConfirmation confirmation);

	MeetingConfirmation getConfirmation();

	void setMeetingId(int meetingId);

	int getMeetingId();

	void setUserId(int userId);

	int getUserId();

	void setParticipantId(int participantId);

	int getParticipantId();

}
