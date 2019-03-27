package com.whz.shop.index.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.whz.shop.category.service.CategoryService;
import com.whz.shop.category.vo.Category;
import com.whz.shop.product.service.ProductService;
import com.whz.shop.product.vo.Product;

/** 
 * @author whz 
 * @version on:2019-3-16 下午06:09:19 
 */
public class IndexAction extends ActionSupport{
	// 注入一级分类的Service:
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//注入热门商品的Service:
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/*
	 * 执行的访问首页的方法:
	 * */
	public String execute(){
		// 查询所有一级分类集合
		List<Category> categoryList = categoryService.findAllCategory();
		// 将一级分类存入到Session的范围:
		ActionContext.getContext().getSession().put("categoryList", categoryList);
		
		//查询热门商品:
		List<Product> hList = productService.findHotProducts();
		ActionContext.getContext().getValueStack().set("hList", hList);
		
		//查询最新商品
		List<Product> nList = productService.findNewProducts();
		ActionContext.getContext().getValueStack().set("nList", nList);
		return "index";
	}
	
	
}