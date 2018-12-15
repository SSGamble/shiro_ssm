package top.qingrang.service;

import top.qingrang.pojo.User;

import java.util.List;

public interface IUserService {
	User getUserByName(String name);
	List<User> listUser();
	void addUser(User user);
	void deleteUser(long id);
	User getUserByID(long id);
	void updateUser(User user);
}
