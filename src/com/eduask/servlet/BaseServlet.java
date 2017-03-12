package com.eduask.servlet;

import javax.servlet.http.HttpServlet;

import com.eduask.service.IRoleService;
import com.eduask.service.IUserService;
import com.eduask.service.impl.RoleServiceImpl;
import com.eduask.service.impl.UserServiceImpl;

public class BaseServlet extends HttpServlet {
	
	protected IUserService userService = new UserServiceImpl();
	
	protected IRoleService roleService = new RoleServiceImpl();
	

}
