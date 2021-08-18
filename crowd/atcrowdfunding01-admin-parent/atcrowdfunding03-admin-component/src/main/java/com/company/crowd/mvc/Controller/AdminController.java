package com.company.crowd.mvc.Controller;

import javax.servlet.http.HttpSession;

import com.company.crowd.constants.CrowdConstant;
import com.company.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import com.company.crowd.entity.Admin;
import com.company.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@RequestMapping("/admin/update.html")
	public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {

		adminService.update(admin);

		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}

	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap
	) {

		// 1.根据adminId查询Admin对象
		Admin admin = adminService.getAdminById(adminId);

		// 2.将Admin对象存入模型
		modelMap.addAttribute("admin", admin);

		return "admin-edit";
	}

	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/save.html")
	public String save(Admin admin) {

		adminService.saveAdmin(admin);

		return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
	}

	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(
			@PathVariable("adminId") Integer adminId,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword
	) {

		// 执行删除
		adminService.remove(adminId);

		// 页面跳转：回到分页页面

		// 尝试方案1：直接转发到admin-page.jsp会无法显示分页数据
		// return "admin-page";

		// 尝试方案2：转发到/admin/get/page.html地址，一旦刷新页面会重复执行删除浪费性能
		// return "forward:/admin/get/page.html";

		// 尝试方案3：重定向到/admin/get/page.html地址
		// 同时为了保持原本所在的页面和查询关键词再附加pageNum和keyword两个请求参数
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}


	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(
				/*未解决并不是所有的参数右后都会设置的问题，引入默认值操作*/
				// 使用@RequestParam注解的defaultValue属性，指定默认值，在请求中没有携带对应参数时使用默认值
				// keyword默认值使用空字符串，和SQL语句配合实现两种情况适配
				@RequestParam(value="keyword", defaultValue="") String keyword,
				
				// pageNum默认值使用1
				@RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
				
				// pageSize默认值使用5
				@RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
				
				ModelMap modelMap
			
			) {
		
		// 调用Service方法获取PageInfo对象
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		
		// 将PageInfo对象存入模型
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		
		return "admin-page";
	}
	
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {
		
		// 强制Session失效:取消登录状态
		session.invalidate();
		
		return "redirect:/admin/to/login/page.html";
	}
	
	@RequestMapping("/admin/do/login.html")
	public String doLogin(
				@RequestParam("loginAcct") String loginAcct,
				@RequestParam("userPswd") String userPswd,
				HttpSession session
			) {

		// 调用Service方法执行登录检查
		// 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

		// 将登录成功返回的admin对象存入Session域
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

		return "redirect:/admin/to/main/page.html";
//		return "admin-main";//直接访问这个页面由于我们还处于提交表单的链接地址上面，刷新就会重新提交表单，这是一个IO操作消耗资源，重定向后跳出这个提交表单页面可以避免这个问题
	}

	@PreFilter(value="filterObject%2==0")//filterObject参数代表集合中的每一个值；该表达式就是保证偶数 PostMan:发送JSON。POST请求的软件
	@ResponseBody
	@RequestMapping("/admin/test/pre/filter")
	public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList) {
		return ResultEntity.successWithData(valueList);
	}
}
