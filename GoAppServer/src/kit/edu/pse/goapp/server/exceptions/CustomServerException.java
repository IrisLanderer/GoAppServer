/**
 * 
 */
package kit.edu.pse.goapp.server.exceptions;

/**
 * 
 * class for our server's exception which sends an error message and a status
 * code
 *
 */
public class CustomServerException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private int statusCode;

	/**
	 * the exception sends an error message and a status code
	 * 
	 * @param message
	 *            error message in exception
	 * @param statusCode
	 *            status code in exception
	 */
	public CustomServerException(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return Integer.toString(statusCode) + " " + message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return the exception's status code
	 */
	public int getStatusCode() {
		return statusCode;
	}
}
