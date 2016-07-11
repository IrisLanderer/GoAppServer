/*
 * @version 1.0
 * @author PSE group
 */

package kit.edu.pse.goapp.server.daos;

import java.io.IOException;
import java.util.List;

import kit.edu.pse.goapp.server.datamodels.User;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;

public interface GroupMemberDAO {

	public void addMember() throws IOException;

	public void deleteMember() throws IOException;

	public void updateMember() throws IOException;

	public List<User> getAllMembers() throws IOException, CustomServerException;

	public void setAdmin();

	public List<User> getAllAdmins() throws IOException;

	public int getGroupId();

	public void setGroupId(int groupId);

	public int getUserId();

	public void setUserId(int userId);

	public boolean isAdmin();

	public void setAdmin(boolean isAdmin);

}
