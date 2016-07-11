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
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			int userId = 1;// (int) session.getAttribute("userId");
			if (userId <= 0) {
				throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
			}
			MeetingDaoImpl dao = new MeetingDaoImpl();
			dao.setUserId(1);
			if (dao != null) {
				List<Meeting> meetings = dao.getAllMeetings();
				for (Meeting m : meetings) {
					if (m instanceof Tour) {
						MeetingGpsAlgorithm.setGpsTour((Tour) m);
					} else {
						MeetingGpsAlgorithm.setGpsEvent((Event) m);
					}
				}
				response.getWriter().write(new ObjectConverter<List<Meeting>>().serialize(meetings,
						(Class<List<Meeting>>) meetings.getClass()));

			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.getMessage());
		}
	}

}