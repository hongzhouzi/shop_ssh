package com.whz.shop.product;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.whz.shop.category.service.CategoryService;
import com.whz.shop.category.vo.Category;
import com.whz.shop.categorysecond.service.CategorySecondService;
import com.whz.shop.product.service.ProductService;
import com.whz.shop.product.vo.Product;
import com.whz.shop.utils.PageBean;

/** 
 * @author whz 
 * @version on:2019-3-18 上午12:02:39 
 */
public class ProductAction extends ActionSupport implements ModelDriven<Product>{
	//模型驱动，接受参数
	private Product product = new Product();
	@Override
	public Product getModel() {
		return product;
	}
	//spring注入时，set注入
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	// 注入一级分类的Service:
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private CategorySecondService categorySecondService;
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	//属性驱动接收参数
	private Integer cid;
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	private Integer csid;
	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	/*
	 * 根据商品pid查找对应的商品信息
	 */
	public String findByPid() {
		//将查询到的值写入模型驱动的参数中
		product= productService.findByPid(product.getPid());
		return "findProductByPid";
	}
	
	/*
	 * 查询二级目录
	 * 根据一级目录加载内容
	 */
	public String findByCid() {
		//左边栏的目录，在首页查找一级目录和二级目录时将相关值存在session中，之后直接取即可
//		Category category = categoryService.findByCid(cid);
		/*List<Category> categoryList = categoryService.findAllCategory();
		ActionContext.getContext().getSession().put("categoryList", categoryList);*/
		
		//页面商品内容
		PageBean<Product> pageBean = productService.findByPageCid(cid, page);// 根据一级分类查询商品,带分页查询
		// 将PageBean存入到值栈中:
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		ActionContext.getContext().getValueStack().set("cid", cid);

		return "findByCid";
	}
	/*
	 * 根据二级目录加载内容
	 */
	public String findByCsid() {
		//页面商品内容
		PageBean<Product> pageBean = productService.findByPageCsid(csid, page);// 根据一级分类查询商品,带分页查询
		System.out.println(pageBean);
		// 将PageBean存入到值栈中:
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
		
	}
}
