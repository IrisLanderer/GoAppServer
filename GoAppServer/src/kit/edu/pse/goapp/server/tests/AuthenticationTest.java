/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.servlets.CookieManager;

/**
 * @author Iris
 *
 */
public class AuthenticationTest extends Authentication {

	@Test
	public void authentication() throws CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		CookieManager cookieManager = Mockito.mock(CookieManager.class);
		Mockito.when(cookieManager.searchCookie(request, "userId")).thenReturn("1");
		AuthenticationTest authentication = new AuthenticationTest();
		authentication.authenticateUser(request, cookieManager);
	}

	@Test(expected = CustomServerException.class)
	public void AuthenticationNoUserId() throws CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		CookieManager cookieManager = Mockito.mock(CookieManager.class);
		Mockito.when(cookieManager.searchCookie(request, "userId")).thenReturn("0");
		AuthenticationTest authentication = new AuthenticationTest();
		authentication.authenticateUser(request, cookieManager);
	}

	@Test(expected = CustomServerException.class)
	public void AuthenticationEmptyString() throws CustomServerException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		CookieManager cookieManager = Mockito.mock(CookieManager.class);
		Mockito.when(cookieManager.searchCookie(request, "userId")).thenReturn("");
		AuthenticationTest authentication = new AuthenticationTest();
		authentication.authenticateUser(request, cookieManager);
	}

}
