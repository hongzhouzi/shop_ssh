package com.whz.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.whz.shop.user.service.UserService;
import com.whz.shop.user.vo.User;

/** 
 * @author whz 
 * @version on:2019-3-16 下午08:37:04 
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;
	//使用模型驱动接收参数
	private User user = new User();
	public User getModel() {
		return user;
	}
	// 注入UserService
	private UserService userService;
	public UserAction(UserService userService) {
		super();
		this.userService = userService;
	}
	//属性驱动，接收参数
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	/**
	 * 跳转到注册页面的执行方法
	 */
	public String registerPage() {
		return "register";
	}
	
	/**
	 * AJAX进行异步校验用户名的执行方法
	 * @throws IOException 
	 */
	public String findByName() throws IOException{
		User existUser = userService.findByUserName(user.getUsername());
		// 获得response对象,项页面输出:
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// 判断
		if (existUser != null) {
			// 查询到该用户:用户名已经存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		} else {
			// 没查询到该用户:用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用</font>");
		}
		return NONE;
	}
	
	/**
	 * 用户注册的方法:
	 */
	public String register() {
		/*//获取session域中的验证码
		String checkImg1 = (String) ServletActionContext.getRequest()
						 			.getSession().getAttribute("checkImg");
		//若验证码不相等（忽略大小写的比较）提示错误
		if(!checkImg.equalsIgnoreCase(checkImg1)){
			this.addActionError("验证码输入错误！");
			return "checkImgFail";
		}*/
		if(!checkImgPass()){
			return "checkImgFail";
		}
		userService.save(user);
		this.addActionMessage("注册成功，请去邮箱激活！");
		return "msg";
	}
	
	/***
	 * 用户激活
	 */
	public String active() {
		User existU = userService.activeFindCode(user.getCode());
		if(existU != null){
			this.addActionMessage("激活成功！");
			existU.setCode(null);
			existU.setState(1);
			userService.activeUpdate(existU);
		}else {
			this.addActionMessage("激活失败!");
		}
		return "msg";
	}
	
	/*
	 * 请求登录页面
	 */
	public String loginPage() {
		return LOGIN;
	}
	
	/*
	 * 用户登录操作
	 */
	public String login() {
		//验证码验证
		if(!checkImgPass()){
			return "checkImgFail";
		}
		System.out.println(user);
		User existU = userService.login(user);
		System.out.println(existU);
		if(existU != null){
			//登录成功，设置session
			ServletActionContext.getRequest().getSession()
			.setAttribute("existUser", existU);
			return "index";
		}else {
			this.addActionError("登录失败！");
			return LOGIN;
		}
	}
	
	/*
	 * 用户退出
	 */
	public String quit() {
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "index";
	}
	
	/*
	 * 验证码部分
	 */
	public boolean checkImgPass() {
		//获取session域中的验证码
		String checkcode_s = (String) ServletActionContext.getRequest()
						 			.getSession().getAttribute("checkcode");
		//若验证码不相等（忽略大小写的比较）提示错误
		if(!checkcode.equalsIgnoreCase(checkcode_s)){
			this.addActionError("验证码输入错误！");
			return false;
		}
		return true;
	}
}
