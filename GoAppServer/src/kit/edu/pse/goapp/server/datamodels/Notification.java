/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.datamodels;

/**
 * Database of notifications
 */
public class Notification {

    private String text;

    /**
     * Constructor. Sets variables of notifications
     * 
     * @param text
     *            text
     */
    public Notification(String text) {
        this.text = text;
    }

    /**
     * Returns text
     * 
     * @return text text
     */
    public String getText() {
        return text;
    }

    /**
     * Equals an object to this notification
     * 
     * @param obj
     *            Object to compare
     * @return boolean true if object is equal to this notification, else false
     */
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
        if (n.getText().equals(text)) {
            return true;
        }
        return false;
    }

}
