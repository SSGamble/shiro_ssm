package top.qingrang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qingrang.dao.IPermissionDao;
import top.qingrang.dao.IRolePermissionDao;
import top.qingrang.pojo.Permission;
import top.qingrang.pojo.Role;
import top.qingrang.pojo.RolePermission;
import top.qingrang.service.IPermissionService;
import top.qingrang.service.IRoleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	IRoleService roleService;
	@Autowired
	IRolePermissionDao rolePermissionDao;
	@Autowired
	IPermissionDao permissionDao;

	@Override
	public Set<String> listPermissionName(String name) {
		Set<String> result = new HashSet<>();
		List<Role> roles = roleService.listRoleByName(name);
		List<RolePermission> rolePermissions = new ArrayList<>();
		for (Role role : roles) {
			List<RolePermission> rps = rolePermissionDao.selectListRolePermissionByRID(role.getId());
			rolePermissions.addAll(rps);
		}

		for (RolePermission rolePermission : rolePermissions) {
			Permission p = permissionDao.selectByPrimaryKey(rolePermission.getPid());
			result.add(p.getName());
		}

		return result;
	}

	@Override
	public List<Permission> getPermissionListByRole(Role role) {
		List<Permission> result = new ArrayList<>();

		List<RolePermission> rolePermissionList = rolePermissionDao.selectListRolePermissionByRID(role.getId());

		for (RolePermission rolePermission : rolePermissionList) {
			result.add(permissionDao.selectByPrimaryKey(rolePermission.getPid()));
		}
		return result;
	}

	@Override
	public List<Permission> getPermissionList() {
		return permissionDao.selectPermissionList();
	}

	@Override
	public void addPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}

	@Override
	public Permission getPermissionByID(long id) {
		return permissionDao.selectByPrimaryKey(id);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionDao.updatePermission(permission);
	}

	@Override
	public void deletePermission(long id) {
		permissionDao.deletePermission(id);
	}

	/**
	 * needInterceptor 表示是否要进行拦截，
	 * 判断依据是如果访问的某个 url,在权限系统里存在，就要进行拦截。 如果不存在，就放行了。
	 * 也可以用这种，即，访问的地址如果不存在于权限系统中，就提示没有拦截。
	 */
	@Override
	public boolean needInterceptor(String requestURI) {
		List<Permission> ps = getPermissionList();
		for (Permission p : ps) {
			if (p.getUrl().equals(requestURI)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 用来获取某个用户所拥有的权限地址集合
	 */
	@Override
	public Set<String> listPermissionURLs(String userName) {
		Set<String> result = new HashSet<>();
		List<Role> roles = roleService.listRoleByName(userName);

		List<RolePermission> rolePermissions = new ArrayList<>();

		for (Role role : roles) {
			List<RolePermission> rps = rolePermissionDao.selectListRolePermissionByRID(role.getId());
			rolePermissions.addAll(rps);
		}
		for (RolePermission rolePermission : rolePermissions) {
			Permission p = permissionDao.selectByPrimaryKey(rolePermission.getPid());
			result.add(p.getUrl());
		}
		return result;
	}
}
