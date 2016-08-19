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

import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.servlets.UsersServlet;

/**
 * @author Iris
 *
 */
public class UsersServletTest extends UsersServlet {

	private static final long serialVersionUID = 1L;

	@Test
	public void testDoGet() throws ServletException, IOException, CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));

		UsersServletTest servlet = new UsersServletTest();
		servlet.doGet(request, response);

	}
}
