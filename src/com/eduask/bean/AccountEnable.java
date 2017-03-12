package com.eduask.bean;

import java.util.ArrayList;
import java.util.List;

public class AccountEnable {
	
	private Long enableId;
	
	private String enableName;
	
	private List<User> userlist = new ArrayList<User>();

	

	public Long getEnableId() {
		return enableId;
	}

	public void setEnableId(Long enableId) {
		this.enableId = enableId;
	}

	public String getEnableName() {
		return enableName;
	}

	public void setEnableName(String enableName) {
		this.enableName = enableName;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

}
