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
import kit.edu.pse.goapp.server.servlets.GroupServlet;

/**
 * @author Iris
 *
 */
public class GroupServletTest extends GroupServlet {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getParameter("groupId")).thenReturn("500");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupServletTest servlet = new GroupServletTest();
		servlet.doGet(request, response, authentication);

	}

	@Test
	public void testDoPost() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine()).thenReturn("{ \"name\":\"Test\" }");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupServletTest servlet = new GroupServletTest();
		servlet.doPost(request, response, authentication);
	}

	@Test
	public void testDoUpdate() throws CustomServerException, IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
		Mockito.when(request.getReader().readLine()).thenReturn("{ \"groupId\" : \"500\", \"name\":\"Test\" }");

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		GroupServletTest servlet = new GroupServletTest();
		servlet.doPut(request, response, authentication);
	}

	// @Test(expected = Exception.class)
	// public void testDoUpdateFails() throws CustomServerException,
	// IOException, ServletException {
	// HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	// Mockito.when(request.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
	// Mockito.when(request.getReader().readLine()).thenReturn("{ \"groupId\" :
	// \"50xxx0\", \"name\":\"Texxxst\" }");
	//
	// HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	// Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));
	//
	// Authentication authentication = Mockito.mock(Authentication.class);
	// Mockito.when(authentication.authenticateUser(request)).thenReturn(500);
	//
	// GroupServletTest servlet = new GroupServletTest();
	// servlet.doPut(request, response, authentication);
	// }

}
