package com.whz.shop.order.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whz.shop.order.vo.Order;
import com.whz.shop.order.vo.OrderItem;
import com.whz.shop.utils.PageHibernateCallback;

/** 
 * @author whz 
 * @version on:2019-3-19 上午09:29:51 
 */
public class OrderDao extends HibernateDaoSupport{

	public void saveOrder(Order order) {
		this.getHibernateTemplate().save(order);
	}

	public int findCountUid(Integer uid) {
		String hql = "select count(*) from Order o where o.user.uid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql,uid);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	public List<Order> findByPageUid(Integer uid, int begin, int limit) {
		String hql = "from Order o where o.user.uid = ? order by o.ordertime desc";
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(hql, new Object[] { uid },
						begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	public Order findByOid(Integer oid) {
		return this.getHibernateTemplate().get(Order.class, oid);
	}
//	===================================后台管理=====================
	// DAO层修改订单的方法:
	public void update(Order currOrder) {
		this.getHibernateTemplate().update(currOrder);
	}

	// DAO中统计订单个数的方法
	public int findCount() {
		String hql = "select count(*) from Order";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	// DAO中分页查询订单的方法
	public List<Order> findByPage(int begin, int limit) {
		String hql = "from Order order by ordertime desc";
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(hql, null, begin, limit));
		return list;
	}

	// DAo中根据订单id查询订单项
	public List<OrderItem> findOrderItem(Integer oid) {
		String hql = "from OrderItem oi where oi.order.oid = ?";
		List<OrderItem> list = this.getHibernateTemplate().find(hql, oid);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}


}
