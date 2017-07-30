<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户注册</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common.css" />
  </head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
  <script type="text/javascript">
  		$(function(){
  			$("a[href='javascript:;']").click(function(){
				$("img").attr("src","<%=request.getContextPath()%>/show.code?t="+new Date().getTime());
  			});
  		
  		
  		
  		
  		});
  </script>
   <script type="text/javascript">
 
    function checkname(){
   var name=document.getElementById("name");
   var msg=document.getElementById("namemsg");
   if(""==name.value){
       msg.innerHTML="用户名不能为空";
       name.className="bc";
       return false;
       }
       msg.innerHTML="";
       name.className="";
      return true; 
   }
   function checkpwd(){
    var pwd=document.getElementById("password");
    var msg=document.getElementById("pwdmsg");
    var len=pwd.value.length;
    if(len<2||len>6)
       {
        pwd.className="bc";
        msg.innerHTML="密码必须在2~6位";
        return false;
       }
       msg.innerHTML="";
       pwd.className="";
       return true;
     }
     function checkphone(){
     var phone=document.getElementById("phone");
     var msg=document.getElementById("phonemsg");
     var reg=/^\d{3,4}-\d{8}$/;
     if(!reg.test(phone.value)){
        phone.className="bc";
        msg.innerHTML="电话号码格式不正确";
        return false;
     }
     msg.innerHTML="";
     phone.className="";
     return true;
     }
    function checkaddress(){
     var address=document.getElementById("address");
     var msg=document.getElementById("addressmsg");
     if(""==address.value){
       msg.innerHTML="用户名不能为空";
       address.className="bc";
       return false;
       }
       msg.innerHTML="";
       address.className="";
      return true;
     }
  </script>
  <body>
    	<h1>用户注册</h1>
    	<hr/>
    	<form action="<%=request.getContextPath() %>/regist.user" method="post">
	    	用&nbsp;户&nbsp;名：<input name="username" id="name" type="text" onblur="checkname();" /><span id="namemsg"></span><br/>
	    	密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<input name="password" id="password" type="password" onblur="checkpwd();" /><span id="pwdmsg"></span><br/>
	    	电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：<input name="phone" id="phone" type="text" onblur="checkphone();" /><span id="phonemsg"></span><br/>
	    	地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：<input name="address" id="address" type="text" onblur="checkaddress();" /><span id="addressmsg"></span><br/>
	    	验&nbsp;证&nbsp;码：<input name="textcode" type="text"/><br/>
	    	<a href="javascript:;">
    		<img src="<%=request.getContextPath()%>/show.code">
    		</a>
    		<a href="javascript:;">换一换</a>
    		<br/>
    		<%
    			String msg=(String)request.getAttribute("message");
    			if(msg!=null){
    		 %>
    		<span style="color: red;"><%=msg %></span>
    		<%
    			}
    		 %>
    		<br/>
	    	<input type="submit" value="注册"/>
    	</form>
    	<a href="<%=request.getContextPath()%>/findAll.product">返回</a>
  </body>
</html>