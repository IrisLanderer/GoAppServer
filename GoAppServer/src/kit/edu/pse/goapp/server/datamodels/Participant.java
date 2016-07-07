package kit.edu.pse.goapp.server.datamodels;

public class Participant {
	private int participantId;
	private int meetingId;
	private User user;
	private MeetingConfirmation confirmation;

	public Participant(int participantId, int meetingId, User user, MeetingConfirmation confirmation) {
		this.participantId = participantId;
		this.meetingId = meetingId;
		this.user = user;
		this.confirmation = confirmation;
	}

	public int getParticipantId() {
		return participantId;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public User getUser() {
		return user;
	}

	public MeetingConfirmation getConfirmation() {
		return confirmation;
	}

}
