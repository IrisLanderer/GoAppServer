/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.servlets.MeetingServlet;

/**
 * @author Iris
 *
 */
public class MeetingServletTest extends MeetingServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGetEvent() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("meetingId")).thenReturn("500");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doGet(request, response, authentication);

	}

	@Test
	public void testDoGetTour() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("meetingId")).thenReturn("501");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doGet(request, response, authentication);

	}

	@Test
	public void testDoPostTour() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{\"type\":\"Tour\",\"center\":{\""
						+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
						+ ":15,\"name\":\"testTourDoPost\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
						+ "\":3.0},\"timestamp\":13,\"duration\":2,\"creator"
						+ "\":{\"participantId\":1,\"meetingId\":15,\"user\":{\"userId\":600,\"name\":\"testName\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
						+ "\"CONFIRMED\"},\"participants\":[]}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(600);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doPost(request, response, authentication);
	}

	@Test
	public void testDoPostEvent() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{\"type\":\"Event\",\"center\":{\""
						+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
						+ ":15,\"name\":\"testEventDoPost\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
						+ "\":3.0},\"timestamp\":13,\"duration\":2,\"creator"
						+ "\":{\"participantId\":1,\"meetingId\":15,\"user\":{\"userId\":600,\"name\":\"testName\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
						+ "\"CONFIRMED\"},\"participants\":[]}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(600);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doPost(request, response, authentication);
	}

	@Test
	public void testDoPutEvent() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{\"type\":\"Event\",\"center\":{\""
						+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
						+ ":504,\"name\":\"testEventDoPost\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
						+ "\":3.0},\"timestamp\":13,\"duration\":2,\"creator"
						+ "\":{\"participantId\":505,\"meetingId\":504,\"user\":{\"userId\":600,\"name\":\"Grischa\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
						+ "\"CONFIRMED\"},\"participants\":[]}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(600);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doPut(request, response, authentication);
	}

	@Test
	public void testDoPutTour() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{\"type\":\"Tour\",\"center\":{\""
						+ "participants\":[],\"place\":{\"x\":1.0,\"y\":2.0,\"z\":3.0}},\"meetingId\""
						+ ":503,\"name\":\"testTourDoPost\",\"place\":{\"x\":1.0,\"y\":2.0,\"z"
						+ "\":3.0},\"timestamp\":13,\"duration\":2,\"creator"
						+ "\":{\"participantId\":504,\"meetingId\":503,\"user\":{\"userId\":600,\"name\":\"Grischa\",\"notificationEnabled\":false,\"meetings\":[],\"groups\":[]},\"confirmation\":"
						+ "\"CONFIRMED\"},\"participants\":[]}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(600);

		MeetingServletTest servlet = new MeetingServletTest();
		servlet.doPut(request, response, authentication);
	}

}
