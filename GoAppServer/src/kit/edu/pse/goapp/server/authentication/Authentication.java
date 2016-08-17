/**
 * 
 */
package kit.edu.pse.goapp.server.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.servlets.CookieManager;

/**
 * @author Iris
 *
 */
public class Authentication {

	/**
	 * Authenticates an user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	public int authenticateUser(HttpServletRequest request) throws CustomServerException {
		CookieManager cm = new CookieManager();
		return authenticateUser(request, cm);
	}

	protected int authenticateUser(HttpServletRequest request, CookieManager cm) throws CustomServerException {

		String userIDString = cm.searchCookie(request, "userId");
		if (userIDString.length() > 0) {
			int userId = Integer.parseInt(userIDString);
			if (userId > 0) {
				return userId;
			}
		}
		throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
	}

}
