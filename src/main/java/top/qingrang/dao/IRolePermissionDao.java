package top.qingrang.dao;

import top.qingrang.pojo.RolePermission;

import java.util.List;

public interface IRolePermissionDao {
	List<RolePermission> selectListRolePermissionByRID(Long rid);

	void deleteByPrimaryKey(Long id);

	void insertRolePermission(RolePermission rolePermission);
}
