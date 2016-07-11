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

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilterServlet implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilterServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		chain.doFilter(request, response);
		return;
		// todo delete above 2 lines
		/*
		 * HttpSession session = request.getSession(false); String loginURI =
		 * request.getContextPath() + "/Login";
		 * 
		 * boolean loggedIn = session != null && session.getAttribute("userId")
		 * != null; boolean loginRequest =
		 * request.getRequestURI().equals(loginURI);
		 * 
		 * if (loggedIn || loginRequest) { chain.doFilter(request, response); }
		 * else { response.sendError(HttpServletResponse.SC_UNAUTHORIZED); }
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
