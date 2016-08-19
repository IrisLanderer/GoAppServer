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
import kit.edu.pse.goapp.server.servlets.GroupUserManagementServlet;

/**
 * @author Iris
 *
 */
public class GroupUserManagementServletTest extends GroupUserManagementServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("groupId")).thenReturn("500");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupUserManagementServletTest servlet = new GroupUserManagementServletTest();
		servlet.doGet(request, response, authentication);

	}

	// this test only works at the first time because one can't add an user to
	// the same group twice a time
	@Test
	public void testDoPost() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{ \"groupId\":\"500\", \"userId\":\"502\", \"isAdmin\":\"true\" }");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupUserManagementServletTest servlet = new GroupUserManagementServletTest();
		servlet.doPost(request, response, authentication);
	}

	@Test
	public void testDoPut() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine())
				.thenReturn("{ \"groupId\" : \"500\", \"userId\":\"502\", \"isAdmin\":\"false\" }");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupUserManagementServletTest servlet = new GroupUserManagementServletTest();
		servlet.doPut(request, response, authentication);
	}

}
