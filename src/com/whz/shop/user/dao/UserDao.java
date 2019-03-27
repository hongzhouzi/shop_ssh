package com.whz.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whz.shop.product.vo.Product;
import com.whz.shop.user.vo.User;
import com.whz.shop.utils.PageHibernateCallback;

/** 
 * @author whz 
 * @version on:2019-3-16 下午10:28:16 
 */
public class UserDao extends HibernateDaoSupport{
	//根据用户名查询是否存在同名用户
	public User findByUserName(String name) {
		String hql = "from User where username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, name);
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;	
	}

	public void save(User user) {
		this.getHibernateTemplate().save(user);
	}

	/*
	 * 用户激活，查找激活码
	 */
	public User activeFindCode(String code) {
		String hql = "from User where code = ? ";
		List<User> list = this.getHibernateTemplate().find(hql, code);
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/*
	 * 激活成功后修改用户状态
	 */
	public void activeUpdate(User existU) {
		this.getHibernateTemplate().update(existU);
	}

	public User login(User user) {
		String hql = "from User where username = ? and password = ? and state = ? ";
		List<User> list = this.getHibernateTemplate().find(hql, user.getUsername(), user.getPassword(), 1);
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	//=======================后台管理部分==========================================
	public int findCount() {
		String hql = "select count(*) from User";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}
	

	public List<User> findByPage(int begin, int limit) {
		String hql = "from User";
		// 分页另一种写法:
		List<User> list = this.getHibernateTemplate().execute(new PageHibernateCallback<User>(hql, null, begin, limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	public User findByUid(Integer uid) {
		String hql = "from User where uid = ?";
		List<User> list = this.getHibernateTemplate().find(hql, uid);
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;	
	}

	public void delete(User existUser) {
		this.getHibernateTemplate().delete(existUser);
	}

	public void update(User user) {
		this.getHibernateTemplate().update(user);
	}
}
