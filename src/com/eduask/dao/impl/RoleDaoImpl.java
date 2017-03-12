package com.eduask.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eduask.bean.PurView;
import com.eduask.bean.Role;
import com.eduask.dao.BaseDao;
import com.eduask.dao.IRoleDao;
import com.eduask.util.PageUtil;
import com.mysql.fabric.xmlrpc.base.Array;

public class RoleDaoImpl extends BaseDao implements IRoleDao {

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		List<Role> rolelist = new ArrayList<Role>();
		getConnection();
		
		String sql = "select roleid,rolename from role";
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				Role role = new Role();
				
				role.setRoleid(rs.getLong(1));
				
				role.setRolename(rs.getString(2));
				
				rolelist.add(role);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
		}
		
		
		
		return rolelist;
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean save(Role role) {
		// TODO Auto-generated method stub
		boolean flag = false;
		getConnection();
		
		String sql = "insert into role(rolename,uid) values(?,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, role.getRolename());
			ps.setLong(2, role.getCreateUser().getUid());
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
	public boolean edit(Role t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Role> findByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFormRole_Purview(Long roleid) {
		// TODO Auto-generated method stub
		getConnection();
		
		String sql = "delete from role_purview where roleid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, roleid);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
	}

	@Override
	public void deleteFormRole(Long roleid) {
		// TODO Auto-generated method stub
		getConnection();
		
		String sql = "delete from role where roleid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, roleid);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
	}

	@Override
	public List<Long> findUserByRoleId(Long roleid) {
		// TODO Auto-generated method stub
		List<Long> uids = new ArrayList<Long>();
		getConnection();
		
		String sql = "select uid from user where roleid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, roleid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				long uid = rs.getLong(1);
				
				uids.add(uid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		return uids;
	}

	@Override
	public List<PurView> findTopPurviewList() {
		// TODO Auto-generated method stub
		List<PurView> purviewlist = new ArrayList<PurView>();
		
		getConnection();
		
		String sql = "select purid,purname from purview where parentpurid is null";
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				PurView purView = new PurView();
				
				purView.setPurId(rs.getInt(1));
				
				purView.setPurname(rs.getString(2));
				
				purviewlist.add(purView);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
		return purviewlist;
	}
	
	@Override
	public List<PurView> findChildrenPurviewList(Integer parentpurid) {
		// TODO Auto-generated method stub
		List<PurView> purviewlist = new ArrayList<PurView>();
		
		getConnection();
		
		String sql = "select purid,purname from purview where parentpurid =?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, parentpurid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				PurView purView = new PurView();
				
				purView.setPurId(rs.getInt(1));
				
				purView.setPurname(rs.getString(2));
				
				purviewlist.add(purView);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
		return purviewlist;
	}

	@Override
	public Role getById(Long roleid) {
		//要连着权限一起查询
		Role role = null;
		
		getConnection();
		
		String sql1 = "select roleid,rolename from role where roleid=?";
		
		String sql2 = "select p.purid,purname from role_purview rp,purview p where rp.purid=p.purid and rp.roleid=? and p.parentpurid is null";//查询该角色对应的一级权限
		
		try {
			ps = conn.prepareStatement(sql1);
			
			ps.setLong(1, roleid);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				role = new Role();
				
				role.setRoleid(rs.getLong(1));
				
				role.setRolename(rs.getString(2));
			}
			
			ps = conn.prepareStatement(sql2);
			
			ps.setLong(1, roleid);
			
			rs = ps.executeQuery();
			
			List<PurView> toppurview = new ArrayList<PurView>();
			
			while(rs.next()){
				
				PurView purview = new PurView();
				
				purview.setPurId(rs.getInt(1));
				
				purview.setPurname(rs.getString(2));
				
				toppurview.add(purview);
			}
			
			role.setPurviewList(toppurview);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
		return role;
	}

	@Override
	public Role findRoleByName(String rolename) {
		// TODO Auto-generated method stub
		Role role = null;
		
		getConnection();
		
		String sql = "select roleid,rolename from role where rolename=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, rolename);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				role = new Role();
				
				role.setRoleid(rs.getLong(1));
				
				role.setRolename(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		return role;
	}

	@Override
	public boolean saveRoleAndPurviewForeign(Long roleid, String[] purids) {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		getConnection();
		
		String sql = "insert into role_purview(roleid,purid) values(?,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<purids.length;i++){
				
				ps.setLong(1, roleid);
				ps.setLong(2, Long.valueOf(purids[i]));
				
				ps.addBatch();
			}
			
			int[] nums = ps.executeBatch();
			
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
		
		
		return flag;
	}

	@Override
	public List<PurView> findChildrenPurviewList1(Long roleid,Integer purId) {
		// TODO Auto-generated method stub
		List<PurView> purviewlist = new ArrayList<PurView>();
		
		getConnection();
		
		String sql = "select purid,purname from purview where purid in (select purid from role_purview where roleid=?) and parentpurid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, roleid);
			
			ps.setInt(2, purId);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				PurView purView = new PurView();
				
				purView.setPurId(rs.getInt(1));
				
				purView.setPurname(rs.getString(2));
				
				purviewlist.add(purView);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeResource();
			
		}
		return purviewlist;
	}

	@Override
	public void deleteRoleAndPurviewForeign(String roleid) {
		// TODO Auto-generated method stub
		getConnection();
		
		String sql = "delete from role_purview where roleid=?";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, Long.valueOf(roleid));
			
			int num = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			closeResource();
		}
	}
	
	

}
