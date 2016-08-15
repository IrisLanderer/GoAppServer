/**
 * 
 */
package kit.edu.pse.goapp.server.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author grisc
 *
 */
public class CookieManager {

	public String searchCookie(HttpServletRequest request, String name) {
		Cookie search = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					search = cookie;
					break;
				}
			}
		}
		if (search != null) {
			return search.getValue();
		}
		return "";
	}

	public void addCookie(HttpServletResponse response, String name, String value, int secMaxAge) {
		Cookie crunchifyCookie = new Cookie(name, value);
		crunchifyCookie.setMaxAge(secMaxAge);
		response.addCookie(crunchifyCookie);
	}

	public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie search = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					search = cookie;
					break;
				}
			}
		}
		if (search != null) {
			search.setMaxAge(0);
			response.addCookie(search);
		}

	}

}
