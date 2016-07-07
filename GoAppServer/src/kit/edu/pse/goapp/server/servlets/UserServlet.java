package kit.edu.pse.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import kit.edu.pse.goapp.server.converter.daos.UserDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get User details?
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		UserDAO dao = new UserDaoImpl();
		dao.setUserId(Integer.parseInt(userId));
		if (dao != null) {
			User user = dao.getUserByID();
			response.getWriter().write(new ObjectConverter<User>().serialize(user));
		}
	}

	/**
	 * CreateUser
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String googleId = request.getParameter("googleId");
		String jsonString = request.getReader().readLine();
		UserDAO dao = new UserDaoConverter().parse(jsonString);
		dao.setNotificationEnabled(true);
		dao.setGoogleId(googleId);
		if (dao != null) {
			dao.addUser();
		}
		User user = dao.getUserByID();
		response.getWriter().write(new ObjectConverter<User>().serialize(user));
	}

	/**
	 * UpdateUser
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		UserDAO dao = new UserDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.updateUser();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		User user = dao.getUserByID();
		response.getWriter().write(new ObjectConverter<User>().serialize(user));
	}

	/**
	 * Delete User
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = request.getReader().readLine();
		UserDAO dao = new UserDaoConverter().parse(jsonString);
		if (dao != null) {
			dao.deleteUser();
		} else {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
