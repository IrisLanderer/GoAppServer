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
import kit.edu.pse.goapp.server.servlets.MeetingParticipantManagementServlet;

/**
 * @author Iris
 *
 */
public class MeetingParticipantManagementServletTest extends MeetingParticipantManagementServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("participantId")).thenReturn("500");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingParticipantManagementServletTest servlet = new MeetingParticipantManagementServletTest();
		servlet.doGet(request, response, authentication);

	}

	// test just works at the first time because one can't add an user to the
	// same meeting twice a time
	@Test
	public void testDoPost() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine()).thenReturn(
				"{\"meetingId\":500 ,\"participantId\":0,\"user\":{\"userId\":501,\"name\":\"Grischa\",\"meetings\":[],\"groups\":[]},\"confirmation\":\"PENDING\"}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingParticipantManagementServletTest servlet = new MeetingParticipantManagementServletTest();
		servlet.doPost(request, response, authentication);
	}

	@Test
	public void testDoPut() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine()).thenReturn(
				"{\"meetingId\":500 ,\"participantId\":500,\"user\":{\"userId\":500,\"name\":\"Iris\",\"meetings\":[],\"groups\":[]},\"confirmation\":\"CONFIRMED\"}");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingParticipantManagementServletTest servlet = new MeetingParticipantManagementServletTest();
		servlet.doPut(request, response, authentication);
	}

}
