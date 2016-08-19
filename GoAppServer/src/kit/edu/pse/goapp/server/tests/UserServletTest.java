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
import kit.edu.pse.goapp.server.servlets.UserServlet;

/**
 * @author Iris
 *
 */
public class UserServletTest extends UserServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("userId")).thenReturn("600");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		UserServletTest servlet = new UserServletTest();
		servlet.doGet(request, response, authentication);

	}

	@Test
	public void testDoPut() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{ \"userId\" : \"600\", \"name\" : \"Grischa\",  \"notificationEnabled\":\"true\" }");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(600);

		UserServletTest servlet = new UserServletTest();
		servlet.doPut(request, response, authentication);

	}

	// @Test
	// public void testDoPost() throws ServletException, IOException,
	// CustomServerException {
	// HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	// Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
	// Mockito.when(request.getReader().readLine())
	// .thenReturn("{ \"name\" : \"Kansei\", \"notificationEnabled\":\"true\"
	// }");
	//
	// HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	// Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));
	//
	// Authentication authentication = Mockito.mock(Authentication.class);
	// Mockito.when(authentication.authenticateUser(request)).thenReturn(600);
	//
	// UserServletTest servlet = new UserServletTest();
	// servlet.doPost(request, response, authentication);
	//
	// }

}
