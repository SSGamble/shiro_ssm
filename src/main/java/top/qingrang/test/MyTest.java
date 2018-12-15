package top.qingrang.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.qingrang.service.IRoleService;
import top.qingrang.service.IUserService;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyTest {

	@Autowired
	IRoleService roleService;

	@Autowired
	IUserService userService;

	@Test
	public void Test() {
		String name = "zhang3";
		Set<String> roles = roleService.listRoleName(name);
		System.out.println(roles);

	}
}
