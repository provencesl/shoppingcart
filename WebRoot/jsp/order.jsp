<%@page import="sunlei.entity.Item"%>
<%@page import="sunlei.vo.Cart"%>
<%@page import="sunlei.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>确认订单</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body>
  		<%
  			User user=(User)session.getAttribute("user");
  			Cart cart=(Cart)session.getAttribute("cart");
  		 %>
    	<h1>确认订单</h1>
    	<hr/>
    	用&nbsp;户&nbsp;名：<%=user.getUsername() %><br/>
    	电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：<%=user.getPhone() %><br/>
    	地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：<%=user.getAddress() %><br/>
    	<table border="1">
    		<tr>
    			<th>序号</th>
    			<th>商品</th>
    			<th>数量</th>
    			<th>价格</th>
    		</tr>
    		<%	
    			int i=1;
    			for(Item item:cart.getItems()){
    		 %>
	    		<tr>
	    			<td><%=i++ %></td>
	    			<td><%=item.getProduct().getName() %></td>
	    			<td><%=item.getNum() %></td>
	    			<td><%=item.getPrice() %></td>
	    		</tr>
    		<%
    			}
    		 %>
    	</table>
    	<hr/>
    	总计：<%=cart.getPrice() %>元<br/>
    	<a href="<%=request.getContextPath()%>/add.order">生成订单</a>
    	<a href="<%=request.getContextPath()%>/jsp/cart.jsp">返回</a>
  </body>
</html>
