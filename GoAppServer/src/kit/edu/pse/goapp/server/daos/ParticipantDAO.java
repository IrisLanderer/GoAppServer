package kit.edu.pse.goapp.server.daos;

import java.util.List;

import kit.edu.pse.goapp.server.datamodels.Participant;

public interface ParticipantDAO {
	
	public void addParticipant();
	public void deleteParticipant();
	public List<Participant> getAllParticipants();
	public Participant getParticipantByID();
	public void updateParticipant();

}
