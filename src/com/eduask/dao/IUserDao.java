package com.eduask.dao;

import java.util.List;
import java.util.Set;

import com.eduask.bean.Department;
import com.eduask.bean.User;
import com.eduask.form.LoginForm;
import com.eduask.util.PageUtil;

public interface IUserDao<User> extends IBaseDao<User> {

	public User loginUser(LoginForm loginForm);

	public Integer checkAccount(String account);

	public boolean enableUser(Long valueOf);

	public boolean disableUser(Long valueOf);

	public void initpwd(Long uid);

	public Long getTotalCount(com.eduask.bean.User user);

	public boolean deleteByIds(Set<Long> sessionSet);

	public boolean initpwdByIds(Set<Long> sessionSet);

	public List<Department> findDepartmentList();
	
	public List<User> findAll(User t, PageUtil pageUtil);
	
	public User findById(Long id);
	
	public boolean save(User t);
	
	public boolean edit(User t);
	
	public boolean delete(Long id);
	
	public List<User> findByIds(Long[] ids);

	public void updateRoleIdIsNull(List<Long> uids);

}
