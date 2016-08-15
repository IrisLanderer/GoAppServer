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
import javax.servlet.http.HttpSession;

import kit.edu.pse.goapp.server.converter.daos.ParticipantDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class MeetingParticipantManagement
 */
@WebServlet("/MeetingParticipantManagement")
public class MeetingParticipantManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingParticipantManagementServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets meeting participants
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String participantId = request.getParameter("participantId");
			ParticipantDAO dao = new ParticipantDaoImpl();
			try {
				dao.setParticipantId(Integer.parseInt(participantId));
			} catch (Exception e) {
				throw new CustomServerException("The ParticipantID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);

			}
			Participant participantForMeetingId = createParticipantWithDao(dao.getParticipantId());
			User user = createUserWithDao(userId);
			Meeting meetingCheckParticipant = createMeetingWithDao(participantForMeetingId.getMeetingId());
			meetingCheckParticipant.isParticipant(user);
			if (dao != null) {
				Participant participant = dao.getParticipantByID();
				response.getWriter()
						.write(new ObjectConverter<Participant>().serialize(participant, Participant.class));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Adds meeting participants
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
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			dao.setConfirmation(MeetingConfirmation.PENDING);
			Meeting meetingCheckingForAuthorization = createMeetingWithDao(dao.getMeetingId());
			User user = createUserWithDao(userId);
			meetingCheckingForAuthorization.isParticipant(user);
			meetingCheckingForAuthorization.isCreator(user);
			if (dao != null) {
				dao.addParticipant();
			}
			MeetingDAO meetingDao = new MeetingDaoImpl();
			meetingDao.setMeetingId(dao.getMeetingId());
			Meeting meeting = meetingDao.getMeetingByID();
			response.getWriter().write(new ObjectConverter<Meeting>().serialize(meeting, Meeting.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Confirm Meeting
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			checkIfUserIsParticipantAndCreator(userId, dao.getParticipantId());
			if (dao != null) {
				dao.updateParticipant();
			}
			MeetingDAO meetingDao = new MeetingDaoImpl();
			meetingDao.setMeetingId(dao.getMeetingId());
			Meeting meeting = meetingDao.getMeetingByID();
			response.getWriter().write(new ObjectConverter<Meeting>().serialize(meeting, Meeting.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Deletes a meeting participant
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String participantId = request.getParameter("participantId");
			ParticipantDAO dao = new ParticipantDaoImpl();
			try {
				dao.setParticipantId(Integer.parseInt(participantId));
			} catch (Exception e) {
				throw new CustomServerException("The ParticipantID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			checkIfUserIsParticipantAndCreator(userId, dao.getParticipantId());
			if (dao != null) {
				dao.deleteParticipant();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Authenticates user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession();

		// int userId = 1;// (int) session.getAttribute("userId");
		int userId = (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

	/**
	 * Creates a meeting participant with DAO
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
	private Meeting createMeetingWithDao(int meetingId) throws IOException, CustomServerException {
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(meetingId);
		Meeting meeting = dao.getMeetingByID();
		return meeting;
	}

	/**
	 * Creates a participant with DAO
	 * 
	 * @param participantId
	 *            participantId
	 * @return participantForMeetingId participant for meeting ID
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private Participant createParticipantWithDao(int participantId) throws IOException, CustomServerException {
		ParticipantDAO participantDAO = new ParticipantDaoImpl();
		participantDAO.setParticipantId(participantId);
		Participant participantForMeetingId = participantDAO.getParticipantByID();
		return participantForMeetingId;
	}

	/**
	 * Checks if user is meeting participant and creator
	 * 
	 * @param userId
	 *            userId
	 * @param participantId
	 *            participantId
	 * @throws IOException
	 *             IOException
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private void checkIfUserIsParticipantAndCreator(int userId, int participantId)
			throws IOException, CustomServerException {
		Participant participantForMeetingId = createParticipantWithDao(participantId);
		User user = createUserWithDao(userId);
		Meeting meetingCheckParticipant = createMeetingWithDao(participantForMeetingId.getMeetingId());
		meetingCheckParticipant.isParticipant(user);
		meetingCheckParticipant.isCreator(user);
	}

}
