package com.whz.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.whz.shop.cart.vo.Cart;
import com.whz.shop.cart.vo.CartItem;
import com.whz.shop.order.service.OrderService;
import com.whz.shop.order.vo.Order;
import com.whz.shop.order.vo.OrderItem;
import com.whz.shop.user.vo.User;
import com.whz.shop.utils.PageBean;
import com.whz.shop.utils.PaymentUtil;

/** 
 * @author whz 
 * @version on:2019-3-19 上午09:30:55 
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
	private OrderService orderService;
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	Order order =new Order();
	@Override
	public Order getModel() {
		return order;
	}
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}

	public String submitOrder() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//还没有登录，就跳转到登录界面（登录页面配置到global-results）
		if(user == null){
			this.addActionError("您还没登录，请先去登录噢！");
			return LOGIN;
		}
		
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		//购物车为空
		if(cart == null){
			this.addActionError("购物车里什么也没有，请先去购物噢！");
			return "msg";
		}
		
		//创建订单
		
		//订单所属用户
		order.setUser(user);
		order.setAddr(order.getUser().getAddr());
		order.setName(order.getUser().getName());
		order.setPhone(order.getUser().getPhone());
		
		//订单中的订单项，从购物车中获取所选商品的所有信息，将信息添加到订单中
		for(CartItem cartItem : cart.getCartItems()){
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			//该商品的订单情况
			orderItem.setOrder(order);
			//将该项产品的订单信息添加到订单中
			order.getOrderItems().add(orderItem);
		}
		//订单生成时间
		order.setOrdertime(new Date());
		//设置订单金额
		order.setTotal(cart.getTotal());
		//设置订单状态： 1：未付款。2：已付款，未发货。3：已发货，未收货。4.完成交易
		order.setState(1); 
		
//		将订单数据存入数据库
		orderService.saveOrder(order);
		
//		将订单数据显示在页面，数据存在值栈中，这儿设置了页面直接取即可

//		保存好了 之后把购物车清空一下
		cart.clearCart();
		
		return "submitOrder";
	}
	
	public String findByUid() {
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//还没有登录，就跳转到登录界面（登录页面配置到global-results）
		if(existUser == null){
			this.addActionError("您还没登录，请先去登录噢！");
			return LOGIN;
		}
		PageBean<Order> pageBean=orderService.findByUid(existUser.getUid(), page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUid";
	}
	
	public String findByOid() {
		order=orderService.findByOid(order.getOid());
		return "findByOid";
	}
	
	/*
	 * ------------------------------------------------------------
	 */
	// 接收支付通道编码:
	private String pd_FrpId;

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}
	// 接收付款成功后的参数:
	private String r3_Amt;
	private String r6_Order;
	
	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}
	// 为订单付款:
	public String payOrder() throws IOException {
		// 1.修改数据:
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		// 修改订单
		orderService.update(currOrder);
		// 2.完成付款:
		// 付款需要的参数:
		String p0_Cmd = "Buy"; // 业务类型:
		String p1_MerId = "10001126856";// 商户编号:
		String p2_Order = order.getOid().toString();// 订单编号:
		String p3_Amt = "0.01"; // 付款金额:
		String p4_Cur = "CNY"; // 交易币种:
		String p5_Pid = ""; // 商品名称:
		String p6_Pcat = ""; // 商品种类:
		String p7_Pdesc = ""; // 商品描述:
		String p8_Url = "http:/10.253.16.143:8080/shop/order_callBack.action"; // 商户接收支付成功数据的地址:
		String p9_SAF = ""; // 送货地址:
		String pa_MP = ""; // 商户扩展信息:
		String pd_FrpId = this.pd_FrpId;// 支付通道编码:
		String pr_NeedResponse = "1"; // 应答机制:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// 向易宝发送请求:
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		// 重定向:向易宝出发:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
		return NONE;
	}

	// 付款成功后跳转回来的路径:
	public String callBack(){
		// 修改订单的状态:
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		// 修改订单状态为2:已经付款:
		currOrder.setState(2);
		orderService.update(currOrder);
		this.addActionMessage("支付成功!订单编号为: "+r6_Order +" 付款金额为: "+r3_Amt);
		return "msg";
	}
	
	// 修改订单的状态:
	public String updateState(){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}
}
