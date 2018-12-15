package top.qingrang.service;

import top.qingrang.pojo.Permission;
import top.qingrang.pojo.Role;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
	Set<String> listPermissionName(String name);

	List<Permission> getPermissionListByRole(Role role);

	List<Permission> getPermissionList();

	void addPermission(Permission permission);

	Permission getPermissionByID(long id);

	void updatePermission(Permission permission);

	void deletePermission(long id);

	boolean needInterceptor(String requestURI);

	Set<String> listPermissionURLs(String userName);
}
