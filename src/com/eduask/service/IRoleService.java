package com.eduask.service;

import java.util.List;

import com.eduask.bean.PurView;
import com.eduask.bean.Role;
import com.eduask.util.PageUtil;

public interface IRoleService {

	List<Role> findAll();

	void deleteFormRole_Purview(Long valueOf);

	void deleteFormRole(Long valueOf);

	List<Long> findUserByRoleId(String roleid);

	List<PurView> findPurview();

	Role getById(Long roleid);

	Role findRoleByName(String rolename);

	boolean save(Long uid, String rolename, String[] purids);

	void deleteRoleAndPurviewForeign(String roleid);

	void save(String roleid, String[] purids);

}
