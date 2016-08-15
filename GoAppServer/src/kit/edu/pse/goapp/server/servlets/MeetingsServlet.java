/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kit.edu.pse.goapp.server.algorithm.MeetingGpsAlgorithm;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Meetings
 */
@WebServlet("/Meetings")
public class MeetingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetingsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * GetAll Meetings
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			MeetingDAO dao = new MeetingDaoImpl();

			dao.setUserId(userId);
			List<Meeting> meetings = dao.getAllMeetings();

			String json = "[";

			for (Meeting meeting : meetings) {
				if (meeting instanceof Tour) {
					MeetingGpsAlgorithm.setGpsTour((Tour) meeting);
				} else {
					MeetingGpsAlgorithm.setGpsEvent((Event) meeting);
				}

				json += new ObjectConverter<Meeting>().serialize(meeting, Meeting.class);
				json += ",";
			}
			json += "]";
			response.getWriter().write(json);

		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}
	}

	/**
	 * Authenticate user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession(true);

		// int userId = 1;// (int) session.getAttribute("userId");
		int userId = (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

}