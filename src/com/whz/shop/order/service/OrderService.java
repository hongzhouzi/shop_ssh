package com.whz.shop.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.whz.shop.order.dao.OrderDao;
import com.whz.shop.order.vo.Order;
import com.whz.shop.order.vo.OrderItem;
import com.whz.shop.product.vo.Product;
import com.whz.shop.utils.PageBean;

/** 
 * @author whz 
 * @version on:2019-3-19 上午09:29:37 
 */
@Transactional
public class OrderService {
	private OrderDao orderDao;
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public void saveOrder(Order order) {
		orderDao.saveOrder(order);
		
	}
	public PageBean<Order> findByUid(Integer uid ,Integer page) {
		
		PageBean<Order> pageBean = new PageBean<Order>();
		//设置当前页
		pageBean.setPage(page);
		
		//设置每页显示的记录
		int limit = 10;
		pageBean.setLimit(limit);
		
		//设置总记录
		int totalCount = orderDao.findCountUid(uid);
		pageBean.setTotalCount(totalCount);
		
		//设置总页数
		int temp = (int) Math.ceil(totalCount / limit);
		int totalPage = temp==0 ? 1 : temp;
		pageBean.setTotalPage(totalPage);
		
		//每页显示的数据集合
		int begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPageUid(uid, begin, limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	public Order findByOid(Integer oid) {
		return orderDao.findByOid(oid);
	}
//	=====================后台管理==================================
	// 业务层修改订单的方法:
	public void update(Order currOrder) {
		orderDao.update(currOrder);
	}

	// 业务层查询所有订单方法
	public PageBean<Order> findAll(Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		// 设置参数
		pageBean.setPage(page);
		// 设置每页显示的记录数:
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总记录数
		int totalCount = orderDao.findCount();
		pageBean.setTotalCount(totalCount);
		// 设置总页数
		int totalPage = 0;
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示数据集合
		int begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPage(begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	// 业务层查询订单项的方法
	public List<OrderItem> findOrderItem(Integer oid) {
		return orderDao.findOrderItem(oid);
	}
	
}
