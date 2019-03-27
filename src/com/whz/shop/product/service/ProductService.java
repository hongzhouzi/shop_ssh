package com.whz.shop.product.service;

import java.util.List;

import com.whz.shop.product.dao.ProductDao;
import com.whz.shop.product.vo.Product;
import com.whz.shop.utils.PageBean;

/** 
 * @author whz 
 * @version on:2019-3-17 下午09:33:46 
 */
public class ProductService {
	private ProductDao productDao;
	//注入productDao
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	//spring注入该bean
	public ProductService() {}
	
	/*
	 * 查找热门商品
	 */
	public List<Product> findHotProducts() {
		return productDao.findHotProducts();
	}
	public List<Product> findNewProducts() {
		return productDao.findNewProducts();
	}
	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}
	
	public PageBean<Product> findByPageCid(Integer cid, Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页
		pageBean.setPage(page);
		
		//设置每页显示的记录
		int limit = 10;
		pageBean.setLimit(limit);
		
		//设置总记录
		int totalCount = productDao.findCountCid(cid);
		pageBean.setTotalCount(totalCount);
		
		//设置总页数
		int temp = (int) Math.ceil(totalCount / limit);
		int totalPage = temp==0 ? 1 : temp;
		pageBean.setTotalPage(totalPage);
		
		//每页显示的数据集合
		int begin = (page - 1) * limit;
		List<Product> list = productDao.findByPageCid(cid, begin, limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	public PageBean<Product> findByPageCsid(Integer csid, Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页
		pageBean.setPage(page);
		
		//设置每页显示的记录
		int limit = 10;
		pageBean.setLimit(limit);
		
		//设置总记录
		int totalCount = productDao.findCountCsid(csid);
		pageBean.setTotalCount(totalCount);
		
		//设置总页数
		int temp = (int) Math.ceil(totalCount / limit);
		int totalPage = temp==0 ? 1 : temp;
		pageBean.setTotalPage(totalPage);
		
		//每页显示的数据集合
		int begin = (page - 1) * limit;
		List<Product> list = productDao.findByPageCsid(csid, begin, limit);
		pageBean.setList(list);
		
		return pageBean;
	}
//	===========================后台管理===================================
	// 后台查询所有商品带分页
	public PageBean<Product> findByPage(Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// 设置当前页数:
		pageBean.setPage(page);
		// 设置每页显示记录数:
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总记录数:
		int totalCount = 0;
		totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		// 设置总页数:
		int totalPage = 0;
		// Math.ceil(totalCount / limit);
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据集合:
		// 从哪开始:
		int begin = (page - 1) * limit;
		List<Product> list = productDao.findByPage(begin, limit);
		pageBean.setList(list);
		return pageBean;
	}
	// 业务层保存商品方法:
	public void save(Product product) {
		productDao.save(product);
	}

	// 业务层删除商品
	public void delete(Product product) {
		productDao.delete(product);
	}

	// 业务层修改商品的方法
	public void update(Product product) {
		productDao.update(product);
	}
	
}
