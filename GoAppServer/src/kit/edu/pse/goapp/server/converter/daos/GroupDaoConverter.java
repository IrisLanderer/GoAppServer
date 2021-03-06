/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.daos;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupDAO;
import kit.edu.pse.goapp.server.daos.GroupDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Group;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

/**
 * Group dao converter
 */
public class GroupDaoConverter implements DaoConverter<GroupDAO> {

    /**
     * Converts group json string to group dao
     * 
     * @param jsonString
     *            JsonString
     * @return dao group dao
     * @throws CustomServerException
     *             CustomServerException
     */
    @Override
    public GroupDAO parse(String jsonString) throws CustomServerException {
        if (jsonString == null) {
            return null;
        }
        Group groupJsonObject = null;
        try {
            Gson gson = new Gson();
            groupJsonObject = gson.fromJson(jsonString, Group.class);
        } catch (Exception e) {
            throw new CustomServerException("The JSON-String is not correct!",
                    HttpServletResponse.SC_BAD_REQUEST);
        }
        GroupDAO dao = new GroupDaoImpl();
        dao.setName(groupJsonObject.getName());
        dao.setGroupId(groupJsonObject.getId());
        return dao;

    }
}
