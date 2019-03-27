package com.whz.shop.category.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.whz.shop.categorysecond.vo.CategorySecond;

/** 
 * @author whz 
 * @version on:2019-3-17 下午08:12:05 
 */
public class Category implements Serializable{
	private Integer cid;
	private String cname;
	Set<CategorySecond> categorySeconds = new HashSet<CategorySecond>();
	public Category(Integer cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}
	public Category() {
		super();
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public Set<CategorySecond> getCategorySeconds() {
		return categorySeconds;
	}
	public void setCategorySeconds(Set<CategorySecond> categorySeconds) {
		this.categorySeconds = categorySeconds;
	}
	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname
				+ ", categorySeconds=" + categorySeconds + "]";
	}
	
	
}
