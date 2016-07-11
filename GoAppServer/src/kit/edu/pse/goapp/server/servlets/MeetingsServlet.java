/*
 * @version 1.0
 * @author Iris
 */
package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.algorithm.MeetingGpsAlgorithm;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;

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
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MeetingDaoImpl dao = new MeetingDaoImpl();
		dao.setUserId(1);
		if (dao != null) {
			List<Meeting> meetings = dao.getAllMeetings();
			for(Meeting m : meetings)
			{
				if(m instanceof Tour)
				{
					MeetingGpsAlgorithm.setGpsTour((Tour)m);
				}
				else
				{
					MeetingGpsAlgorithm.setGpsEvent((Event)m);
				}
			}
			response.getWriter().write(new ObjectConverter<List<Meeting>>().serialize(meetings,
					(Class<List<Meeting>>) meetings.getClass()));

		}
	}

}