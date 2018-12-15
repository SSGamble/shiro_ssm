package top.qingrang.service;

import top.qingrang.pojo.Role;

public interface IRolePermissionService {
	void updateRolePermission(Role role, long[] permissionIds);
}
