package com.whz.shop.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.whz.shop.admin.vo.Admin;

/** 
 * @author whz 
 * @version on:2019-3-20 下午05:34:23 
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		Admin admin = (Admin) ServletActionContext.getRequest().getSession().getAttribute("existAdmin");
		System.out.println(admin);
		//已登录
		if(admin != null){
			return arg0.invoke();
		}else{
			//获得当前这个action
			ActionSupport support=(ActionSupport) arg0.getAction();
			support.addActionError("您还没登录，请先登录哟！");
			//跳转到登录页面
			return ActionSupport.LOGIN;
		}
	}

}
