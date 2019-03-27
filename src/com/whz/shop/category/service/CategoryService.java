package com.whz.shop.category.service;

import java.util.List;

import com.whz.shop.category.dao.CategoryDao;
import com.whz.shop.category.vo.Category;
import com.whz.shop.categorysecond.vo.CategorySecond;

/** 
 * @author whz 
 * @version on:2019-3-17 下午08:21:56 
 */
public class CategoryService {
	private CategoryDao categoryDao;
	//注入categoryDao，set注入
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	//实例化bean，构造函数注入
	public CategoryService(){}
	/*
	 * 查找一级目录
	 */
	/*public List<Category> findAll() {
		return categoryDao.findAll();
	}
*/
	public Category findByCid(Integer cid) {
		return categoryDao.findByCid(cid);
	}

	public List<Category> findAllCategory() {
		return categoryDao.findAllCategory();
	}
//	=========================================后台管理=====================================
	// 业务层查询所有一级分类的方法
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	// 业务层保存一级分类的操作
	public void save(Category category) {
		categoryDao.save(category);
	}

	// 业务层删除一级分类
	public void delete(Category category) {
		categoryDao.delete(category);
	}

	// 业务层修改一级分类
	public void update(Category category) {
		categoryDao.update(category);
	}
}
