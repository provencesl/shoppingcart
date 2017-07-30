<%@page import="java.text.SimpleDateFormat"%>
<%@page import="sunlei.entity.Order"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的订单</title>
  </head>
  <body>
  		<%
  			List<Order> orders=(List<Order>)request.getAttribute("orders");
  		 %>
    	<h1>我的订单</h1>
    	<table border="1">
    		<tr>
    			<th>编号</th>
    			<th>订单号</th>
    			<th>价格</th>
    			<th>时间</th>
    		
    		</tr>
    		<%	
    			int i=1;
    			for(Order order:orders){
    		 %>
    		<tr>
    			<td><%=i++ %></td>
    			<td><%=order.getNo() %></td>
    			<td><%=order.getPrice() %></td>
    			<td><%=formatTime(order.getCreateTime()) %></td>
    			
    		</tr>
    		<%
    			}
    		 %>
    	</table>
    	<a href="<%=request.getContextPath()%>/findAll.product">返回</a>
    	<%!
    		private String formatTime(Date date){
    			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    			return sdf.format(date);
    		}
    	
    	 %>
  </body>
</html>
