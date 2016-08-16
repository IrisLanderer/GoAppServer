/**
 * 
 */
package kit.edu.pse.goapp.server.creating_obj_with_dao;

import java.io.IOException;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class MeetingWithDao {

	/**
	 * Creates a meeting with DAO
	 * 
	 * @param meetingId
	 *            meetingId
	 * @return meeting meeting
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public Meeting createMeetingWithDao(int meetingId) throws IOException, CustomServerException {
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingId);
		Meeting meeting = dao.getMeetingByID();
		return meeting;
	}

}
