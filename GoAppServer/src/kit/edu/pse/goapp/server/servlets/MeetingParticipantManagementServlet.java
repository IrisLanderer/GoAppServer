/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import kit.edu.pse.goapp.server.converter.daos.ParticipantDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.daos.ParticipantDAO;
import kit.edu.pse.goapp.server.daos.ParticipantDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class MeetingParticipantManagement
 */
@WebServlet("/MeetingParticipantManagement")
public class MeetingParticipantManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingParticipantManagementServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String participantId = request.getParameter("participantId");
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(Integer.parseInt(participantId));
		if (dao != null) {
			Participant participant = dao.getParticipantByID();
			response.getWriter().write(new ObjectConverter<Participant>().serialize(participant, Participant.class));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jsonString = request.getReader().readLine();
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			dao.setConfirmation(MeetingConfirmation.PENDING);
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
			String jsonString = request.getReader().readLine();
			ParticipantDAO dao = new ParticipantDaoConverter().parse(jsonString);
			if (dao != null) {
				dao.updateParticipant();
			} else {
				throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
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
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String participantId = request.getParameter("participantId");
		ParticipantDAO dao = new ParticipantDaoImpl();
		dao.setParticipantId(Integer.parseInt(participantId));
		if (dao != null) {
			dao.deleteParticipant();
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
