<%@page import="sunlei.entity.User"%>
<%@page import="sunlei.entity.Product"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商品列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body>
  		<%
  			List<Product> products=(List<Product>)request.getAttribute("products");
  			User user=(User)session.getAttribute("user");
  		 %>
    	<h1>商品列表</h1>
    	<%
    		if(user==null){
    	 %>
	    	<a href="<%=request.getContextPath()%>/jsp/login.jsp">登陆</a>
	    	<a href="<%=request.getContextPath()%>/jsp/regist.jsp">注册</a>
    	<%
    		}else{
    	 %>
	    	<%=user.getUsername() %>用户
	    	<a href="<%=request.getContextPath()%>/logout.user">注销</a>
	    	<a href="<%=request.getContextPath()%>/myOrders.order">我的订单</a>
    	<%
    		}
    	 %>
    	<hr/>
    	<table border="1">
    		<tr>
    			<th>序号</th>
    			<th>商品</th>
    			<th>价格</th>
    			<th>操作</th>
    		</tr>
    		<%	
    			int i=1;
    			for(Product product:products){
    		 %>
    		<tr>
    			<td><%=i++ %></td>
    			<td><%=product.getName() %></td>
    			<td><%=product.getPrice() %></td>
    			<td>
    				<a href="<%=request.getContextPath()%>/add.cart?productId=<%=product.getId()%>">加入购物车</a>
    			</td>
    		</tr>
    		<%
    			}
    		 %>
    	</table>
    	<a href="<%=request.getContextPath()%>/find.cart">查看购物车</a>
  </body>
</html>