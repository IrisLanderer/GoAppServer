/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.converter.daos;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kit.edu.pse.goapp.server.daos.GroupMemberDAO;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.datamodels.GroupMember;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public class GroupMemberDaoConverter implements DaoConverter<GroupMemberDAO> {

	@Override
	public GroupMemberDAO parse(String jsonString) throws CustomServerException {
		if (jsonString == null) {
			return null;
		}
		GroupMember groupMemberJsonObject = null;
		try {
			Gson gson = new Gson();
			groupMemberJsonObject = gson.fromJson(jsonString, GroupMember.class);
		} catch (Exception e) {
			throw new CustomServerException("The JSON-String is not correct!", HttpServletResponse.SC_BAD_REQUEST);
		}

		GroupMemberDAO dao = new GroupMemberDaoImpl();
		dao.setGroupId(groupMemberJsonObject.getGroupId());
		dao.setUserId(groupMemberJsonObject.getUserId());
		dao.setAdmin(groupMemberJsonObject.isAdmin());
		return dao;
	}

}
