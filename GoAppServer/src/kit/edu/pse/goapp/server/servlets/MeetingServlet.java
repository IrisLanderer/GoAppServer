/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.algorithm.MeetingGpsAlgorithm;
import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Meeting
 */
@WebServlet("/Meeting")
public class MeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingServlet() {
		super();
	}

	/**
	 * GetDetails
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();
			dao.setUserId(userId);
			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = createUserWithDao(userId);
			Meeting meetingCheckParticipant = createMeetingWithDao(dao.getMeetingId(), dao.getUserId());
			meetingCheckParticipant.isParticipant(user);
			if (dao != null) {
				Meeting meeting = dao.getMeetingByID();
				if (meeting instanceof Tour) {
					MeetingGpsAlgorithm.setGpsTour((Tour) meeting);
				} else {
					MeetingGpsAlgorithm.setGpsEvent((Event) meeting);
				}

				String json = new ObjectConverter<Meeting>().serialize(meeting, Meeting.class);
				response.getWriter().write(json);
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Create meeting
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			if (dao != null) {
				dao.addMeeting();
			}
			Meeting meeting = dao.getMeetingByID();

			if (meeting instanceof Tour) {
				MeetingGpsAlgorithm.setGpsTour((Tour) meeting);
			} else {
				MeetingGpsAlgorithm.setGpsEvent((Event) meeting);
			}
			response.getWriter().write(new ObjectConverter<Meeting>().serialize(meeting, Meeting.class));

		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}

	}

	/**
	 * Change meeting
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			checkIfUserIsParticipantAndCreator(userId, dao.getMeetingId());
			if (dao != null) {
				dao.updateMeeting();
			}
			Meeting meeting = dao.getMeetingByID();
			if (meeting instanceof Tour) {
				MeetingGpsAlgorithm.setGpsTour((Tour) meeting);
			} else {
				MeetingGpsAlgorithm.setGpsEvent((Event) meeting);
			}

			response.getWriter().write(new ObjectConverter<Meeting>().serialize(meeting, Meeting.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}

	}

	/**
	 * Delete meeting
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();
			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			checkIfUserIsParticipantAndCreator(userId, dao.getMeetingId());
			if (dao != null) {
				dao.deleteMeeting();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Authenticate user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		CookieManager cm = new CookieManager();

		String userIDString = cm.searchCookie(request, "userId");
		if (userIDString.length() > 0) {
			int userId = Integer.parseInt(userIDString);
			if (userId > 0) {
				return userId;
			}
		}
		throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
	}

	/**
	 * Create user with DAO
	 * 
	 * @param userId
	 *            userId
	 * @return user user
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private User createUserWithDao(int userId) throws IOException, CustomServerException {
		UserDAO userDao = new UserDaoImpl();
		userDao.setUserId(userId);
		User user = userDao.getUserByID();
		return user;
	}

	/**
	 * Create meeting with DAO
	 * 
	 * @param meetingId
	 *            meetingID
	 * @param userId
	 *            userId
	 * @return meeting meeting
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private Meeting createMeetingWithDao(int meetingId, int userId) throws IOException, CustomServerException {
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingId);
		dao.setUserId(userId);
		Meeting meeting = dao.getMeetingByID();
		return meeting;
	}

	/**
	 * Check if user is meeting participant and creator
	 * 
	 * @param userId
	 *            userId
	 * @param meetingId
	 *            meetingId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private void checkIfUserIsParticipantAndCreator(int userId, int meetingId)
			throws IOException, CustomServerException {
		User user = createUserWithDao(userId);
		Meeting meetingCheckParticipant = createMeetingWithDao(meetingId, userId);
		meetingCheckParticipant.isParticipant(user);
		meetingCheckParticipant.isCreator(user);
	}

}
