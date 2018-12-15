package top.qingrang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qingrang.dao.IRolePermissionDao;
import top.qingrang.pojo.Role;
import top.qingrang.pojo.RolePermission;
import top.qingrang.service.IRolePermissionService;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements IRolePermissionService {
	@Autowired
	IRolePermissionDao rolePermissionDao;

	@Override
	public void updateRolePermission(Role role, long[] permissionIds) {
		// 删除当前角色所有的权限
		List<RolePermission> rolePermissionList = rolePermissionDao.selectListRolePermissionByRID(role.getId());
		for (RolePermission rolePermission : rolePermissionList){
			rolePermissionDao.deleteByPrimaryKey(rolePermission.getId());
		}

		// 设置新的权限关系
		if (null != permissionIds){
			for (long pid : permissionIds) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setPid(pid);
				rolePermission.setRid(role.getId());
				rolePermissionDao.insertRolePermission(rolePermission);
			}
		}
	}
}
