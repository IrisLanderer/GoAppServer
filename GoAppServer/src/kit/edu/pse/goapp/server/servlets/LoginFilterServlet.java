/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilterServlet implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilterServlet() {

	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * checks if someone is logged in and ,if not, if he is on the login service
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			CookieManager cm = new CookieManager();
			String userIdString = cm.searchCookie(request, "userId");
			String loggedInString = cm.searchCookie(request, "loggedIn");
			String registerString = cm.searchCookie(request, "register");
			String loginURI = request.getContextPath() + "/Login";

			boolean loggedIn = false;

			if (userIdString.length() > 0) {
				if (loggedInString.length() > 0) {
					loggedIn = true;
				}
			}
			if (registerString.length() > 0) {
				cm.deleteCookie(request, response, "register");
				loggedIn = true;
			}

			boolean loginRequest = request.getRequestURI().equals(loginURI);

			if (loggedIn || loginRequest) {
				cm.addCookie(response, "userId", userIdString, 3600);
				cm.addCookie(response, "loggedIn", loggedInString, 3600);

				chain.doFilter(request, response);
			} else {
				throw new CustomServerException(
						"The User is not logged in! + userId :" + userIdString + " loginCookie: " + loggedInString,
						HttpServletResponse.SC_UNAUTHORIZED);
			}

		} catch (CustomServerException e) {
			((HttpServletResponse) res).setStatus(e.getStatusCode());
			res.getWriter().write(e.toString());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}
}
