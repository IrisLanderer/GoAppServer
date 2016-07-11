/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junit.framework.TestCase;
import kit.edu.pse.goapp.server.servlets.GroupServlet;

public class GroupServletTest extends TestCase {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;

	@Override
	@Before
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() throws Exception {

		when(request.getParameter("name")).thenReturn("PSE");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);

		new GroupServlet().doPost(request, response);

		String result = sw.getBuffer().toString().trim();

		assertEquals("Login successfull...", result);
	}
}
