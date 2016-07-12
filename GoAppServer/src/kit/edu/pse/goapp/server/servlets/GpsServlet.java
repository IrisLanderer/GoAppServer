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
import javax.servlet.http.HttpSession;

import kit.edu.pse.goapp.server.converter.daos.GpsDaoConverter;
import kit.edu.pse.goapp.server.converter.objects.ObjectConverter;
import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Servlet implementation class GpsServlet
 */
@WebServlet("/Gps")
public class GpsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GpsServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userId = authenticateUser(request);
			String jsonString = request.getReader().readLine();
			GpsDaoImpl dao = (GpsDaoImpl) new GpsDaoConverter().parse(jsonString);
			dao.setUserId(userId);
			if (dao != null) {
				dao.setUserId(userId);
				dao.userSetGPS();
			}
			GPS gps = dao.userGetGPS();
			response.getWriter().write(new ObjectConverter<GPS>().serialize(gps, GPS.class));
		} catch (CustomServerException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(e.toString());
		}
	}

	private int authenticateUser(HttpServletRequest request) throws CustomServerException {
		HttpSession session = request.getSession(true);

		int userId = 1;// (int) session.getAttribute("userId");
		if (userId <= 0) {
			throw new CustomServerException("This user is unauthorized!", HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userId;
	}

}
