package com.eduask.bean;

import java.util.ArrayList;
import java.util.List;

public class Role {
	
	private Long roleid;
	
	private String rolename;
	
	private List<User> userlist = new ArrayList<User>();
	
	private List<PurView> purviewList = new ArrayList<>();

	private User createUser;
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	
	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<PurView> getPurviewList() {
		return purviewList;
	}

	public void setPurviewList(List<PurView> purviewList) {
		this.purviewList = purviewList;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", rolename=" + rolename + "]";
	}
	
	

}
