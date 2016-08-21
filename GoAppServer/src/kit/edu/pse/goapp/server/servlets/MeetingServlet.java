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
import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.creating_obj_with_dao.MeetingWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.MeetingValidation;

/**
 * Servlet implementation class Meeting
 */
@WebServlet("/Meeting")
public class MeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserWithDao userWithDao = new UserWithDao();
	private MeetingWithDao meetingWithDao = new MeetingWithDao();

	private MeetingValidation validation = new MeetingValidation();

	private Authentication authentication = new Authentication();

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response, authentication);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();
			dao.setUserId(userId);
			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			User user = userWithDao.createUserWithDao(userId);
			Meeting meetingCheckParticipant = meetingWithDao.createMeetingWithDao(dao.getMeetingId(), dao.getUserId());
			meetingCheckParticipant.isParticipant(user);
			Meeting meeting = dao.getMeetingByID();
			if (meeting instanceof Tour) {
				MeetingGpsAlgorithm.setGpsTour((Tour) meeting);
			} else {
				MeetingGpsAlgorithm.setGpsEvent((Event) meeting);
			}
			String json = new ObjectConverter<Meeting>().serialize(meeting, Meeting.class);
			response.getWriter().write(json);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Create meeting
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response, authentication);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			dao.addMeeting();
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
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Change meeting
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPut(request, response, authentication);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {

		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			validation.checkIfUserIsParticipantAndCreator(userId, dao.getMeetingId());
			dao.updateMeeting();
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
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int userId = authentication.authenticateUser(request);
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();
			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			validation.checkIfUserIsParticipantAndCreator(userId, dao.getMeetingId());
			if (dao != null) {
				dao.deleteMeeting();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
