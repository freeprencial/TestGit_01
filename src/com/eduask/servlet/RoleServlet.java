package com.eduask.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eduask.bean.PurView;
import com.eduask.bean.Role;
import com.eduask.bean.User;
import com.eduask.util.ReturnResult;

@WebServlet("/role/RoleServlet")
public class RoleServlet extends BaseServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		
		try {
			Method method1 = this.getClass().getDeclaredMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			
			method1.invoke(this, request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		List<Role> rolelist = roleService.findAll();
		
		request.setAttribute("rolelist", rolelist);
		
		request.getRequestDispatcher("../WEB-INF/role/list.jsp").forward(request, response);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String roleid = request.getParameter("roleid");
		
		List<Long> uids = roleService.findUserByRoleId(roleid);//根据角色查询所有的用户id
		
		userService.updateRoleIdIsNull(uids);//根据用户id置空角色
		
		roleService.deleteFormRole_Purview(Long.valueOf(roleid));
		
		roleService.deleteFormRole(Long.valueOf(roleid));
		
		response.sendRedirect("RoleServlet?method=list");
	}
	
	private void editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String roleid = request.getParameter("roleid");
		
		String type = request.getParameter("type");
		
		System.out.println(type);
		
		List<PurView> purviewlist = roleService.findPurview();
		
		Role role = null;
		
		if("see".equals(type)||"edit".equals(type)){
			
			role = roleService.getById(Long.valueOf(roleid));//关联权限表查询
			
			
			System.out.println(role);
			List<PurView> toppurviewlist = role.getPurviewList();
			
			for(int i=0;i<toppurviewlist.size();i++){
				
				System.out.println(toppurviewlist.get(i));
				
				List<PurView> children = toppurviewlist.get(i).getChildrenPurviews();
				
				for(int j=0;j<children.size();j++){
					
					System.out.println(children.get(j));
				}
			}
			
			request.setAttribute("type", type);
		}
		
		
		if("add".equals(type)){
			
			
		}
		
		request.setAttribute("purviewlist", purviewlist);
		
		request.setAttribute("role", role);
		
		request.getRequestDispatcher("../WEB-INF/role/editUI.jsp").forward(request, response);
		
		
	}
	
	private void checkRoleName(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException{
		
		request.setCharacterEncoding("utf-8");
		
		String rolename = request.getParameter("rolename");
		
		Role role = roleService.findRoleByName(rolename);
		
		ReturnResult returnRes = new ReturnResult();
		
		if(role!=null){
			returnRes.setStatusId(0);
			returnRes.setMsg("角色名已存在");
			
		}else{
			returnRes.setStatusId(1);
			
			returnRes.setMsg("角色名可用");
			
			
		}
		
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		
		pw.write(returnRes.toJson());
		
		pw.flush();
		
		pw.close();
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("utf-8");
		
		String rolename = request.getParameter("rolename");
		
		rolename = new String(rolename.getBytes("ISO8859-1"),"utf-8");
		
		String[] purids = request.getParameterValues("purid");
		
		Long uid = ((User)request.getSession().getAttribute("loginUser")).getUid();
		
		boolean flag = roleService.save(uid,rolename,purids);
		
		response.sendRedirect("RoleServlet?method=list");
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String roleid = request.getParameter("roleid");
		
		String[] purids = request.getParameterValues("purid");
		
		roleService.deleteRoleAndPurviewForeign(roleid);
		
		roleService.save(roleid,purids);
		
		response.sendRedirect("RoleServlet?method=list");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
