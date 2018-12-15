package top.qingrang.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import top.qingrang.pojo.User;
import top.qingrang.service.IPermissionService;
import top.qingrang.service.IRoleService;
import top.qingrang.service.IUserService;
import java.util.Set;

/**
 * 通过数据库，验证用户，和相关授权的类
 * DatabaseRealm 类，由 Shiro 调用，在 applicationContext-shiro.xml 中配置。
 */
public class DatabaseRealm extends AuthorizingRealm {

	@Autowired
	IUserService userService;
	@Autowired
	IPermissionService permissionService;
	@Autowired
	IRoleService roleService;

	/**
	 * 验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// 获取输入用户的账号和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		String name = upToken.getPrincipal().toString();

		// 获取指定用户数据库中的密码，盐
		User user = userService.getUserByName(name);
		String pwdInDB = user.getPassword();
		String salt = user.getSalt();

		// 认证信息，存放账号，密码，盐，getName() 是当前 Realm 的继承方法,通常返回当前类名 :databaseRealm
		// 通过 applicationContext-shiro.xml 里配置的 HashedCredentialsMatcher 进行自动校验
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				name,pwdInDB, ByteSource.Util.bytes(salt),getName());
		return authenticationInfo;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 能进入到这里，表示账号已经通过验证了
		String name = (String) principalCollection.getPrimaryPrincipal();
		// 获取账号的角色和权限信息
		Set<String> roles = roleService.listRoleName(name);
		Set<String> permissions = permissionService.listPermissionName(name);
		// 授权对象信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setStringPermissions(permissions);
		authorizationInfo.setRoles(roles);
		return authorizationInfo;
	}
}