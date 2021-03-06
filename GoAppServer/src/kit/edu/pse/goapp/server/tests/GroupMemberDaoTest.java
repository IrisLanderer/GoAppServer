/**
 * 
 */
package kit.edu.pse.goapp.server.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import kit.edu.pse.goapp.server.daos.DatabaseConnection;
import kit.edu.pse.goapp.server.daos.GroupMemberDaoImpl;
import kit.edu.pse.goapp.server.exceptions.CustomServerException;
import kit.edu.pse.goapp.server.validation.GroupValidation;

/**
 * @author Iris
 *
 */
public class GroupMemberDaoTest extends GroupMemberDaoImpl {

	@Test(expected = CustomServerException.class)
	public void addMemberWithoutGroupId() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setUserId(1);
		dao.addMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void addMemberWithoutUserId() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setGroupId(1);
		dao.addMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void addMemberSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setGroupId(1);
		dao.setUserId(1);
		dao.addMember(mock);

	}

	@Test(expected = CustomServerException.class)
	public void deleteMemberWithoutGroupID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setUserId(1);
		dao.deleteMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void deleteMemberWithoutUserID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setGroupId(1);
		dao.deleteMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void deleteMemberSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		// Mockito.when(dao.checkIfMember(groupMembers);)
		dao.setGroupId(1);
		dao.setUserId(1);
		dao.deleteMember(mock);

	}

	@Test(expected = CustomServerException.class)
	public void updateGroupWithoutGroupID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setUserId(1);
		dao.updateMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test(expected = CustomServerException.class)
	public void updateGroupWithoutUserID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setGroupId(1);
		dao.updateMember(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void updateMemberSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.setGroupId(1);
		dao.setUserId(1);
		dao.updateMember(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getAllMembersWithoutGroupID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.getAllMembers(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getAllMembersSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);
		GroupValidation validation = new GroupValidation();
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		List<Integer> memberIds = new ArrayList<>();
		memberIds.add(1);
		dao.setMemberIds(memberIds);
		dao.setGroupId(1);
		dao.getAllMembers(mock);

	}

	@Test(expected = CustomServerException.class)
	public void getAllAdminsWithoutGroupID() throws Exception {
		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		dao.getAllAdmins(Mockito.mock(DatabaseConnection.class));
	}

	@Test()
	public void getAllAdminsSuccessfully() throws Exception {
		// Stubbing behavior
		DatabaseConnection mock = Mockito.mock(DatabaseConnection.class);

		GroupMemberDaoTest dao = new GroupMemberDaoTest();
		List<Integer> adminIds = new ArrayList<>();
		adminIds.add(1);
		dao.setAdminIds(adminIds);
		dao.setGroupId(1);
		dao.getAllAdmins(mock);

	}

}
