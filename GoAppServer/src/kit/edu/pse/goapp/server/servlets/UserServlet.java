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

import kit.edu.pse.goapp.server.authentication.Authentication;
import kit.edu.pse.goapp.server.converter.daos.UserDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Authentication authentication = new Authentication();

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
	}

	/**
	 * Get User details
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String userId = request.getParameter("userId");
			UserDAO dao = new UserDaoImpl();
			try {
				dao.setUserId(Integer.parseInt(userId));
			} catch (Exception e) {
				throw new CustomServerException("The UserID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				User user = dao.getUserByID();
				response.getWriter().write(new ObjectConverter<User>().serialize(user, User.class));
			}
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * CreateUser
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CookieManager cm = new CookieManager();

			String googleId = cm.searchCookie(request, "googleId");
			if (googleId.length() > 0) {

				String jsonString = request.getReader().readLine();
				UserDAO dao = new UserDaoConverter().parse(jsonString);
				// every user has notifications enabled by default
				dao.setNotificationEnabled(true);
				dao.setGoogleId(googleId);
				if (dao != null) {
					dao.addUser();
				}
				User user = dao.getUserByID();
				response.getWriter().write(new ObjectConverter<User>().serialize(user, User.class));
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (

		CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * UpdateUser
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authentication.authenticateUser(request);
			String jsonString = request.getReader().readLine();
			UserDAO dao = new UserDaoConverter().parse(jsonString);
			if (userId != dao.getUserId()) {
				throw new CustomServerException("The UserID from the JSON string isn't the same as the actual user!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				dao.updateUser();
			}
			User user = dao.getUserByID();
			response.getWriter().write(new ObjectConverter<User>().serialize(user, User.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete User
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int authenticatingUserId = authentication.authenticateUser(request);
			String userId = request.getParameter("userId");
			UserDAO dao = new UserDaoImpl();
			try {
				dao.setUserId(Integer.parseInt(userId));
			} catch (Exception e) {
				throw new CustomServerException("The UserID from the JSON string isn't correct!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (authenticatingUserId != Integer.parseInt(userId)) {
				throw new CustomServerException("The UserID from the JSON string isn't the same as the actual user!",
						HttpServletResponse.SC_BAD_REQUEST);
			}
			if (dao != null) {
				dao.deleteUser();
			}
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		} catch (IOException io) {
			response.getWriter().write(io.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
