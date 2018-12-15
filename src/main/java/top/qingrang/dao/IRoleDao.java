package top.qingrang.dao;

import top.qingrang.pojo.Role;

import java.util.List;

public interface IRoleDao {
	Role selectByPrimaryKey(Long id);

	List<Role> selectRoleList();

	void insertRole(Role role);

	void updateRole(Role role);

	void deleteRoleByID(long id);
}
