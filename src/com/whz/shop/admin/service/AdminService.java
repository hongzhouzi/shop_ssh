package com.whz.shop.admin.service;

import com.whz.shop.admin.dao.AdminDao;
import com.whz.shop.admin.vo.Admin;

/** 
 * @author whz 
 * @version on:2019-3-20 上午09:36:52 
 */
public class AdminService{
	private AdminDao adminDao;
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	public Admin login(Admin admin) {
		return adminDao.login(admin);
	}
	

}
