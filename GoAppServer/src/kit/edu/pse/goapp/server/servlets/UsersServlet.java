/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Users
 */
@WebServlet("/Users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public UsersServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets users
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// int userId = authenticateUser(request);
			UserDAO dao = new UserDaoImpl();
			// dao.setUserId(userId);
			if (dao != null) {
				List<User> users = dao.getAllUsers();
				response.getWriter().write(
						new ObjectConverter<List<User>>().serialize(users, (Class<List<User>>) users.getClass()));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Authenticates an user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	// private int authenticateUser(HttpServletRequest request) throws
	// CustomServerException {
	// HttpSession session = request.getSession(true);
	//
	// // int userId = 1;// (int) session.getAttribute("userId");
	// int userId = (int) session.getAttribute("userId");
	// if (userId <= 0) {
	// throw new CustomServerException("This user is unauthorized!",
	// HttpServletResponse.SC_UNAUTHORIZED);
	// }
	// return userId;
	// }

}
