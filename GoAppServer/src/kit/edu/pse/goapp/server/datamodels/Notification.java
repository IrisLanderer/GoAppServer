/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

public class Notification {

	private String text;
	public Notification(String text)
	{
		this.text = text;
	}
	public String getText()
	{
		return text;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		// Class name is Employ & have lastname
		Notification n = (Notification) obj;
		 if(n.getText().equals(text)) {
			return true;
		}
		return false;
	}
	
}
