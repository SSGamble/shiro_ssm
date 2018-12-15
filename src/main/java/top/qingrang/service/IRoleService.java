package top.qingrang.service;

import top.qingrang.pojo.Role;
import top.qingrang.pojo.User;

import java.util.List;
import java.util.Set;

public interface IRoleService{
	Set<String> listRoleName(String name);
	List<Role> listRoleByName(String name);

	List<Role> listRoleByUser(User user);

	List<Role> listRole();

	void addRole(Role role);

	Role getRole(long id);

	void updateRole(Role role);

	void deleteRoleByID(long id);
}
