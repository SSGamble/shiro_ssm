package top.qingrang.service;

import top.qingrang.pojo.User;

public interface IUserRoleService {
	void editUserRole(User user, long[] roleIds);
}
