package com.whz.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whz.shop.product.vo.Product;
import com.whz.shop.utils.PageHibernateCallback;

/** 
 * @author whz 
 * @version on:2019-3-17 下午09:34:09 
 */
public class ProductDao extends HibernateDaoSupport{

	public List<Product> findHotProducts() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		//添加查询条件，热门商品
		criteria.add(Restrictions.eq("is_hot", 1));
		//倒序排序，取前10的值
		criteria.addOrder(Order.desc("pdate"));
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria,0,10);
		return list;
	}

	public List<Product> findNewProducts() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		//添加查询条件，最新商品，按时间倒序排序，取前10的值
		criteria.addOrder(Order.desc("pdate"));
		return this.getHibernateTemplate().findByCriteria(criteria,0,10);
	}

	/*
	 * 根据商品id查询商品信息
	 */
	public Product findByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);
	}

	/*
	 * 所选类别商品分页查询
	 */

	/*
	 * 根据一级目录id查询该二级目录下的商品数
	 */
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql,cid);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	/*
	 * 根据二级目录id查询该二级目录下的商品数
	 */
	public int findCountCsid(Integer csid) {
		String hql = "select count(*) from CategorySecond where csid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql,csid);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	/*
	 * 根据一级目录的id、起始数量、每页显示的数量查询该页显示的商品信息
	 */
	public List<Product> findByPageCid(Integer cid, Integer begin, Integer limit) {
		// select p.* from category c,categorysecond cs,product p where c.cid = cs.cid and cs.csid = p.csid and c.cid = 2
		// select p from Category c,CategorySecond cs,Product p where c.cid = cs.category.cid and cs.csid = p.categorySecond.csid and c.cid = ?
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		// 分页另一种写法:
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid = ?";
		// 分页另一种写法:
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, new Object[]{csid}, begin, limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
//	==================后台管理============================
	// 后台统计商品个数的方法
	public int findCount() {
		String hql = "select count(*) from Product";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	// 后台查询所有商品的方法
	public List<Product> findByPage(int begin, int limit) {
		String hql = "from Product order by pdate desc";
		List<Product> list =  this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, null, begin, limit));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	// DAO中的保存商品的方法
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}

	// DAO中的删除商品的方法
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}
	

}
