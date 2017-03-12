package com.eduask.bean;

import java.util.ArrayList;
import java.util.List;

public class PurView {
	
	private Integer purId;
	
	private String purname;
	
	private PurView parentPurview;
	
	private List<PurView> childrenPurviews = new ArrayList<PurView>();
	
	private List<Role> rolelist = new ArrayList<Role>();

	public Integer getPurId() {
		return purId;
	}

	public void setPurId(Integer purId) {
		this.purId = purId;
	}

	public String getPurname() {
		return purname;
	}

	public void setPurname(String purname) {
		this.purname = purname;
	}

	public PurView getParentPurview() {
		return parentPurview;
	}

	public void setParentPurview(PurView parentPurview) {
		this.parentPurview = parentPurview;
	}

	public List<PurView> getChildrenPurviews() {
		return childrenPurviews;
	}

	public void setChildrenPurviews(List<PurView> childrenPurviews) {
		this.childrenPurviews = childrenPurviews;
	}

	public List<Role> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<Role> rolelist) {
		this.rolelist = rolelist;
	}

	@Override
	public String toString() {
		return "PurView [purId=" + purId + ", purname=" + purname + "]";
	}


}
