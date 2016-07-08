package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import kit.edu.pse.goapp.server.converter.daos.MeetingDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.MeetingDAO;
import kit.edu.pse.goapp.server.daos.MeetingDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.Tour;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String meetingId = request.getParameter("meetingId");
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(Integer.parseInt(meetingId));
		if (dao != null) {
			Meeting meeting = dao.getMeetingByID();
		    RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
		    	    .of(Meeting.class, "type")
		    	    .registerSubtype(Event.class, "event")
		    	    .registerSubtype(Tour.class, "tour");
		    	     Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
		    	     String json = gson.toJson(meeting);
		    	 	response.getWriter().write(json);
		}
	}

	/**
	 * Create meeting
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.addMeeting();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		Meeting meeting = dao.getMeetingByID();
	    RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
	    	    .of(Meeting.class, "type")
	    	    .registerSubtype(Event.class, "event")
	    	    .registerSubtype(Tour.class, "tour");
	    	     Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
	    	     String json = gson.toJson(meeting);
	    	 	response.getWriter().write(json);

	}

	/**
	 * Change meeting
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		MeetingDAO dao = new MeetingDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.updateMeeting();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		Meeting meeting = dao.getMeetingByID();
	    RuntimeTypeAdapterFactory<Meeting> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
	    	    .of(Meeting.class, "type")
	    	    .registerSubtype(Event.class, "event")
	    	    .registerSubtype(Tour.class, "tour");
	    	     Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
	    	     String json = gson.toJson(meeting);
	    	 	response.getWriter().write(json);

	}

	/**
	 * Delete meeting
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String meetingId = request.getParameter("meetingId");
		MeetingDAO dao = new MeetingDaoImpl();
		dao.setMeetingId(Integer.parseInt(meetingId));
		if (dao != null) {
			dao.deleteMeeting();
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
