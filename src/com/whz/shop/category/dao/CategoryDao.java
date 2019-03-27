package com.whz.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whz.shop.category.vo.Category;
import com.whz.shop.categorysecond.vo.CategorySecond;

/** 
 * @author whz 
 * @version on:2019-3-17 下午08:21:28 
 */
public class CategoryDao extends HibernateDaoSupport{

	/*
	 * 查找
	 */
	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	public List<Category> findAllCategory() {
		String hql = "from Category ";
		return this.getHibernateTemplate().find(hql);
	}
//	=================================================
	//DAO层的查询所有一级分类的方法
	public List<Category> findAll() {
		String hql = "from Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	// Dao中的保存一级分类的方法
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	// DAO中删除一级分类
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	// Dao中修改一级分类
	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}
}
