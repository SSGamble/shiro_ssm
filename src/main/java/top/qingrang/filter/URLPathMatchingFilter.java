package top.qingrang.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.qingrang.service.IPermissionService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * PathMatchingFilter 是 shiro 内置过滤器
 * 基本思路如下：
 * 1. 如果没登录就跳转到登录
 * 2. 如果当前访问路径没有在权限系统里维护，则允许访问
 * 3. 当前用户所拥有的权限如何不包含当前的访问地址，则跳转到/unauthorized，否则就允许访问
 */
public class URLPathMatchingFilter extends PathMatchingFilter {

	@Autowired
	IPermissionService permissionService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 当前请求的 URL
		String requestURI = getPathWithinApplication(request);
		// 获取 subject 对象
		Subject subject = SecurityUtils.getSubject();
		// 如果没有登录，就跳转到登录页面
		if (!subject.isAuthenticated()) {
			WebUtils.issueRedirect(request, response, "/login");
			return false;
		}
		// 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
		boolean needInterceptor = permissionService.needInterceptor(requestURI);
		if (needInterceptor) {	// 需要拦截
			boolean hasPermission = false;
			String userName = subject.getPrincipal().toString();
			Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
			for (String url : permissionUrls) {
				// 这就表示当前用户有这个权限
				if (url.equals(requestURI)) {
					hasPermission = true;
					break;
				}
			}
			if (hasPermission){
				return true;
			}else {
				UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
				subject.getSession().setAttribute("ex", ex);
				WebUtils.issueRedirect(request, response, "/unauthorized");
				return false;
			}
		} else {
			return true;	// 不需要拦截
		}
	}
}