/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kit.edu.pse.goapp.server.converter.daos.GroupDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.GroupMemberDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.ParticipantDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.UserDaoConverter;
import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * @author Iris
 *
 */
public class DaoConverterTest {

	@Test
	public void testJsonToGroupDao() throws CustomServerException {
		String jsonString = "{\"groupId\":\"1\",\"name\":\"test\"}";
		GroupDaoConverter converter = new GroupDaoConverter();
		GroupDAO dao = converter.parse(jsonString);
		GroupDAO expectedDao = new GroupDaoImpl();
		expectedDao.setGroupId(1);
		expectedDao.setName("test");
		assertEquals(expectedDao.getGroupId(), dao.getGroupId());
		assertEquals(expectedDao.getName(), dao.getName());
	}

	@Test
	public void testJsonToUserDao() throws CustomServerException {
		String jsonString = "{\"userId\":\"1\",\"name\":\"testName\"}";
		UserDaoConverter converter = new UserDaoConverter();
		UserDAO dao = converter.parse(jsonString);
		UserDAO expectedDao = new UserDaoImpl();
		expectedDao.setUserId(1);
		expectedDao.setName("testName");
		assertEquals(expectedDao.getUserId(), dao.getUserId());
		assertEquals(expectedDao.getName(), dao.getName());
	}

	@Test
	public void testJsonToParticipantDao() throws CustomServerException {
		String jsonString = "{\"meetingId\" : 2 , \"participantId\":1,"
				+ "\"user\":{\"userId\":1,\"name\":\"Iris\",\"meetings\":[],\"groups\":[]},\"confirmation\":\"PENDING\"}";
		ParticipantDaoConverter converter = new ParticipantDaoConverter();
		ParticipantDAO dao = converter.parse(jsonString);
		ParticipantDAO expectedDao = new ParticipantDaoImpl();
		expectedDao.setParticipantId(1);
		expectedDao.setUserId(1);
		expectedDao.setMeetingId(2);
		expectedDao.setConfirmation(MeetingConfirmation.PENDING);
		assertEquals(expectedDao.getParticipantId(), dao.getParticipantId());
		assertEquals(expectedDao.getUserId(), dao.getUserId());
		assertEquals(expectedDao.getMeetingId(), dao.getMeetingId());
		assertEquals(expectedDao.getConfirmation(), dao.getConfirmation());
	}

	@Test
	public void testJsonToGroupMemberDao() throws CustomServerException {
		String jsonString = "{\"groupId\":\"1\", \"userId\":\"1\",\"isAdmin\":\"true\"}";
		GroupMemberDaoConverter converter = new GroupMemberDaoConverter();
		GroupMemberDAO dao = converter.parse(jsonString);
		GroupMemberDAO expectedDao = new GroupMemberDaoImpl();
		expectedDao.setGroupId(1);
		expectedDao.setUserId(1);
		expectedDao.setAdmin(true);
		assertEquals(expectedDao.getGroupId(), dao.getGroupId());
		assertEquals(expectedDao.getUserId(), dao.getUserId());
		assertEquals(expectedDao.isAdmin(), dao.isAdmin());
	}

	@Test
	public void testJsonToMeetingDao() throws CustomServerException {
		String jsonString = "{\"type\":\"Tour\",\"center\":{\"participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},"
				+ "\"meetingId\":13,\"name\":\"test\",\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0},\"timespamp\":15,\"duration\":3,"
				+ "\"creator\":{\"participantId\":14,\"meetingId\":13,"
				+ "\"user\":{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":\"REJECTED\"},"
				+ "\"participants\":[]}";
		MeetingDaoConverter converter = new MeetingDaoConverter();
		MeetingDAO dao = converter.parse(jsonString);
		MeetingDAO expectedDao = new MeetingDaoImpl();
		// expectedDao.se
		// expectedDao.setGroupId(1);
		// expectedDao.setUserId(1);
		// expectedDao.setAdmin(true);
		// assertEquals(expectedDao.getGroupId(), dao.getGroupId());
		// assertEquals(expectedDao.getUserId(), dao.getUserId());
		// assertEquals(expectedDao.isAdmin(), dao.isAdmin());
	}

}
