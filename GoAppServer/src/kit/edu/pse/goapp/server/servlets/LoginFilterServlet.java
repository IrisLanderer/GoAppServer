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
		// try {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		session.setAttribute("userId", 1);
		chain.doFilter(request, response);
		// session.setAttribute("userId", 1);
		// chain.doFilter(request, response);
		// return;
		// todo delete above 2 lines

		// HttpSession session = request.getSession(false);
		// String loginURI = request.getContextPath() + "/Login";
		/*
		 * if (session == null) { if (request.getRequestURI().contains("Login"))
		 * { chain.doFilter(request, response); } else throw new
		 * CustomServerException("error auth2",
		 * HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION); } else {
		 * boolean loggedIn = false; if (session.getAttribute("loggedIn") !=
		 * null) if ((boolean) session.getAttribute("loggedIn")) { loggedIn =
		 * true; } else if (session.getAttribute("registerToken") != null) if
		 * ((boolean) session.getAttribute("registerToken")) {
		 * session.setAttribute("registerToken", false); loggedIn = true; }
		 * 
		 * boolean loginRequest = request.getRequestURI().contains("Login");
		 * boolean register = request.getRequestURI().contains("User"); if
		 * (loggedIn || loginRequest || register) { chain.doFilter(request,
		 * response); } else { throw new CustomServerException("error auth",
		 * HttpServletResponse.SC_UNAUTHORIZED); } } } catch
		 * (CustomServerException e) { ((HttpServletResponse)
		 * res).setStatus(e.getStatusCode());
		 * res.getWriter().write(e.toString()); }
		 */
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
