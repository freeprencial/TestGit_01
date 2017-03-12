package com.eduask.service.impl;

import java.util.List;
import java.util.Set;

import com.eduask.bean.Department;
import com.eduask.bean.User;
import com.eduask.dao.IUserDao;
import com.eduask.dao.impl.UserDaoImpl;
import com.eduask.form.LoginForm;
import com.eduask.service.IUserService;
import com.eduask.util.PageUtil;

public class UserServiceImpl implements IUserService {
	
	private IUserDao<User> userDao =  new UserDaoImpl();

	@Override
	public User loginUser(LoginForm loginForm) {
		// TODO Auto-generated method stub
		return userDao.loginUser(loginForm);
	}

	@Override
	public Integer checkAccount(String account) {
		// TODO Auto-generated method stub
		return userDao.checkAccount(account);
	}

	@Override
	public List<User> findAll(User user,PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return userDao.findAll(user,pageUtil);
	}

	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		
		return userDao.save(user);
	}

	@Override
	public boolean enableUser(String id) {
		// TODO Auto-generated method stub
		return userDao.enableUser(Long.valueOf(id));
	}

	@Override
	public boolean disableUser(String id) {
		// TODO Auto-generated method stub
		return userDao.disableUser(Long.valueOf(id));
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return userDao.delete(Long.valueOf(id));
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return userDao.findById(Long.valueOf(id));
	}

	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return userDao.edit(user);
	}

	@Override
	public void initpwd(String uid) {
		// TODO Auto-generated method stub
		userDao.initpwd(Long.valueOf(uid));
	}

	@Override
	public Long getTotalCount(User user) {
		// TODO Auto-generated method stub
		return userDao.getTotalCount(user);
	}

	@Override
	public boolean deleteByIds(Set<Long> sessionSet) {
		// TODO Auto-generated method stub
		System.out.println("sessionSet.size()=="+sessionSet);
		if(sessionSet!=null&&sessionSet.size()>0){
			return userDao.deleteByIds(sessionSet);
		}else{
			return true;
		}
		
	}

	@Override
	public boolean initpwdByIds(Set<Long> sessionSet) {
		// TODO Auto-generated method stub
		
		if(sessionSet!=null&&sessionSet.size()>0){
			return userDao.initpwdByIds(sessionSet);
		}else{
			return true;
		}
	}

	@Override
	public List<Department> findDepartmentList() {
		// TODO Auto-generated method stub
		return userDao.findDepartmentList();
	}

	@Override
	public void updateRoleIdIsNull(List<Long> uids) {
		// TODO Auto-generated method stub
		userDao.updateRoleIdIsNull(uids);
	}

}
