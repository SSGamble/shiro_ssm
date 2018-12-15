package top.qingrang.controller;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.qingrang.pojo.Role;
import top.qingrang.pojo.User;
import top.qingrang.service.IRoleService;
import top.qingrang.service.IUserRoleService;
import top.qingrang.service.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理：用户
 */
@Controller
@RequestMapping("admin")
public class UserController {

	@Autowired
	IUserRoleService userRoleService;
	@Autowired
	IUserService userService;
	@Autowired
	IRoleService roleService;

	/**
	 * 用户列表
	 */
	@RequestMapping("listUser")
	public String list(Model model) {
		List<User> listUser = userService.listUser();
		model.addAttribute("listUser", listUser);

		// 一个用户对应一堆角色
		Map<User, List<Role>> user_roles = new HashMap<>();
		for (User user : listUser) {
			List<Role> roles = roleService.listRoleByUser(user);
			user_roles.put(user, roles);
		}
		model.addAttribute("user_roles", user_roles);

		return "listUser";
	}

	/**
	 * 添加用户
	 */
	@RequestMapping("addUser")
	public String add(Model model, String name, String password) {
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();	// 盐
		int times = 2;	// 加盐次数
		String algorithmName = "md5";	// MD5 加密
		// 加密 密码
		String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
		// 添加用户
		User user = new User();
		user.setName(name);
		user.setPassword(encodedPassword);
		user.setSalt(salt);
		userService.addUser(user);

		return "redirect:listUser";
	}

	/**
	 * 更改用户，页面
	 */
	@RequestMapping("editUser")
	public String edit(Model model, long id) {
		List<Role> listRole = roleService.listRole();
		model.addAttribute("listRole", listRole);
		User user = userService.getUserByID(id);
		model.addAttribute("user", user);

		List<Role> roles = roleService.listRoleByUser(user);
		model.addAttribute("currentRoles", roles);

		return "editUser";
	}

	/**
	 * 更改用户，实现
	 */
	@RequestMapping("updateUser")
	public String update(User user, long[] roleIds) {
		// 修改 用户角色 表
		userRoleService.editUserRole(user, roleIds);

		// 修改密码
		String password = user.getPassword();
		// 如果在修改的时候没有设置密码，就表示不改动密码
		if (user.getPassword().length() != 0) {
			String salt = new SecureRandomNumberGenerator().nextBytes().toString();
			int times = 2;
			String algorithmName = "md5";
			String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
			user.setSalt(salt);
			user.setPassword(encodedPassword);
		} else{
			User u = userService.getUserByID(user.getId());
			user.setPassword(u.getPassword());
			user.setSalt(u.getSalt());
		}

		// 修改用户信息
		userService.updateUser(user);

		return "redirect:listUser";
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("deleteUser")
	public String delete(long id) {
		userService.deleteUser(id);
		return "redirect:listUser";
	}
}