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
import javax.servlet.http.HttpSession;

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
		// TODO Auto-generated method stub
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

			// chain.doFilter(request, response);
			// return;

			HttpSession session = request.getSession();
			String loginURI = request.getContextPath() + "/Login";

			boolean loggedIn = session.getAttribute("userId") != null;

			if (loggedIn && session != null) {
				loggedIn = false;
				if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
					loggedIn = true;
				} else if (session.getAttribute("registerToken") != null
						&& (boolean) session.getAttribute("registerToken")) {
					session.setAttribute("registerToken", false);
					loggedIn = true;
				}
			}
			boolean loginRequest = request.getRequestURI().equals(loginURI);

			if (loggedIn || loginRequest) {
				chain.doFilter(request, response);
			} else {
				throw new CustomServerException("The User is not logged in!", HttpServletResponse.SC_BAD_REQUEST);
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
		// TODO Auto-generated method stub
	}

}
