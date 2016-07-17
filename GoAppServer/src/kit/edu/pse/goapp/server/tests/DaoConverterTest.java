/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import kit.edu.pse.goapp.server.converter.daos.GpsDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.GroupDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.GroupMemberDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.ParticipantDaoConverter;
import kit.edu.pse.goapp.server.converter.daos.UserDaoConverter;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
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
import kit.edu.pse.goapp.server.datamodels.GPS;
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
	public void testNullForGroupDaoConverter() throws CustomServerException {
		String jsonString = null;
		GroupDaoConverter converter = new GroupDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
	}

	@Test
	public void testNullForUserDaoConverter() throws CustomServerException {
		String jsonString = null;
		UserDaoConverter converter = new UserDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
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
	public void testNullForParticipantDaoConverter() throws CustomServerException {
		String jsonString = null;
		ParticipantDaoConverter converter = new ParticipantDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
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
	public void testNullForGroupMemberDaoConverter() throws CustomServerException {
		String jsonString = null;
		GroupMemberDaoConverter converter = new GroupMemberDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
	}

	@Test
	public void testJsonToMeetingDao() throws CustomServerException {
		String jsonString = "{\"type\":\"Tour\",\"center\":{\"participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},"
				+ "\"meetingId\":1,\"name\":\"test\",\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0},\"timespamp\":\"15\",\"duration\":3,"
				+ "\"creator\":{\"participantId\":14,\"meetingId\":13,"
				+ "\"user\":{\"userId\":1,\"name\":\"Iris\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":\"REJECTED\"},"
				+ "\"participants\":[]}";
		MeetingDaoConverter converter = new MeetingDaoConverter();
		MeetingDAO dao = converter.parse(jsonString);
		MeetingDAO expectedDao = new MeetingDaoImpl();
		expectedDao.setUserId(1);
		expectedDao.setMeetingId(1);
		expectedDao.setName("test");
		expectedDao.setPlaceX(1.0);
		expectedDao.setPlaceY(2.0);
		expectedDao.setPlaceZ(3.0);
		expectedDao.setTimestamp(15);
		expectedDao.setDuration(3);
		expectedDao.setType("Tour");
		assertEquals(expectedDao.getUserId(), dao.getUserId());
		assertEquals(expectedDao.getMeetingId(), dao.getMeetingId());
		assertEquals(expectedDao.getName(), dao.getName());
		assertEquals(expectedDao.getPlaceX(), dao.getPlaceX(), 1.00);
		assertEquals(expectedDao.getPlaceY(), dao.getPlaceY(), 1.00);
		assertEquals(expectedDao.getPlaceZ(), dao.getPlaceZ(), 1.00);
		assertEquals(expectedDao.getTimestamp(), dao.getTimestamp());
		assertEquals(expectedDao.getDuration(), dao.getDuration());
		assertEquals(expectedDao.getType(), dao.getType());
	}

	@Test
	public void testNullForMeetingDaoConverter() throws CustomServerException {
		String jsonString = null;
		MeetingDaoConverter converter = new MeetingDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
	}

	@Test
	public void testJsonToGpsDao() throws CustomServerException {
		String jsonString = "{\"gps\":{\"x\":\"1\",\"y\":\"1\",\"z\":\"1\"}}";
		GpsDaoConverter converter = new GpsDaoConverter();
		GpsDaoImpl dao = (GpsDaoImpl) converter.parse(jsonString);
		GpsDaoImpl expectedDao = new GpsDaoImpl();
		GPS gps = new GPS(1, 1, 1);
		expectedDao.setGps(gps);
		assertEquals(expectedDao.getGps().getX(), dao.getGps().getX(), 3.00);
		assertEquals(expectedDao.getGps().getY(), dao.getGps().getY(), 3.00);
		assertEquals(expectedDao.getGps().getZ(), dao.getGps().getZ(), 3.00);
	}

	@Test
	public void testNullForGpsDaoConverter() throws CustomServerException {
		String jsonString = null;
		GpsDaoConverter converter = new GpsDaoConverter();
		converter.parse(jsonString);
		assertNull(jsonString);
	}

}
