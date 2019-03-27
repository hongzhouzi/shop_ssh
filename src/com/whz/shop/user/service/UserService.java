package com.whz.shop.user.service;

import java.util.List;

import com.whz.shop.product.vo.Product;
import com.whz.shop.user.dao.UserDao;
import com.whz.shop.user.vo.User;
import com.whz.shop.utils.MailUitls;
import com.whz.shop.utils.PageBean;
import com.whz.shop.utils.UUIDUtil;

/** 
 * @author whz 
 * @version on:2019-3-16 下午10:34:08 
 */
public class UserService {
	private UserDao userDao;
	//注入
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
//预留一个空参构造，spring注入
	public UserService(){}
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * 根据用户名找是否存在同用户名用户
	 */
	public User findByUserName(String name) {
		return userDao.findByUserName(name);
	}
	
	public void save(User user) {
		// 将数据存入到数据库
		user.setState(0); // 0:代表用户未激活.  1:代表用户已经激活.
		String code = UUIDUtil.getUUID()+UUIDUtil.getUUID();
		user.setCode(code);
		userDao.save(user);
		//发送激活邮件
		MailUitls.sendMail(user.getEmail(), code);
	}
	/*
	 * 用户激活
	 */
	public User activeFindCode(String code) {
		return userDao.activeFindCode(code);
	}
	public void activeUpdate(User existU) {
		userDao.activeUpdate(existU);
		
	}
	
	/*
	 * 用户登录
	 */
	public User login(User user) {
		return userDao.login(user);
	}
	//=======================后台管理部分==========================================
	/*
	 * 后台管理员查询用户的分页方法
	 */
	public PageBean<User> findByPage(Integer page) {
		PageBean<User> pageBean = new PageBean<User>();
		//设置当前页
		pageBean.setPage(page);
		
		//设置每页显示的记录
		int limit = 10;
		pageBean.setLimit(limit);
		
		//设置总记录
		int totalCount = userDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		//设置总页数
		int totalPage = (int) Math.ceil(totalCount / limit);
		pageBean.setTotalPage(totalPage);
		
		//每页显示的数据集合
		int begin = (page - 1) * limit;
		List<User> list = userDao.findByPage(begin, limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	public User findByUid(Integer uid) {
		return userDao.findByUid(uid);
	}
	public void delete(User existUser) {
		userDao.delete(existUser);
	}
	public void update(User user) {
		userDao.update(user);
	}
	
}
