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
import javax.servlet.http.HttpSession;

import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.NotificationDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Notification;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class Notification
 */
@WebServlet("/Notification")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationServlet() {
		super();
	}

	/**
	 * GetNotification
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			NotificationDaoImpl dao = new NotificationDaoImpl();
			dao.setUserId(userId);
			List<Notification> notifications = dao.getNotifications();
			response.getWriter().write(new ObjectConverter<List<Notification>>().serialize(notifications,
					(Class<List<Notification>>) notifications.getClass()));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	/**
	 * Authenticates user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return userId
	 * @throws CustomServerException
	 *             CustomServerException
	 */
	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession();

		int userId = (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

}
