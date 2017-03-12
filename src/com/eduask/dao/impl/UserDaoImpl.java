package com.eduask.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.eduask.bean.Department;
import com.eduask.bean.User;
import com.eduask.dao.BaseDao;
import com.eduask.dao.IUserDao;
import com.eduask.form.LoginForm;
import com.eduask.util.MD5Utils;
import com.eduask.util.PageUtil;

public class UserDaoImpl extends BaseDao implements IUserDao<User> {

	@Override
	public User loginUser(LoginForm loginForm) {
		
		User user = null;
		getConnection();
		
		String sql = "select user.uid,username,headImg,lastLoginTime,lastLoginIp,user.roleid,rolename from user,role "
				+ "where user.roleid = role.roleid and account=? and pwd=? and enableid=1";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, loginForm.getAccount());
			
			ps.setString(2, MD5Utils.md5(loginForm.getPwd()));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				user = new User();
				
				user.setUid(rs.getLong(1));
				
				user.setUsername(rs.getString(2));
				
				user.setHeadImg(rs.getString(3));
				
				user.setLastLoginTime(rs.getTimestamp(4));
				
				user.setLastLoginIp(rs.getString(5));
				
				user.getRole().setRoleid(rs.getLong(6));
				
				user.getRole().setRolename(rs.getString(7));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		return user;
	}

	@Override
	public Integer checkAccount(String account) {
		// TODO Auto-generated method stub
		Integer disabled = 0;
		
		getConnection();
		
		String sql = "select enableid from user where account=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				disabled = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
		}
		
		return disabled;
	}

	@Override
	public List<User> findAll(User user1,PageUtil pageUtil) {
		
		List<User> userlist = new ArrayList<User>();
		getConnection();
		
		String sql = "select * from (select tab.uid,account,username,gender,phone,rolename,enableid,enablename,departid from (select user.uid,account,username,gender,phone,roleid,user.enableid,enablename,user.departid from user,accountenable,department where user.enableid=accountenable.enableid and department.id = user.departid) tab left outer join role on tab.roleid=role.roleid) tab1 where 1=1 ";
		
		if(user1.getAccount()!=null&&!"".equals(user1.getAccount())){
			
			sql += "and tab1.account like ? ";
		}
		
		if(user1.getUsername()!=null&&!"".equals(user1.getUsername())){
			
			sql += "and tab1.username like ? ";
		}
		
		if(user1.getDepartment().getId()!=null){
			
			sql += "and tab1.departid=? ";
		}
		
		if(user1.getGender()!=null){
			sql += "and tab1.gender=? ";
			
		}
		
		if(user1.getAccountEnable().getEnableId()!=null&&user1.getAccountEnable().getEnableId()!=0){
			
			sql += "and tab1.enableid=? ";
		}
		
		System.out.println("listsql="+sql);
			
				sql += " limit "+(pageUtil.getPageIndex()-1)*pageUtil.getPageNum()+","+pageUtil.getPageNum();
	
		try {
			ps = conn.prepareStatement(sql);
			
			int index = 1;
			
			if(user1.getAccount()!=null&&!"".equals(user1.getAccount())){
				
				ps.setString(index, "%"+user1.getAccount()+"%");
				
				index++;
			}
			
			if(user1.getUsername()!=null&&!"".equals(user1.getUsername())){
				
				ps.setString(index, "%"+user1.getUsername()+"%");
				
				index++;
			}
			
			if(user1.getDepartment().getId()!=null){
				
				ps.setLong(index, user1.getDepartment().getId());
				
				index++;
			}
			
			if(user1.getGender()!=null){
				
				ps.setString(index, user1.getGender());
				
				index++;
				
			}
			
			if(user1.getAccountEnable().getEnableId()!=null&&user1.getAccountEnable().getEnableId()!=0){
				
				ps.setLong(index, user1.getAccountEnable().getEnableId());
				
				index++;
			}
			
			rs = ps.executeQuery();
			
			for(;rs.next();){
				
				User user = new User();
				
				user.setUid(rs.getLong(1));
				
				user.setAccount(rs.getString(2));
				
				user.setUsername(rs.getString(3));
				
				user.setGender(rs.getString(4));
				
				user.setPhone(rs.getString(5));
				
				user.getRole().setRolename(rs.getString(6));
				
				user.getAccountEnable().setEnableId(rs.getLong(7));
				
				user.getAccountEnable().setEnableName(rs.getString(8));
				
				userlist.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
		
		return userlist;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		User user = null;
		
		getConnection();
		
		String sql = "select uid,account,username,gender,phone,headimg,roleid from user where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				user = new User();
				
				user.setUid(rs.getLong(1));
				
				user.setAccount(rs.getString(2));
				
				user.setUsername(rs.getString(3));
				
				user.setGender(rs.getString(4));
				
				user.setPhone(rs.getString(5));
				
				user.setHeadImg(rs.getString(6));
				
				user.getRole().setRoleid(rs.getLong(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		return user;
	}

	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		boolean flag = false;
		getConnection();
		
		String sql = "insert into user(account,pwd,gender,username,phone,headimg,enableid,roleid) values(?,'"+MD5Utils.md5("1234")+"',?,?,?,null,1,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			
			int index = 1;
			
			ps.setString(index, user.getAccount());
			
			index++;
			
			ps.setString(index, user.getGender());
			
			index++;
			
			ps.setString(index, user.getUsername());
			
			index++;
			
			ps.setString(index, user.getPhone());
			
			index++;
			
			if(user.getRole().getRoleid()!=null){
				
				ps.setLong(index, user.getRole().getRoleid());
				
			}else{
				//填充空值
				ps.setObject(index, null);
			}
			
			int num = ps.executeUpdate();
			
			if(num>0){
				
				flag = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
		}
		return flag;
	}

	@Override
	public boolean edit(User user) {
		// TODO Auto-generated method stub
		boolean flag = false;
		getConnection();
		
		String sql = "update user set account=?,gender=?,username=?,phone=?,headimg=?,roleid=? where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			int index = 1;
			
			ps.setString(index, user.getAccount());
			
			index++;
			
			ps.setString(index, user.getGender());
			
			index++;
			
			ps.setString(index, user.getUsername());
			
			index++;
			
			ps.setString(index, user.getPhone());
			
			index++;
			
			ps.setString(index, null);//图片
			
			index++;
			
			if(user.getRole().getRoleid()!=null){
				
				ps.setLong(index, user.getRole().getRoleid());
				
				
			}else{
				//填充空值
				ps.setObject(index, null);
			}
			
			index++;
			
			ps.setLong(index, user.getUid());
			
			int num = ps.executeUpdate();
			
			if(num>0){
				
				flag = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
		}
		return flag;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		getConnection();
		
		String sql = "delete from user where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, id);
			
			int num = ps.executeUpdate();
			
			if(num>0){
				
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
		return flag;
	}

	@Override
	public List<User> findByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean enableUser(Long id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		getConnection();
		
		String sql = "update user set enableid=1 where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			
			int num = ps.executeUpdate();
			
			if(num>0){
				
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
		return flag;
	}

	@Override
	public boolean disableUser(Long id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		getConnection();
		
		String sql = "update user set enableid=-1 where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			
			int num = ps.executeUpdate();
			
			if(num>0){
				
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
		return flag;
	}

	@Override
	public void initpwd(Long uid) {
		// TODO Auto-generated method stub
		getConnection();
		
		String sql = "update user set pwd='1234' where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, uid);
			
			int num = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
	}

	@Override
	public Long getTotalCount(User user1) {
		// TODO Auto-generated method stub
		Long totalCount = 0L;
		
		getConnection();
		
		String sql = "select count(*) from (select tab.uid,account,username,gender,phone,enableid,enablename,tab.id ,tab.roleid from (select user.uid,account,username,gender,phone,roleid,user.enableid,enablename,department.id from user,accountenable,department where user.enableid=accountenable.enableid and department.id = user.departid) tab left outer join role on tab.roleid=role.roleid) tab1 where 1=1 ";
		
		if(user1.getAccount()!=null&&!"".equals(user1.getAccount())){
			
			sql += "and tab1.account like ? ";
		}
		
		if(user1.getUsername()!=null&&!"".equals(user1.getUsername())){
			
			sql += "and tab1.username like ? ";
		}
		
		if(user1.getDepartment().getId()!=null){
			
			sql += "and tab1.id=? ";
		}
		
		if(user1.getGender()!=null){
			sql += "and tab1.gender=? ";
			
		}
		
		if(user1.getAccountEnable().getEnableId()!=null&&user1.getAccountEnable().getEnableId()!=0){
			
			sql += "and tab1.enableid=? ";
		}
		
		try {
			ps = conn.prepareStatement(sql);
			
			int index = 1;
			
			if(user1.getAccount()!=null&&!"".equals(user1.getAccount())){
				
				ps.setString(index, "%"+user1.getAccount()+"%");
				
				index++;
			}
			
			if(user1.getUsername()!=null&&!"".equals(user1.getUsername())){
				
				ps.setString(index, "%"+user1.getUsername()+"%");
				
				index++;
			}
			
			if(user1.getDepartment().getId()!=null){
				
				ps.setLong(index, user1.getDepartment().getId());
				
				index++;
			}
			
			if(user1.getGender()!=null){
				
				ps.setString(index, user1.getGender());
				
				index++;
				
			}
			
			if(user1.getAccountEnable().getEnableId()!=null&&user1.getAccountEnable().getEnableId()!=0){
				
				ps.setLong(index, user1.getAccountEnable().getEnableId());
				
				index++;
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				totalCount = rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		return totalCount;
	}

	@Override
	public boolean deleteByIds(Set<Long> sessionSet) {
		// TODO Auto-generated method stub
		boolean flag = false;
		getConnection();
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "delete from user where uid in (";
		
		for(int i=0;i<sessionSet.size();i++){
			
			if(i==sessionSet.size()-1){
				
				sql += "?";
			}else{
				
				sql += "?,";
			}

		}
		
		sql += ")";
		
		try {
			ps = conn.prepareStatement(sql);
			
			int i = 0;
			for(Long uid:sessionSet){

				ps.setLong((i+1), uid);
				
				i++;
			}
			
			
			int nums = ps.executeUpdate();
			
			
			if(nums>0){
				
				flag = true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		return flag;
	}

	@Override
	public boolean initpwdByIds(Set<Long> sessionSet) {
		boolean flag = false;
		getConnection();
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "update user set pwd='1234' where uid in (";
		
		
		int i = 0;
		for(Long uid:sessionSet){

				if(i==sessionSet.size()-1){
				
				sql += "?";
			}else{
				
				sql += "?,";
			}
				
				i++;
		}
		
		sql += ")";
		
		System.out.println("sql=="+sql);
		
		try {
			ps = conn.prepareStatement(sql);
			
			int j = 0;
			for(Long uid:sessionSet){
				
				
				ps.setLong((j+1), uid);
				
				j++;
			}
			
			
			int nums = ps.executeUpdate();
			
			//手动提交数据修改
			conn.commit();
			
			
			if(nums>0){
				
				flag = true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		return flag;
	}

	@Override
	public List<Department> findDepartmentList() {
		// TODO Auto-generated method stub
		List<Department> departmentlist = new ArrayList<Department>();
		getConnection();
		String sql = "select id,name from department";
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				Department department = new Department();
				
				department.setId(rs.getLong(1));
				
				department.setName(rs.getString(2));
				
				departmentlist.add(department);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
		}
		
	
		return departmentlist;
	}

	@Override
	public void updateRoleIdIsNull(List<Long> uids) {
		// TODO Auto-generated method stub
		getConnection();
		
		String sql = "update user set roleid=null where uid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			for(int i=1;i<uids.size();i++){
				
				ps.setLong(1, uids.get(i));
				ps.addBatch();
			}

			ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
	}

}
