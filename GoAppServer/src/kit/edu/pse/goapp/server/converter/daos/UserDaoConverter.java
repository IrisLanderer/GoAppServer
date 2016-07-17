/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.daos;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.UserDAO;
import kit.edu.pse.goapp.server.daos.UserDaoImpl;
import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * User dao converter
 */
public class UserDaoConverter implements DaoConverter<UserDAO> {

    /**
     * Converts user json string to user dao
     * 
     * @param jsonString
     *            JsonString
     * @return dao user dao
     * @throws CustomServerException
     *             CustomServerException
     */
    @Override
    public UserDAO parse(String jsonString) throws CustomServerException {
        if (jsonString == null) {
            return null;
        }
        User userJsonObject = null;
        try {
            Gson gson = new Gson();
            userJsonObject = gson.fromJson(jsonString, User.class);
        } catch (Exception e) {
            throw new CustomServerException("The JSON-String is not correct!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        UserDAO dao = new UserDaoImpl();
        dao.setName(userJsonObject.getName());
        dao.setUserId(userJsonObject.getId());
        dao.setNotificationEnabled(userJsonObject.isNotificationEnabled());
        return dao;
    }

}
