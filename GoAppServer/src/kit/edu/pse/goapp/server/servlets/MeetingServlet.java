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

import kit.edu.pse.goapp.server.algorithm.MeetingGpsAlgorithm;
import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Meeting
 */
@WebServlet("/Meeting")
public class MeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingServlet() {
		super();
		// TODO Auto-generated constructor stub
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
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();
			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
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
			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			if (dao != null) {
				dao.addMeeting();
			} else {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
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
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}

			String jsonString = request.getReader().readLine();
			MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
			if (dao != null) {
				dao.updateMeeting();
			} else {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
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
			HttpSession session = request.getSession(true);

			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			String meetingId = request.getParameter("meetingId");
			MeetingDAO dao = new MeetingDaoImpl();

			try {
				dao.setMeetingId(Integer.parseInt(meetingId));
			} catch (Exception e) {
				throw new CustomServerException("The MeetingID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				dao.deleteMeeting();
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

}
