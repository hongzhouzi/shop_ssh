package com.whz.shop.admin.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.whz.shop.admin.service.AdminService;
import com.whz.shop.admin.vo.Admin;

/** 
 * @author whz 
 * @version on:2019-3-20 上午09:37:53 
 */
public class AdminAction extends ActionSupport implements ModelDriven<Admin>{
	//模型驱动接收参数
	private Admin admin = new Admin();
	@Override
	public Admin getModel() {
		return admin;
	}
	//set注入
	private AdminService adminService;
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	/*
	 * 进行登录操作
	 */
	public String login() {
		System.out.println(admin);
		//进入登录界面
		if(admin.getUsername() == null){
			return "index";
		}
		Admin existAdmin =adminService.login(admin);
		// 判断
		if (existAdmin == null) {
			// 用户名或密码错误
			this.addActionError("用户名或密码错误!");
			return "index";
		} else {
			// 登录成功:
			ServletActionContext.getRequest().getSession()
					.setAttribute("existAdmin", existAdmin);
			return "loginSuccess";
		}
	}
	
	
}
