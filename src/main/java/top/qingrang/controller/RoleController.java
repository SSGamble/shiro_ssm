package top.qingrang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.qingrang.pojo.Permission;
import top.qingrang.pojo.Role;
import top.qingrang.service.IPermissionService;
import top.qingrang.service.IRolePermissionService;
import top.qingrang.service.IRoleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理：角色
 */
@Controller
@RequestMapping("admin")
public class RoleController {

	@Autowired
	IRoleService roleService;
	@Autowired
	IRolePermissionService rolePermissionService;
	@Autowired
	IPermissionService permissionService;

	@RequestMapping("listRole")
	public String list(Model model) {
		List<Role> rs = roleService.listRole();
		model.addAttribute("rs", rs);

		Map<Role, List<Permission>> role_permissions = new HashMap<>();

		for (Role role : rs) {
			List<Permission> ps = permissionService.getPermissionListByRole(role);
			role_permissions.put(role, ps);
		}
		model.addAttribute("role_permissions", role_permissions);

		return "listRole";
	}

	@RequestMapping("addRole")
	public String list(Role role) {
		System.out.println(role.getName());
		System.out.println(role.getDesc_());
		roleService.addRole(role);
		return "redirect:listRole";
	}

	@RequestMapping("editRole")
	public String list(Model model, long id) {
		Role role = roleService.getRole(id);
		model.addAttribute("role", role);

		List<Permission> ps = permissionService.getPermissionList();
		model.addAttribute("ps", ps);

		List<Permission> currentPermissions = permissionService.getPermissionListByRole(role);
		model.addAttribute("currentPermissions", currentPermissions);

		return "editRole";
	}

	@RequestMapping("updateRole")
	public String update(Role role, long[] permissionIds) {
		rolePermissionService.updateRolePermission(role, permissionIds);
		roleService.updateRole(role);
		return "redirect:listRole";
	}

	@RequestMapping("deleteRole")
	public String delete(long id) {
		roleService.deleteRoleByID(id);
		return "redirect:listRole";
	}

}