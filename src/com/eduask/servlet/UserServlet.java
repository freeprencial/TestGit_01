package com.eduask.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import com.eduask.bean.Department;
import com.eduask.bean.Role;
import com.eduask.bean.User;
import com.eduask.form.LoginForm;
import com.eduask.service.IUserService;
import com.eduask.service.impl.UserServiceImpl;
import com.eduask.util.PageUtil;
import com.eduask.util.ReturnResult;
@WebServlet("/user/UserServlet")
public class UserServlet extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");

		Class cla = this.getClass();

		try {
			Method method1 = cla.getDeclaredMethod(method, HttpServletRequest.class,HttpServletResponse.class);

			method1.invoke(this, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}


	public void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account = request.getParameter("account");

		String pwd = request.getParameter("pwd");

		String rempwd = request.getParameter("rempwd");

		LoginForm loginForm = new LoginForm();

		loginForm.setAccount(account);

		loginForm.setPwd(pwd);
		
		System.out.println(111);

		if(Boolean.parseBoolean(rempwd)){

			Cookie cookie1 = new Cookie("account", account);

			Cookie cookie2 = new Cookie("pwd",pwd);

			cookie1.setMaxAge(Integer.MAX_VALUE);

			cookie2.setMaxAge(Integer.MAX_VALUE);

			cookie1.setPath("/");

			cookie2.setPath("/");

			response.addCookie(cookie1);

			response.addCookie(cookie2);
		}

		User user = userService.loginUser(loginForm);
		System.out.println(user);

		if(user==null&&!Boolean.parseBoolean(rempwd)){

			//登陆失败
			request.setAttribute("loginForm", loginForm);

			request.getRequestDispatcher("../login.jsp").forward(request, response);
		}else{
			
			request.getSession().setAttribute("loginUser",user);
			response.sendRedirect("../role/RoleServlet?method=list");

		}
	}

	public void checkAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String account = request.getParameter("account");
		String status = null;
		Integer disabled = userService.checkAccount(account);
		response.setContentType("text/html;charset=utf-8");
		//如果账户正常，那么返回1，如果账户被禁用，那么返回-1，如果账户不存在，返回0
		PrintWriter pw = response.getWriter();
		ReturnResult result = new ReturnResult();
		if(disabled==1){//正常
			result.setStatusId(disabled);
			result.setMsg("当前用户允许登录");
		}else if(disabled==-1){
			result.setStatusId(disabled);
			result.setMsg("当前用户帐号异常，请联系管理员");
		}else{
			result.setStatusId(0);
			result.setMsg("账户不存在，是否<a href=\"register.jsp?account="+account+"\">立即注册</a>");
		}
		try {
			pw.write(result.toJson());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();

		pw.close();
	}

	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String clear = request.getParameter("clear");
		
		if("true".equals(clear)){
			
			request.getSession().removeAttribute("account");
			request.getSession().removeAttribute("username");
			request.getSession().removeAttribute("department");
			request.getSession().removeAttribute("gender");
			request.getSession().removeAttribute("disabled");
		}

		String pageIndex = request.getParameter("pageIndex");
		
		//获取查询表单中的参数
		String account = request.getParameter("account");
		
		System.out.println(account);
		
		String username = request.getParameter("username");
		
		String department = request.getParameter("department");
		
		String sex = request.getParameter("sex");
		
		String disabled = request.getParameter("disabled");
		
		System.out.println(disabled);
		
		if(account!=null){
			
			request.getSession().setAttribute("account", account);
		}
		
		if(username!=null){
			
			request.getSession().setAttribute("username", username);
		}
		
		if(department!=null){
			
			request.getSession().setAttribute("department", department);
		}
		
		if(sex!=null){
			
			request.getSession().setAttribute("gender", sex);
		}
		
		if(disabled!=null){
			
			request.getSession().setAttribute("disabled", disabled);
		}
		
		User user = new User();
		
		if(request.getSession().getAttribute("account")!=null){
			
			user.setAccount(request.getSession().getAttribute("account").toString());
		}
		
		if(request.getSession().getAttribute("username")!=null){
			
			user.setUsername(request.getSession().getAttribute("username").toString());
		}
		
		if(request.getSession().getAttribute("department")!=null&&!"".equals(request.getSession().getAttribute("department"))){
			
			user.getDepartment().setId(Long.valueOf(request.getSession().getAttribute("department").toString()));
		}
		
		if(request.getSession().getAttribute("gender")!=null&&!"".equals(request.getSession().getAttribute("gender"))){
			
			user.setGender(request.getSession().getAttribute("gender").toString());
		}
		
		if(request.getSession().getAttribute("disabled")!=null){
			
			user.getAccountEnable().setEnableId(Long.valueOf(request.getSession().getAttribute("disabled").toString()));
		}
		

		PageUtil pageUtil = new PageUtil();

		pageUtil.setPageIndex(Long.valueOf(pageIndex));

		Long totalCount = userService.getTotalCount(user);

		pageUtil.setTotalCount(totalCount);

		List<User> userlist = userService.findAll(user, pageUtil);
		
		List<Department> departments = userService.findDepartmentList();
		
		request.setAttribute("departments", departments);

		request.setAttribute("userlist", userlist);

		request.setAttribute("pageUtil", pageUtil);

		request.getRequestDispatcher("../WEB-INF/user/list.jsp").forward(request, response);
	}

	public void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		//查询角色列表
		List<Role> rolelist = roleService.findAll();

		request.setAttribute("rolelist", rolelist);

		request.getRequestDispatcher("../WEB-INF/user/saveUI.jsp").forward(request, response);
	}

	private void editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//查询要修改的用户
		String id = request.getParameter("id");

		User user = userService.findById(id);

		request.setAttribute("editUser", user);

		//查询角色列表
		List<Role> rolelist = roleService.findAll();

		request.setAttribute("rolelist", rolelist);

		request.getRequestDispatcher("../WEB-INF/user/saveUI.jsp").forward(request, response);
	}

	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		String account = request.getParameter("account");

		String username = request.getParameter("username");

		String gender = request.getParameter("gender");

		String phone = request.getParameter("phone");

		//String headImg

		String roleid = request.getParameter("roleid");

		User user = new User();

		user.setAccount(account);

		user.setUsername(username);

		user.setGender(gender);

		user.setPhone(phone);

		if(roleid==null||"".equals(roleid)){

			user.getRole().setRoleid(null);
		}else{

			user.getRole().setRoleid(Long.valueOf(roleid));
		}

		boolean flag = userService.save(user);

		if(flag){

			response.sendRedirect("UserServlet?method=list&pageIndex=1");
		}else{

			request.getRequestDispatcher("UserServlet?method=saveUI").forward(request, response);
		}


	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		String uid = request.getParameter("uid");

		String account = request.getParameter("account");

		String username = request.getParameter("username");

		String gender = request.getParameter("gender");

		String phone = request.getParameter("phone");

		//String headImg

		String roleid = request.getParameter("roleid");

		User user = new User();

		user.setUid(Long.valueOf(uid));

		user.setAccount(account);

		user.setUsername(username);

		user.setGender(gender);

		user.setPhone(phone);

		if(roleid==null||"".equals(roleid)){

			user.getRole().setRoleid(null);
		}else{

			user.getRole().setRoleid(Long.valueOf(roleid));
		}

		boolean flag = userService.update(user);

		if(flag){

			response.sendRedirect("UserServlet?method=list&pageIndex=1");
		}else{

			request.getRequestDispatcher("UserServlet?method=editUI&id="+uid).forward(request, response);
		}
	}

	private void enableUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");

		boolean flag = userService.enableUser(id);

		response.sendRedirect("UserServlet?method=list&pageIndex=1");

	}

	private void disableUser(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String id = request.getParameter("id");

		boolean flag = userService.disableUser(id);

		response.sendRedirect("UserServlet?method=list&pageIndex=1");
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String id = request.getParameter("id");

		boolean flag = userService.delete(id);

		response.sendRedirect("UserServlet?method=list&pageIndex=1");
	}

	private void initpwd(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String uid = request.getParameter("id");

		userService.initpwd(uid);

		response.sendRedirect("UserServlet?method=list&pageIndex=1");
	}

	private void deleteUids(HttpServletRequest request, HttpServletResponse response) throws IOException{

		//获取复选框多个选中选项
		String[] uids2 = request.getParameterValues("uid");
		
		Long[] uids1 = new Long[uids2.length];
		
		for(int i=0;i<uids1.length;i++){
			
			uids1[i] = Long.valueOf(uids2[i]);
		}

		//获取session中保存的要操作的uid数组，和当前页选中的数组合并为一个新数组
		Set<Long> sessionSet =  (Set<Long>) request.getSession().getAttribute("uidSet1");
		
		if(sessionSet!=null){
			
			sessionSet.addAll(Arrays.asList(uids1));

		}else{

			sessionSet = new HashSet<Long>(Arrays.asList(uids1));
		}

		boolean flag = userService.deleteByIds(sessionSet);
		
		request.getSession().removeAttribute("uidSet1");

		response.sendRedirect("UserServlet?method=list&pageIndex=1");
	}

	private void initpwdUids(HttpServletRequest request, HttpServletResponse response) throws IOException{

		//获取复选框多个选中选项
		String[] uids2 = request.getParameterValues("uid");
		Long[] uids1 = new Long[uids2.length];
		
		for(int i=0;i<uids1.length;i++){
			
			uids1[i] = Long.valueOf(uids2[i]);
		}
		//获取session中保存的要操作的uid数组，和当前页选中的数组合并为一个新数组
		Set<Long> sessionSet =  (Set<Long>) request.getSession().getAttribute("uidSet");
		
		if(sessionSet!=null){
			
			sessionSet.addAll(Arrays.asList(uids1));

		}else{

			sessionSet = new HashSet<Long>(Arrays.asList(uids1));
		}


		boolean flag = userService.initpwdByIds(sessionSet);
		
		request.getSession().removeAttribute("uidSet");

		response.sendRedirect("UserServlet?method=list&pageIndex=1");
	}

	private void saveCheckedUid(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException{

		String uids = request.getParameter("uids");

		String[] uidArr = uids.split("-");
		
		Long[] uids1 = new Long[uidArr.length];
		
		for(int i=0;i<uids1.length;i++){
			
			uids1[i] = Long.valueOf(uidArr[i]);
		}
		
		List<Long> uidParaList = Arrays.asList(uids1);
		
		if(request.getSession().getAttribute("uidSet")!=null){
			Set<Long> sessionSet = (Set<Long>) request.getSession().getAttribute("uidSet");
			
			sessionSet.addAll(uidParaList);

			request.getSession().setAttribute("uidSet", sessionSet);

		}else{

			request.getSession().setAttribute("uidSet", new HashSet<Long>(uidParaList));

		}

		PrintWriter pw = response.getWriter();

		ReturnResult returnRes = new ReturnResult();

		returnRes.setStatusId(1);

		pw.write(returnRes.toJson());

		pw.flush();

		pw.close();


	}

}
