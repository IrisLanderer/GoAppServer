/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.servlets.MeetingsServlet;

/**
 * @author Iris
 *
 */
public class MeetingsServletTest extends MeetingsServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.authenticateUser(request)).thenReturn(500);

		MeetingsServletTest servlet = new MeetingsServletTest();
		servlet.doGet(request, response, authentication);

	}

}
