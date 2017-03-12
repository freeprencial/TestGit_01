package com.eduask.service;

import java.util.List;
import java.util.Set;

import com.eduask.bean.Department;
import com.eduask.bean.User;
import com.eduask.form.LoginForm;
import com.eduask.util.PageUtil;

public interface IUserService {
	
	public User loginUser(LoginForm loginForm);

	public Integer checkAccount(String account);

	public List<User> findAll(User user, PageUtil pageUtil);

	public boolean save(User user);

	public boolean enableUser(String id);

	public boolean disableUser(String id);

	public boolean delete(String id);

	public User findById(String id);

	public boolean update(User user);

	public void initpwd(String uid);

	public Long getTotalCount(User user);

	public boolean deleteByIds(Set<Long> sessionSet);

	public boolean initpwdByIds(Set<Long> sessionSet);

	public List<Department> findDepartmentList();

	public void updateRoleIdIsNull(List<Long> uids);

}
