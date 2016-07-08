package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import kit.edu.pse.goapp.server.daos.MeetingDAO;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MeetingDAO dao = new MeetingDaoImpl();
		if (dao != null) {
			List<Meeting> meetings = dao.getAllMeetings();
    RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
    .of(Meeting.class, "type")
    .registerSubtype(Event.class, "event")
    .registerSubtype(Tour.class, "tour");
     Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
     String json = gson.toJson(meetings);
 	response.getWriter().write(json);
		

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}