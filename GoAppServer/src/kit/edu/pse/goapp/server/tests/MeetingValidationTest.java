/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.MeetingValidation;

/**
 * @author Iris
 *
 */
public class MeetingValidationTest extends MeetingValidation {

	@Test(expected = CustomServerException.class)
	public void isAlreadyParticipantTest() throws CustomServerException {
		List<Participant> participants = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		participants.add(part1);
		participants.add(part2);
		MeetingValidationTest validation = new MeetingValidationTest();
		validation.checkIfAlreadyParticipant(participants, 1);
	}

	@Test
	public void isNotAlreadyParticipantTest() throws CustomServerException {
		List<Participant> participants = new ArrayList<>();
		User user1 = new User(1, "user1");
		User user2 = new User(2, "user2");
		Participant part1 = new Participant(1, 1, user1, MeetingConfirmation.CONFIRMED);
		Participant part2 = new Participant(2, 1, user2, MeetingConfirmation.CONFIRMED);
		participants.add(part1);
		participants.add(part2);
		MeetingValidationTest validation = new MeetingValidationTest();
		validation.checkIfAlreadyParticipant(participants, 3);
	}
}
