package com.eduask.service.impl;

import java.util.List;

import com.eduask.bean.PurView;
import com.eduask.bean.Role;
import com.eduask.bean.User;
import com.eduask.dao.IRoleDao;
import com.eduask.dao.impl.RoleDaoImpl;
import com.eduask.service.IRoleService;

public class RoleServiceImpl implements IRoleService {
	
	private IRoleDao roleDao = new RoleDaoImpl();

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}

	@Override
	public void deleteFormRole_Purview(Long roleid) {
		// TODO Auto-generated method stub
		roleDao.deleteFormRole_Purview(roleid);
	}

	@Override
	public void deleteFormRole(Long roleid) {
		// TODO Auto-generated method stub
		roleDao.deleteFormRole(roleid);
	}

	@Override
	public List<Long> findUserByRoleId(String roleid) {
		// TODO Auto-generated method stub
		return roleDao.findUserByRoleId(Long.valueOf(roleid));
	}

	@Override
	public List<PurView> findPurview() {
		// TODO Auto-generated method stub
		List<PurView> topPurviewList = roleDao.findTopPurviewList();
		
		for(int i=0;i<topPurviewList.size();i++){
			
			List<PurView> childrenPurviewList = roleDao.findChildrenPurviewList(topPurviewList.get(i).getPurId());
			
			topPurviewList.get(i).setChildrenPurviews(childrenPurviewList);
		}
	
		return topPurviewList;
	}

	@Override
	public Role getById(Long roleid) {
		// TODO Auto-generated method stub
		Role role = roleDao.getById(roleid);
		
		for(int i=0;i<role.getPurviewList().size();i++){
			
			List<PurView> childrenPurview = roleDao.findChildrenPurviewList1(role.getRoleid(),role.getPurviewList().get(i).getPurId());
			
			role.getPurviewList().get(i).setChildrenPurviews(childrenPurview);
		}
		
		
		return role;
	}

	@Override
	public Role findRoleByName(String rolename) {
		// TODO Auto-generated method stub
		return roleDao.findRoleByName(rolename);
	}

	@Override
	public boolean save(Long uid,String rolename, String[] purids) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		Role role = new Role();
		
		role.setRolename(rolename);
		User user = new User();
		user.setUid(uid);
		role.setCreateUser(user);
		flag = roleDao.save(role);

		if(flag){
			
			Role findRole = roleDao.findRoleByName(rolename);
			
			flag = roleDao.saveRoleAndPurviewForeign(findRole.getRoleid(),purids);
			
			
		}
		return flag;
	}

	@Override
	public void deleteRoleAndPurviewForeign(String roleid) {
		// TODO Auto-generated method stub
		roleDao.deleteRoleAndPurviewForeign(roleid);
	}

	@Override
	public void save(String roleid, String[] purids) {
		// TODO Auto-generated method stub
		roleDao.saveRoleAndPurviewForeign(Long.valueOf(roleid),purids);
	}

}
