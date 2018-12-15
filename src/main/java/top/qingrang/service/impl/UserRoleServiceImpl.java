package top.qingrang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qingrang.dao.IUserRoleDao;
import top.qingrang.pojo.User;
import top.qingrang.pojo.UserRole;
import top.qingrang.service.IUserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

	@Autowired
	IUserRoleDao userRoleDao;


	@Override
	public void editUserRole(User user, long[] roleIds) {
		// 删除当前用户所有的角色
		List<UserRole> userRoleList = userRoleDao.getUserRoleByUID(user.getId());
		for (UserRole userRole : userRoleList){
			userRoleDao.deleteByPrimaryKey(userRole.getId());
		}

		// 设置新的角色关系
		if (null != roleIds){
			for (long rid : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setRid(rid);
				userRole.setUid(user.getId());
				userRoleDao.insertUserRole(userRole);
			}
		}
	}
}
