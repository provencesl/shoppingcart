<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户登录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  
  <body>
    	<h1>用户登录</h1>
    	<hr/>
    	<form action="<%=request.getContextPath() %>/login.user" method="post">
	    	用&nbsp;户&nbsp;名：<input name="username" type="text"/><br/>
	    	密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<input name="password" type="password"/><br/>
	    	<%
	    		String msg=(String)request.getAttribute("message");
	    		if(msg!=null){
	    	 %>
	    	 <span style="color: red;"><%=msg %></span>
	    	 <%
	    	 	}
	    	  %>
	    	<br/>
	    	<input type="submit" value="登陆"/>
    	</form>
    	<a href="<%=request.getContextPath()%>/findAll.product">返回</a>
  </body>
</html>