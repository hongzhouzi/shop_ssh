<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>whz商城</title>
<link href="${pageContext.request.contextPath}/css/slider.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<div class="container header">
	<div class="span5">
		<div class="logo">
			<a href="./网上商城/index.htm">
				<img src="${pageContext.request.contextPath}/image/common/logo.png" alt="whz"/>
			</a>
		</div>
	</div>
	<div class="span9">
<div class="headerAd">
	<img src="${pageContext.request.contextPath}/image/header.jpg" width="320" height="50" alt="正品保障" title="正品保障"/>
</div>	
</div>
	
	<%@ include file="menu.jsp" %>

</div>	

<div class="container index">
		

		<div class="span24">
			<div id="hotProduct" class="hotProduct clearfix">
					<div class="title">
						<strong>热门商品</strong>
					</div>
					<ul class="tab">
							<li class="current">
								<a href="./蔬菜分类.htm?tagIds=1" target="_blank"></a>
							</li>
					</ul>
						<ul class="tabContent" style="display: block;">
							<s:iterator var="p" value="hList">
									<li>
										<a href="${ pageContext.request.contextPath }/product_findByPid?pid=<s:property value="#p.pid"/>" target="_blank"><img src="${pageContext.request.contextPath}/<s:property value="#p.image"/>" style="display: block;"></a>
									</li>
							</s:iterator>		
						</ul>
						<ul class="tabContent" style="display: none;">
								<!-- 	<li>
										<a target="_blank"><img src="./Mango商城 - Powered By Mango Team_files/c5b1b396-181a-4805-9e68-9b400d71f91e-thumbnail.jpg" data-original="http://storage.shopxx.net/demo-image/3.0/201301/c5b1b396-181a-4805-9e68-9b400d71f91e-thumbnail.jpg" style="display: block;"></a>
									</li> -->
									
						</ul>
						<ul class="tabContent" style="display: none;">
									
						</ul>
			</div>
		</div>
		<div class="span24">
			<div id="newProduct" class="newProduct clearfix">
					<div class="title">
						<strong>最新商品</strong>
						<a  target="_blank"></a>
					</div>
					<ul class="tab">
							<li class="current">
								<a href="./蔬菜分类.htm?tagIds=2" target="_blank"></a>
							</li>
					</ul>
<!-- 					<div class="newProductAd">
									<img src="${pageContext.request.contextPath}/image/q.jpg" width="260" height="343" alt="最新商品" title="最新商品">
						</div>
						 -->						
					 <ul class="tabContent" style="display: block;">
					 	<s:iterator var="p" value="nList">
							<li>
								<a href="${ pageContext.request.contextPath }/product_findByPid?pid=<s:property value="#p.pid"/>" target="_blank"><img src="${pageContext.request.contextPath}/<s:property value="#p.image"/>" style="display: block;"></a>									</li>
							</li>
						</s:iterator>		
					</ul>
					<ul class="tabContent" style="display: none;">

					</ul>
			</div>
		</div>
		<div class="span24">
			<div class="friendLink">
				<dl>
					<dt>新手指南</dt>
							<dd>
								<a  target="_blank">支付方式</a>
								|
							</dd>
							<dd>
								<a  target="_blank">配送方式</a>
								|
							</dd>
							<dd>
								<a  target="_blank">售后服务</a>
								|
							</dd>
							<dd>
								<a  target="_blank">购物帮助</a>
								|
							</dd>
							<dd>
								<a  target="_blank">蔬菜卡</a>
								|
							</dd>
							<dd>
								<a  target="_blank">礼品卡</a>
								|
							</dd>
							<dd>
								<a target="_blank">银联卡</a>
								|
							</dd>
							<dd>
								<a  target="_blank">亿家卡</a>
								|
							</dd>
							
					<dd class="more">
						<a >更多</a>
					</dd>
				</dl>
			</div>
		</div>
	</div>

<!-- footer -->
<%@ include file="footer.jsp" %>

</body>
</html>