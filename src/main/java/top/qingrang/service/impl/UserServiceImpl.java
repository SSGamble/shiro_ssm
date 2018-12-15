package top.qingrang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qingrang.dao.IUserDao;
import top.qingrang.pojo.User;
import top.qingrang.service.IUserService;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserDao userDao;

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	@Override
	public List<User> listUser() {
		return userDao.selectUserList();
	}

	@Override
	public void addUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public void deleteUser(long id) {
		userDao.deleteUser(id);
	}

	@Override
	public User getUserByID(long id) {
		return userDao.getUserByID(id);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
}
