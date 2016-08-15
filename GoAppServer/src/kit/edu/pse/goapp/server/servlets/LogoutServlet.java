/*
 * @version 1.0
 * @author PSE group
 */
package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor for class LogoutServlet
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
	}

	/**
	 * method for logout
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("userID", "");
		request.getSession().invalidate();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
