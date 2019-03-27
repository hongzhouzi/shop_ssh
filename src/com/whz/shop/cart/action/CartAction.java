package com.whz.shop.cart.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.whz.shop.cart.vo.Cart;
import com.whz.shop.cart.vo.CartItem;
import com.whz.shop.product.service.ProductService;
import com.whz.shop.product.vo.Product;

/** 
 * @author whz 
 * @version on:2019-3-18 下午10:48:04 
 */
public class CartAction extends ActionSupport{
	private Integer pid;
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	private Integer count;
	public void setCount(Integer count) {
		this.count = count;
	}
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String addCart(){
		/*HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
		String a= httpServletRequest.getHeader("referer");*/
		CartItem cartItem = new CartItem();
		//设置数量（添加几个该商品至购物车）
		cartItem.setCount(count);
		
		//根据pid查找到对应的商品
		Product product = productService.findByPid(pid);
		
		//设置商品相关信息
		cartItem.setProduct(product);
		
		//将该商品存入购物车
		Cart cart = getCart();
		cart.addCart(cartItem);
		
		return "addCart";
	}
	public String addCartRedirect() {
		return "addCartRedirect";
	}
	
	/*
	 * 根据商品pid删除某商品
	 */
	public String removeCart() {
		Cart cart = getCart();
		cart.removeCart(pid);
		return "removeCart";
	}
	
	/*
	 * 清空购物车
	 */
	public String clearCart() {
		Cart cart = getCart();
		cart.clearCart();
		return "clearCart";
	}
	
	/*
	 * 购买购物车所有商品
	 */
	public String saveOrder() {
		return "saveOrder";
	}
	/*
	 * 从菜单栏的购物车按钮到购物车界面
	 */
	public String myCart() {
		return "myCart";
	}
	
	
	/*
	 * 获取session域中的购物车
	 */
	private Cart getCart() {
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	
}
