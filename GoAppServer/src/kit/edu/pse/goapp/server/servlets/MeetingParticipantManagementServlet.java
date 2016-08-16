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

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.converter.daos.ParticipantDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.creating_obj_with_dao.MeetingWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.ParticipantWithDao;
import kit.edu.pse.goapp.server.creating_obj_with_dao.UserWithDao;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.MeetingValidation;

/**
 * Servlet implementation class MeetingParticipantManagement
 */
@WebServlet("/MeetingParticipantManagement")
public class MeetingParticipantManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserWithDao userWithDao = new UserWithDao();
	private ParticipantWithDao participantWithDao = new ParticipantWithDao();
	private MeetingWithDao meetingWithDao = new MeetingWithDao();

	private MeetingValidation validation = new MeetingValidation();

	private Authentication authentication = new Authentication();

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingParticipantManagementServlet() {
		super();
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
			int userId = authentication.authenticateUser(request);
			String participantId = request.getParameter("participantId");
			ParticipantDAO dao = new ParticipantDaoImpl();
			try {
				dao.setParticipantId(Integer.parseInt(participantId));
			} catch (Exception e) {
				throw new CustomServerException("The ParticipantID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);

			}
			Participant participantForMeetingId = participantWithDao.createParticipantWithDao(dao.getParticipantId());
			User user = userWithDao.createUserWithDao(userId);
			Meeting meetingCheckParticipant = meetingWithDao.createMeetingWithDao(
					participantForMeetingId.getMeetingId(), participantForMeetingId.getUser().getId());
			meetingCheckParticipant.isParticipant(user);
			if (dao != null) {
				Participant participant = dao.getParticipantByID();
				response.getWriter()
						.write(new ObjectConverter<Participant>().serialize(participant, Participant.class));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			dao.setConfirmation(MeetingConfirmation.PENDING);
			Meeting meetingCheckingForAuthorization = meetingWithDao.createMeetingWithDao(dao.getMeetingId(),
					dao.getUserId());
			User user = userWithDao.createUserWithDao(userId);
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
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			validation.checkIfUserIsParticipantAndCreator(userId, dao.getParticipantId());
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
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
			int userId = authentication.authenticateUser(request);
			String participantId = request.getParameter("participantId");
			ParticipantDAO dao = new ParticipantDaoImpl();
			try {
				dao.setParticipantId(Integer.parseInt(participantId));
			} catch (Exception e) {
				throw new CustomServerException("The ParticipantID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			validation.checkIfUserIsParticipantAndCreator(userId, dao.getParticipantId());
			if (dao != null) {
				dao.deleteParticipant();
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
