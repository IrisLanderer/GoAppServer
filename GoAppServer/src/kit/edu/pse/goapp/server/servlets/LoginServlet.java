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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = "132993241263-8t9fa24d8tmqhit12p6cl94b69ci122d.apps.googleusercontent.com";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * login
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("token") != null) {
			String requestToken = request.getParameter("token").toString();
			System.out.println("token:" + requestToken);

			GoogleIdToken googleToken = validateToken(requestToken);
			String googleId = getGoogleId(googleToken);
			if (googleId.length() > 0) {
				HttpSession session = request.getSession(true);
				UserDAO userDao = new UserDaoImpl();
				userDao.setGoogleId(googleId);
				String userId = Integer.toString(userDao.getUserByGoogleID().getId());
				session.setAttribute("userId", userId);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		// doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private String getGoogleId(GoogleIdToken token) {
		if (token != null) {
			Payload payload = token.getPayload();
			String userId = payload.getSubject();
			return userId;
		}
		return "";
	}

	private GoogleIdToken validateToken(String token) {
		JsonFactory jsonFactory = new JacksonFactory();
		NetHttpTransport transport = new NetHttpTransport();
		/*
		 * GoogleIdTokenVerifier verifier = new
		 * GoogleIdTokenVerifier.Builder(transport, jsonFactory)
		 * .setAudience(Arrays.asList(CLIENT_ID)) // If you retrieved the token
		 * on Android using the Play Services // 8.3 API or newer, set // the
		 * issuer to "https://accounts.google.com". Otherwise, set // the issuer
		 * to // "accounts.google.com". If you need to verify tokens from //
		 * multiple sources, build // a GoogleIdTokenVerifier for each issuer
		 * and try them both. .setIssuer("https://accounts.google.com").build();
		 */
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(transport, jsonFactory);
		verifier.getAudience().add(CLIENT_ID);
		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(token);
		} catch (Exception e) {
			idToken = null;
		}
		return idToken;
	}
}
