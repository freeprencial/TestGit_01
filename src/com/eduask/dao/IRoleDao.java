package com.eduask.dao;

import java.util.List;

import com.eduask.bean.PurView;
import com.eduask.bean.Role;
import com.eduask.util.PageUtil;

public interface IRoleDao extends IBaseDao<Role> {
	
	public List<Role> findAll();
	
	public Role findById(Long id);
	
	public boolean save(Role t);
	
	public boolean edit(Role t);
	
	public boolean delete(Long id);
	
	public List<Role> findByIds(Long[] ids);

	public void deleteFormRole_Purview(Long roleid);

	public void deleteFormRole(Long roleid);

	public List<Long> findUserByRoleId(Long roleid);

	public List<PurView> findTopPurviewList();
	
	public List<PurView> findChildrenPurviewList(Integer parentpurid);

	public Role getById(Long roleid);

	public Role findRoleByName(String rolename);

	public boolean saveRoleAndPurviewForeign(Long roleid, String[] purids);

	public List<PurView> findChildrenPurviewList1(Long roleid, Integer purId);

	public void deleteRoleAndPurviewForeign(String roleid);

}
