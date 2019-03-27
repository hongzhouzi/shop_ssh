package com.whz.shop.admin.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whz.shop.admin.vo.Admin;

/** 
 * @author whz 
 * @version on:2019-3-20 上午09:36:20 
 */
public class AdminDao extends HibernateDaoSupport{
	public Admin login(Admin admin) {
		String hql = "from Admin where username = ? and password = ? ";
		List<Admin> list = this.getHibernateTemplate().find(hql, admin.getUsername(),admin.getPassword());
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
